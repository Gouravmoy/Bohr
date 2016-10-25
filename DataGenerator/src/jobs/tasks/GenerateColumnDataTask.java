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
import enums.KeyType;

public class GenerateColumnDataTask extends Task {
	List<Tabledetail> sortedTableList;
	List<GeneratedTable> generatedTableData;
	List<GeneratedColumn> generatedColumnList;
	String mainFolderPath = "C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\DataGeneration";

	public GenerateColumnDataTask(List<Tabledetail> sortedTableList) {
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
		boolean hasUKFK = false;
		for (Tabledetail tabledetail : sortedTableList) {
			GeneratedTable generatedTable = new GeneratedTable();
			generatedTable.setTableName(tabledetail.getTableName());
			tableFolder = new File(mainFolderPath + "\\" + tabledetail.getTableName());
			tableFolder.mkdir();
			generatedTable.setTablePath(tableFolder.getPath() + "\\TableFile_" + tabledetail.getTableName() + ".txt");
			generatedTable.setSchemaName(tabledetail.getSchemadetail().getName());
			generatedColumnList = new ArrayList<>();
			for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
				textFilePath = tableFolder.getPath() + "\\";
				if (columnsdetail.getDatasamplemodel() != null) {
					generatePredefinedValues(textFilePath, columnsdetail);
					continue;
				} else {
					if (columnsdetail.getKeytype() == null) {
						generateRandomColumn(textFilePath, columnsdetail);
					} else {
						if (columnsdetail.getType() == ColumnType.ENUM) {
							generateEnumColumn(textFilePath, columnsdetail);
							continue;
						} else if (columnsdetail.getKeytype().equals(KeyType.UK)) {
							generateRandomColumnWithUnique(textFilePath, columnsdetail);
						} else if (columnsdetail.getKeytype().equals(KeyType.PK)) {
							generatePrimaryKeyColumn(textFilePath, columnsdetail);
						} else if (columnsdetail.getKeytype().equals(KeyType.FK)) {
							generatePrimaryColumnAsForeignKey(columnsdetail,
									columnsdetail.getConstraintsdetails1().iterator().next());
						} else if (columnsdetail.getKeytype().equals(KeyType.UK_FK)) {
							hasUKFK = true;
							generatePrimaryColumnAsForeignKey(columnsdetail,
									columnsdetail.getConstraintsdetails1().iterator().next());
						} else {
							generateRandomColumn(textFilePath, columnsdetail);
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
		generatedColumn.setPreDefinedValues(columnsdetail.getDatasamplemodel().getDatasamplemodelcol());
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
