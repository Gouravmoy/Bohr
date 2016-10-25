package jobs.tasks;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Tabledetail;
import entity.generateEntity.GenerateColumnPreDefined;
import entity.generateEntity.GenerateColumnPrimaryKey;
import entity.generateEntity.GenerateColumnRandom;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedColumnEnum;
import entity.generateEntity.GeneratedTable;
import enums.ColumnType;

public class GenerateTableDataTask_1 extends Task {
	List<Tabledetail> sortedTableList;
	List<GeneratedTable> generatedTableData;
	List<GeneratedColumn> generatedColumnList;
	String mainFolderPath = "C:\\Users\\M1026352\\Desktop\\DataGn\\Temp";

	public GenerateTableDataTask_1(List<Tabledetail> sortedTableList) {
		super();
		this.sortedTableList = sortedTableList;
	}

	@Override
	public void execute() throws BuildException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		mainFolderPath = mainFolderPath + timeStamp;
		File mainFolder = new File(mainFolderPath);
		File tableFolder;
		mainFolder.mkdir();
		generatedTableData = new ArrayList<>();
		String textFilePath;
		for (Tabledetail tabledetail : sortedTableList) {
			GeneratedTable generatedTable = new GeneratedTable();
			generatedTable.setTableName(tabledetail.getTableName());
			tableFolder = new File(mainFolderPath + "\\" + tabledetail.getTableName());
			tableFolder.mkdir();
			generatedTable.setTablePath(tableFolder.getPath() + "\\" + tabledetail.getTableName() + ".txt");
			generatedColumnList = new ArrayList<>();
			for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
				textFilePath = tableFolder.getPath() + "\\";
				if (columnsdetail.getDatasamplemodel() != null) {
					generatePredefinedValues(textFilePath, columnsdetail);
					continue;
				}
				if (columnsdetail.getConstraintsdetails1().size() == 0) {
					if (columnsdetail.getType() == ColumnType.ENUM) {
						generateEnumColumn(textFilePath, columnsdetail);
						continue;
					}
					generateRandomColumn(textFilePath, columnsdetail);
				} else {
					for (Constraintsdetail constraintsdetail : columnsdetail.getConstraintsdetails1()) {
						if (constraintsdetail.getConstraintname().equals("PRIMARY")) {
							generatePrimaryKeyColumn(textFilePath, columnsdetail);
							break;
						} else if (constraintsdetail.getReferenceTable() != null) {
							generatePrimaryColumnAsForeignKey(columnsdetail, constraintsdetail);
							break;
						} else if (constraintsdetail.getIsunique() == 1
								&& constraintsdetail.getReferenceTable() != null) {
							generateRandomColumnWithUnique(textFilePath, columnsdetail);
							break;
						}
					}
				}
			}
			generatedTable.setGeneratedColumn(generatedColumnList);
			generatedTableData.add(generatedTable);
		}
	}

	private void generatePredefinedValues(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnPreDefined generatedColumn = new GenerateColumnPreDefined();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setPreDefinedValues(columnsdetail.getDatasamplemodel().getSampelValues());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumnList.add(generatedColumn);
	}

	private void generateRandomColumnWithUnique(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnRandom generatedColumn = new GenerateColumnRandom();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setGenerateAllUnique(true);
		generatedColumnList.add(generatedColumn);
	}

	private void generatePrimaryColumnAsForeignKey(Columnsdetail columnsdetail, Constraintsdetail constraintsdetail) {
		GenerateColumnPrimaryKey generatedColumn = new GenerateColumnPrimaryKey();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setFilePath(mainFolderPath + "\\" + constraintsdetail.getReferenceTable().getTableName() + "\\"
				+ constraintsdetail.getReferenceColumnName() + ".txt");
		generatedColumn.setStartValue(1);
		generatedColumn.setForeignKey(true);
		generatedColumnList.add(generatedColumn);
	}

	private void generatePrimaryKeyColumn(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnPrimaryKey generatedColumn = new GenerateColumnPrimaryKey();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setStartValue(1);
		generatedColumn.setForeignKey(false);
		generatedColumnList.add(generatedColumn);
	}

	private void generateEnumColumn(String textFilePath, Columnsdetail columnsdetail) {
		GeneratedColumnEnum generatedColumn = new GeneratedColumnEnum();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setEnumValues(columnsdetail.getEnumvalues());
		generatedColumnList.add(generatedColumn);
	}

	private void generateRandomColumn(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnRandom generatedColumn = new GenerateColumnRandom();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		if (columnsdetail.getIsnullable() == 1) {
			generatedColumn.setNullable(true);
		}
		generatedColumn.setGenerateAllUnique(false);
		generatedColumnList.add(generatedColumn);
	}

	public List<GeneratedTable> getGeneratedTableData() {
		return generatedTableData;
	}

}