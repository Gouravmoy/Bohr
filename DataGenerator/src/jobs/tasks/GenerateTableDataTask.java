package jobs.tasks;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.GeneratedTableData;
import entity.KeysData;
import entity.Tabledetail;
import enums.KeyType;
import exceptions.CannotFindParentException;

public class GenerateTableDataTask extends Task {
	List<Tabledetail> tabledetailListSorted = new ArrayList<>();
	public static List<GeneratedTableData> tableDatas = new ArrayList<>();

	public GenerateTableDataTask(List<Tabledetail> tabledetailListSorted) {
		super();
		this.tabledetailListSorted = tabledetailListSorted;
		GenerateTableDataTask.tableDatas = new ArrayList<>();
	}

	/*
	 * public static void main(String args[]) {
	 * Master.INSTANCE.setEnvironment(Environment.STAGING); SchemaDao schemaDao
	 * = new SchemaDaoImpl(); try { List<Schemadetail> schemadetails =
	 * schemaDao.getAllSchemainDB(); for (Schemadetail schemadetail :
	 * schemadetails) { if(schemadetail.getName().equals("nagios")){
	 * List<Tabledetail> tabledetails = new ArrayList<>();
	 * tabledetails.addAll(schemadetail.getTabledetails()); SortTableTask
	 * sortTableTask = new SortTableTask(tabledetails);
	 * sortTableTask.executeSort();
	 * executeGenerate(sortTableTask.getTabledetailListSorted()); } } } catch
	 * (ReadEntityException e) { e.printStackTrace(); } }
	 */

	@Override
	public void execute() throws BuildException {
		createInsertScripts(tabledetailListSorted, 5);

	}

	public static List<GeneratedTableData> createInsertScripts(List<Tabledetail> sortedTableList, int noOfRows) {
		System.out.println("Here");
		GeneratedTableData generatedTableData = new GeneratedTableData();
		String generatedValue = "";
		StringBuilder rowBuilder = new StringBuilder();
		List<KeysData> keyDataList = new ArrayList<>();
		List<String> rowDataList = new ArrayList<>();
		KeysData keysData = null;
		boolean storePrimaryKey = false;
		boolean storeForeignKey = false;
		boolean storeUniqueKey = false;
		boolean generateRandom = true;
		int tableOrder = 0;
		for (Tabledetail tabledetail : sortedTableList) {
			rowDataList = new ArrayList<>();
			keyDataList = new ArrayList<>();
			keysData = null;
			generatedTableData = new GeneratedTableData();
			generatedTableData.setTable(tabledetail);
			generatedTableData.setTableOrder(tableOrder++);

			List<Columnsdetail> allCols = new ArrayList<>();
			allCols.addAll(tabledetail.getColumnsdetails());
			List<Columnsdetail> uniqueCols = getCompositeUniqueList(allCols);

			List<String> compositeKeyList = new ArrayList<>();
			for (int i = 0; i < noOfRows; i++) {
				rowBuilder = new StringBuilder();
				// generateCompositeKeyCombination()
				while (!uniqueCols.isEmpty()) {
					System.out.println("in loop");
					String compositeKey = "";
					boolean matched = false;
					for (Columnsdetail compositeCol : uniqueCols) {
						if (tabledetail.getTableName().equals("store")) {
							System.out.println("Here");
						}
						try {
							generatedValue = getForeignKeyValue(compositeCol, tableDatas);
						} catch (CannotFindParentException e) {
							System.out.println(e.getMessage());
							generatedValue = null;
						}

						compositeKey += compositeCol.getName() + ":" + generatedValue + ",";
						keysData = checkKeyData(keyDataList, compositeCol, KeyType.UK_FK);
						if (keysData.getValues() == null)
							keysData.setValues(new ArrayList<>());
						if (!keysData.getValues().contains(generatedValue))
							keysData.getValues().add(generatedValue);
					}
					for (String testCompositeKey : compositeKeyList) {
						if (compositeKey.substring(0, compositeKey.length() - 1).equals(testCompositeKey)) {
							matched = true;
							break;
						}
					}
					if (matched == false) {
						compositeKeyList.add(compositeKey.substring(0, compositeKey.length() - 1));
						break;
					}
				}

				for (Columnsdetail column : allCols) {
					generateRandom = true;
					storePrimaryKey = false;
					if (column.getKeytype() == KeyType.PK) {
						keysData = checkKeyData(keyDataList, column, KeyType.PK);
						storePrimaryKey = true;
						storeUniqueKey = false;
						storeForeignKey = false;
					} else if (column.getKeytype() == KeyType.FK) {
						generateRandom = false;
						try {
							generatedValue = getForeignKeyValue(column, tableDatas);
						} catch (CannotFindParentException e) {
							System.out.println(e.getMessage());
							generatedValue = null;
						}
						keysData = checkKeyData(keyDataList, column, KeyType.FK);
						storeForeignKey = true;
						storePrimaryKey = false;
						storeUniqueKey = false;
					} else {
						storePrimaryKey = false;
						storeForeignKey = false;
						storeUniqueKey = false;
						if (column.getKeytype() == KeyType.UK) {
							storeUniqueKey = true;
							keysData = checkKeyData(keyDataList, column, KeyType.UK);
						} else if (column.getKeytype() == KeyType.UK_FK) {
							generateRandom = false;
							for (String keyValue : compositeKeyList.get(i).split(",")) {
								String key = keyValue.split(":")[0];
								if (column.getName().equals(key))
									generatedValue = keyValue.split(":")[1];
							}
						}
					}
					if (generateRandom) {
						if (column.getKeytype() != KeyType.UK) {
							generatedValue = generateRandomData(column, keysData).toString();
						} else {
							generatedValue = generateUniqueRandom(generatedValue, keysData, column);
						}
					}
					rowBuilder.append(generatedValue + ",");

					if (storePrimaryKey || storeForeignKey || storeUniqueKey) {
						if (keysData.getValues() == null)
							keysData.setValues(new ArrayList<>());
						if (!keysData.getValues().contains(generatedValue))
							keysData.getValues().add(generatedValue);
					}
				}
				rowDataList.add(rowBuilder.toString().substring(0, rowBuilder.length() - 1));
			}
			generatedTableData.setKeysDatas(keyDataList);
			generatedTableData.setRows(rowDataList);
			for (String row : rowDataList)
				System.out.println(generatedTableData.getTable().getTableName() + ":" + row);
			tableDatas.add(generatedTableData);
			System.out.println(generatedTableData);
		}
		return tableDatas;
	}

	private static List<Columnsdetail> getCompositeUniqueList(List<Columnsdetail> columnList) {
		List<Columnsdetail> compositeUniqueColumns = new ArrayList<>();

		for (int i = 0; i < columnList.size(); i++) {
			Columnsdetail column = columnList.get(i);
			if (column.getKeytype() == KeyType.UK_FK) {
				compositeUniqueColumns.add(column);
			}
		}
		return compositeUniqueColumns;
	}

	private static String generateUniqueRandom(String generatedValue, KeysData keysData, Columnsdetail column) {
		boolean generatedIsUnique;
		generatedIsUnique = false;
		while (!generatedIsUnique) {
			generatedValue = generateRandomData(column, keysData).toString();
			if (keysData.getValues() != null) {
				for (String generatedVal : keysData.getValues()) {
					if (!(generatedVal.equals(generatedValue))) {
						generatedIsUnique = true;
						break;
					}
				}
			} else {
				return generateRandomData(column, keysData).toString();
			}
		}
		return generatedValue;
	}

	private static KeysData checkKeyData(List<KeysData> keyDataList, Columnsdetail column, KeyType keyType) {
		KeysData keysData = null;
		int index = keyDataListContainsColumn(keyDataList, column);
		if (index != -1) {
			keysData = keyDataList.get(index);
		} else {
			if (keyType == KeyType.PK)
				keysData = new KeysData(column.getName(), KeyType.PK);
			else if (keyType == KeyType.FK)
				keysData = new KeysData(column.getName(), KeyType.FK);
			else if (keyType == KeyType.UK)
				keysData = new KeysData(column.getName(), KeyType.UK);
			else if (keyType == KeyType.UK_FK)
				keysData = new KeysData(column.getName(), KeyType.UK_FK);
			keyDataList.add(keysData);
		}
		return keysData;
	}

	private static String getForeignKeyValue(Columnsdetail column, List<GeneratedTableData> tableDatas)
			throws CannotFindParentException {
		for (Constraintsdetail constraint : column.getConstraintsdetails1()) {
			if (constraint.getConstraintname().equals("idx_unique_manager")) {
				System.out.println("Here");
			}
			if (constraint.getConstraintname().equals("PRIMARY") || constraint.getReferenceTable() == null)
				continue;

			String refTableName = null;
			try {
				refTableName = constraint.getReferenceTable().getTableName();
			} catch (Exception err) {
				err.printStackTrace();
			}
			String refColumnName = constraint.getReferenceColumnName();
			Random random = new Random();
			for (GeneratedTableData tableData : tableDatas) {
				if (tableData.getTable().getTableName().equals(refTableName)) {
					for (KeysData keysData : tableData.getKeysDatas()) {
						if (keysData.getKeyName().equals(refColumnName)) {
							return keysData.getValues().get(random.nextInt(keysData.getValues().size()));
						}
					}
				}
			}

			throw new CannotFindParentException("Error in load order.. cannot find parent table for constraint "
					+ constraint.getColumnsdetail1().getName() + "." + constraint.getConstraintname());

		}
		return null;
	}

	private static int keyDataListContainsColumn(List<KeysData> keyDataList, Columnsdetail column) {
		for (KeysData keysData : keyDataList) {
			if (keysData.getKeyName().equals(column.getName()))
				return keyDataList.indexOf(keysData);
		}
		return -1;
	}

	public static StringBuilder generateRandomData(Columnsdetail column, KeysData keysData) {
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int N = alphabet.length();
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		boolean usePreviousKey = false;
		if (keysData != null) {
			if (keysData.getKeyName().equals(column.getName()) && keysData.getValues() != null) {
				usePreviousKey = true;
			}
		}

		try {
			switch (column.getType()) {
			case CHAR:
				int size = (int) (column.getLength() <= 10 ? column.getLength() : 10);
				builder.append("\"");
				for (int i = 0; i < size; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				builder.append("\"");
				break;
			case VARCHAR:
				int sizeVarchar = (int) (column.getLength() <= 10 ? column.getLength() : 10);
				builder.append("\"");
				for (int i = 0; i < sizeVarchar; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				builder.append("\"");
				break;
			case INTEGER:
				if (!usePreviousKey) {
					builder.append("" + (r.nextInt(((2 ^ 31) - 0) + 1) + 0) + 1);
				} else {
					String previousKey = keysData.getValues().get(keysData.getValues().size() - 1);
					builder.append("" + (Integer.parseInt(previousKey) + 1));
				}
				break;
			case FLOAT:
				float minX = 50.0f;
				float maxX = 100.0f;
				float finalX = r.nextFloat() * (maxX - minX) + minX;
				if (!usePreviousKey) {
					builder.append("" + finalX);
				}
				break;
			case TINYINT:
				if (!usePreviousKey) {
					builder.append("" + r.nextInt(128));
				} else {
					String previousKey = keysData.getValues().get(keysData.getValues().size() - 1);
					builder.append("" + (Integer.parseInt(previousKey) + 1));
				}
				break;
			case ENUM:
				try {
					String[] enums = null;
					try {
						enums = column.getEnumvalues().split(",");
					} catch (Exception err) {
						err.printStackTrace();
					}
					int index = r.nextInt(enums.length);
					builder.append("\'");
					builder.append("" + enums[index]);
					builder.append("\'");
				} catch (Exception err) {
					err.printStackTrace();
				}
				break;
			case DATE:
				String startDate = "2013-02-08 00:00:00";
				String endDate = "2016-02-08 00:58:00";
				String finalDateFormat = "yyyy-MM-dd hh:mm:ss";
				long rangebegin = Timestamp.valueOf(startDate).getTime();
				long rangeend = Timestamp.valueOf(endDate).getTime();
				long diff = rangeend - rangebegin + 1;
				Timestamp rand = new Timestamp(rangebegin + (long) (Math.random() * diff));
				Date date = new Date(rand.getTime());
				SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateFormat);
				builder.append("\"");
				builder.append("" + dateFormat.format(date));
				builder.append("\"");
				break;
			case YEAR:
				String startDate1 = "2013-02-08 00:00:00";
				String endDate1 = "2016-02-08 00:58:00";
				String finalDateFormat1 = "yyyy";
				long rangebegin1 = Timestamp.valueOf(startDate1).getTime();
				long rangeend1 = Timestamp.valueOf(endDate1).getTime();
				long diff1 = rangeend1 - rangebegin1 + 1;
				Timestamp rand1 = new Timestamp(rangebegin1 + (long) (Math.random() * diff1));
				Date date1 = new Date(rand1.getTime());
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(finalDateFormat1);
				builder.append("\"");
				builder.append("" + dateFormat1.format(date1));
				builder.append("\"");
				break;
			default:
				System.out.println("Incorrect Data Type");
				break;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return builder;
	}

}
