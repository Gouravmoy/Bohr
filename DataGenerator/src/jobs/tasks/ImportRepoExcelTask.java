package jobs.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import dao.ColumnsDao;
import dao.ConstraintsDao;
import dao.SchemaDao;
import dao.TableDao;
import dao.impl.ColumnsDAOImpl;
import dao.impl.ConstraintsDAOImpl;
import dao.impl.SchemaDaoImpl;
import dao.impl.TableDaoImpl;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import enums.ColumnType;
import enums.KeyType;
import exceptions.ExcelImportException;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class ImportRepoExcelTask extends Task {
	static Schemadetail schemadetail;
	Databasedetail databasedetail;
	String importFilePath;
	private static SchemaDao schemaDao;
	private static TableDao tableDao;
	private static ColumnsDao columnsDao;
	private static ConstraintsDao constraintsDao;

	@Override
	public void execute() throws BuildException {
		System.out.println("Importing Excel Schema");
		try {
			schemaDao = new SchemaDaoImpl();
			schemadetail = new Schemadetail();
			schemadetail.setName(databasedetail.getConnectionName());
			schemadetail.setDatabasedetail(databasedetail);
			schemaDao.saveSchema(schemadetail);
			tableDao = new TableDaoImpl();
			columnsDao = new ColumnsDAOImpl();
			constraintsDao = new ConstraintsDAOImpl();
			FileInputStream file = new FileInputStream(new File(importFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			System.out.println("Importing All Tables");
			List<Tabledetail> tabledetails = createTables(workbook, schemadetail);
			tableDao.saveListOfTables(tabledetails);
			System.out.println("Importing Columns");
			List<Columnsdetail> columnsdetails = createColumns(workbook, tabledetails);
			columnsDao.saveListOfColumns(columnsdetails);
			System.out.println("Importing Constraints");
			List<Constraintsdetail> constraintsdetailList = createConstraints(workbook, columnsdetails);
			constraintsDao.saveListOfConstraint(constraintsdetailList);
			file.close();
		} catch (BuildException e) {
			e.printStackTrace();
			try {
				throw new ExcelImportException("Build Exception");
			} catch (ExcelImportException e1) {
				throw new BuildException(e1.getMessage(), e1);
			}
		} catch (PersistException e) {
			e.printStackTrace();
			try {
				throw new ExcelImportException("Persist Exception");
			} catch (ExcelImportException e1) {
				throw new BuildException(e1.getMessage(), e1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				throw new ExcelImportException("File Not Found Exception");
			} catch (ExcelImportException e1) {
				throw new BuildException(e1.getMessage(), e1);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				throw new ExcelImportException("I/O Exception");
			} catch (ExcelImportException e1) {
				throw new BuildException(e1.getMessage(), e1);
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
			try {
				throw new ExcelImportException("Exception in Reading Database");
			} catch (ExcelImportException e1) {
				throw new BuildException(e1.getMessage(), e1);
			}
		}
	}

	private static List<Constraintsdetail> createConstraints(XSSFWorkbook workbook, List<Columnsdetail> columnsdetails)
			throws ReadEntityException {
		List<Constraintsdetail> constraintsdetails = new ArrayList<>();
		Constraintsdetail constraintsdetail;
		int rowCount = 0;
		List<Tabledetail> tabledetails = new ArrayList<>();
		List<String> tableNames = new ArrayList<>();
		for (Columnsdetail columnsdetail : columnsdetails) {
			if (!tableNames.contains(columnsdetail.getTabledetail().getTableName())) {
				tabledetails.add(tableDao.getTableByNameAndSchema(columnsdetail.getTabledetail().getTableName(),
						schemadetail.getIdschema()));
				tableNames.add(columnsdetail.getTabledetail().getTableName());
			}
		}
		for (Tabledetail tabledetail : tabledetails) {
			XSSFSheet sheet = workbook.getSheet(tabledetail.getTableName());
			Iterator<Row> rowIterator = sheet.iterator();
			rowCount = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 1000)
					break;
				if (rowCount++ != 0) {
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getColumnIndex() == 0) {
							for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
								if (cell.getStringCellValue().equalsIgnoreCase(columnsdetail.getName())) {
									Cell refTableCell = row.getCell(8);
									Cell refColumnCell = row.getCell(9);
									if (refTableCell != null && refColumnCell != null) {
										constraintsdetail = new Constraintsdetail();
										constraintsdetail.setColumnsdetail1(columnsdetail);
										constraintsdetail.setConstraintname("UDC");
										constraintsdetail.setColumnsdetail1(columnsdetail);
										constraintsdetail
												.setIsunique((byte) (columnsdetail.getKeytype() == KeyType.UK ? 1 : 0));
										constraintsdetail.setReferenceTable(tableDao.getTableByNameAndSchema(
												refTableCell.getStringCellValue(), schemadetail.getIdschema()));
										for (Columnsdetail columnsdetail1 : columnsdetails) {
											if (columnsdetail1.getName()
													.equalsIgnoreCase(refColumnCell.getStringCellValue())) {
												if (columnsdetail1.getTabledetail().getTableName()
														.equals(constraintsdetail.getReferenceTable().getTableName())) {
													constraintsdetail.setReferenceColumnName(columnsdetail1.getName());
													break;
												}
											}
										}
										constraintsdetails.add(constraintsdetail);
									} else {
										break;
									}
									break;
								}
							}
						} else {
							break;
						}
					}
				}
			}

		}
		return constraintsdetails;
	}

	public static List<Columnsdetail> createColumns(XSSFWorkbook workbook, List<Tabledetail> tabledetails) {
		int rowCount = 0;
		List<Columnsdetail> columnsdetails = new ArrayList<>();
		Columnsdetail columnsdetail;
		for (Tabledetail tabledetail : tabledetails) {
			XSSFSheet sheet = workbook.getSheet(tabledetail.getTableName());
			Iterator<Row> rowIterator = sheet.iterator();
			rowCount = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 1000)
					break;
				if (rowCount++ != 0) {
					columnsdetail = new Columnsdetail();
					columnsdetail.setTabledetail(tabledetail);
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getColumnIndex() <= 7) {
							getCellValueForCell(columnsdetail, cell);
						} else {
							break;
						}
					}
					columnsdetails.add(columnsdetail);
				}
			}
		}
		return columnsdetails;
	}

	public static void getCellValueForCell(Columnsdetail columnsdetail, Cell cell) {
		switch (cell.getColumnIndex()) {
		case 0:
			columnsdetail.setName(cell.getStringCellValue());
			break;
		case 1:
			columnsdetail.setLength((long) cell.getNumericCellValue());
			break;
		case 2:
			columnsdetail.setDecimalLength((int) cell.getNumericCellValue());
			break;
		case 3:
			columnsdetail.setType(ColumnType.valueOf(cell.getStringCellValue()));
			break;
		case 4:
			if (columnsdetail.getType() == ColumnType.ENUM) {
				columnsdetail.setEnumvalues(cell.getStringCellValue());
			}
			break;
		case 5:
			columnsdetail.setKeytype(KeyType.valueOf(cell.getStringCellValue()));
			break;
		case 6:
			if (cell.getStringCellValue().equalsIgnoreCase("YES")) {
				if (columnsdetail.getKeytype() == KeyType.FK) {
					columnsdetail.setKeytype(KeyType.UK_FK);
				} else if (columnsdetail.getKeytype() == KeyType.NO_KEY) {
					columnsdetail.setKeytype(KeyType.UK);
				}
			}
			break;
		case 7:
			String isNullable = cell.getStringCellValue();
			columnsdetail.setIsnullable((byte) (isNullable.equalsIgnoreCase("YES") ? 1 : 0));
			break;
		default:
			break;
		}
	}

	private static List<Tabledetail> createTables(XSSFWorkbook workbook, Schemadetail schemadetail2) {
		XSSFSheet sheet = workbook.getSheet("Meta");
		Iterator<Row> rowIterator = sheet.iterator();
		int count = 0;
		List<Tabledetail> tabledetails = new ArrayList<>();
		Tabledetail tabledetail;
		String tableName;

		while (rowIterator.hasNext()) {
			tableName = "";
			Row row = rowIterator.next();
			if (count++ != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.print(cell.getNumericCellValue() + "t");
						break;
					case Cell.CELL_TYPE_STRING:
						// System.out.print(cell.getStringCellValue() + "t");
						tableName = cell.getStringCellValue();
						break;
					}
					break;
				}
				tabledetail = new Tabledetail();
				tabledetail.setTableName(tableName);
				tabledetail.setSchemadetail(schemadetail2);
				tabledetails.add(tabledetail);
			}
		}
		return tabledetails;
	}

	public Databasedetail getDatabasedetail() {
		return databasedetail;
	}

	public void setDatabasedetail(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	public void setImportFilePath(String importFilePath) {
		this.importFilePath = importFilePath;
	}

}
