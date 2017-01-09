package jobs.tasks.generate;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Columnsdetail;
import entity.Conditions;
import entity.Constraintsdetail;
import entity.Datasamplemodel;
import entity.Relationsdetail;
import entity.Tabledetail;
import entity.generateEntity.GenerateColumnPreDefined;
import entity.generateEntity.GenerateColumnPrimaryKey;
import entity.generateEntity.GenerateColumnRandom;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedColumnEnum;
import entity.generateEntity.GeneratedTable;
import service.ModelService;
import service.impl.ModelServiceImpl;

public class GenerateColumnTaskUpdated extends Task {

	static Logger log = Logger.getLogger(GenerateColumnDataTask.class.getName());
	List<Tabledetail> sortedTableList;
	List<GeneratedTable> generatedTableData;
	List<GeneratedColumn> generatedColumnList;
	String mainFolderPath = "";
	ModelService modelService;
	List<String> generatedTableList;
	int projectId;

	long startTime = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public GenerateColumnTaskUpdated(List<Tabledetail> sortedTableList) {
		super();
		this.sortedTableList = sortedTableList;
	}

	@Override
	public void execute() throws BuildException {
		modelService = new ModelServiceImpl();
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		mainFolderPath = System.getProperty("log_file_loc");
		mainFolderPath = mainFolderPath + "\\" + timeStamp;
		File mainFolder = new File(mainFolderPath);
		File tableFolder;
		mainFolder.mkdir();
		generatedTableData = new ArrayList<>();
		generatedTableList = new ArrayList<>();
		int rowRank = 1;
		String textFilePath;
		for (Tabledetail tabledetail : sortedTableList) {
			
		}
	}

	private void generateRelationColumnms(Columnsdetail columnsdetail, String textFilePath) {

	}

	private void generateNullableColumn(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnRandom generatedColumn = new GenerateColumnRandom();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setNullable(true);
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generatePredefinedValues(String textFilePath, Columnsdetail columnsdetail,
			GeneratedTable generatedTable) {
		GenerateColumnPreDefined generatedColumn = new GenerateColumnPreDefined();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		startTime = System.currentTimeMillis();
		if (columnsdetail.getDatasamplemodel() != null) {
			Datasamplemodel datasamplemodel = columnsdetail.getDatasamplemodel().iterator().next();
			generatedColumn.setPreDefinedValues(datasamplemodel.getDatasamplemodelcol());
			generatedColumn.setFileReopen(true);
			if (!datasamplemodel.isRepeteableIndex()) {
				generatedColumn.setFileReopen(false);
				generatedTable.setRowCount(generatedColumn.getPreDefinedValues().split(",").length);
			}
		} else {
			generatedColumn.setPreDefinedValues(columnsdetail.getPredefinedModel().getSampelValues());
			generatedColumn.setFileReopen(true);
		}

		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generateRandomColumnWithUnique(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnRandom generatedColumn = new GenerateColumnRandom();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setGenerateAllUnique(true);
		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		generatedColumn.setFileReopen(false);
		// generatedColumn.setPattern(columnsdetail.getPatterndetail());
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generatePrimaryColumnAsForeignKey(Columnsdetail columnsdetail, Constraintsdetail constraintsdetail,
			boolean b) {
		GenerateColumnPrimaryKey generatedColumn = new GenerateColumnPrimaryKey();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(mainFolderPath + "\\" + constraintsdetail.getReferenceTable().getTableName() + "\\"
				+ constraintsdetail.getReferenceColumnName() + ".txt");
		generatedColumn.setStartValue(1);
		generatedColumn.setForeignKey(true);
		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setFileReopen(b);
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generatePrimaryKeyColumn(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnPrimaryKey generatedColumn = new GenerateColumnPrimaryKey();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setStartValue(1);
		generatedColumn.setForeignKey(false);
		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		generatedColumn.setFileReopen(false);
		if (checkPrjectCondition(columnsdetail.getConditions())) {
			generatedColumn.setCondition(columnsdetail.getConditions().get(0));
		}
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generateEnumColumn(String textFilePath, Columnsdetail columnsdetail) {
		GeneratedColumnEnum generatedColumn = new GeneratedColumnEnum();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setEnumValues(columnsdetail.getEnumvalues());
		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		generatedColumn.setFileReopen(true);
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	private void generateRandomColumn(String textFilePath, Columnsdetail columnsdetail) {
		GenerateColumnRandom generatedColumn = new GenerateColumnRandom();
		generatedColumn.setColName(columnsdetail.getName());
		generatedColumn.setColumnType(columnsdetail.getType());
		generatedColumn.setColLength(columnsdetail.getLength());
		generatedColumn.setColDecLenght(columnsdetail.getDecimalLength());
		generatedColumn.setFilePath(textFilePath + columnsdetail.getName() + ".txt");
		generatedColumn.setGenerateAllUnique(false);
		generatedColumn.setKeyType(columnsdetail.getKeytype());
		generatedColumn.setTabledetail(columnsdetail.getTabledetail());
		if (checkPrjectCondition(columnsdetail.getConditions())) {
			generatedColumn.setCondition(columnsdetail.getConditions().get(0));
		}
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		if (!columnsdetail.getRelationsdetails().isEmpty()) {
			Relationsdetail relationsdetail = columnsdetail.getRelationsdetails().iterator().next();
			if (relationsdetail.getProjectdetail().getIdproject() == projectId) {
				relationsdetails.addAll(columnsdetail.getRelationsdetails());
				generatedColumn.setRelationsdetail(relationsdetails.get(0));
			}
		}
		generatedColumnList.add(generatedColumn);
	}

	public List<GeneratedTable> getGeneratedTableData() {
		return generatedTableData;
	}

	public List<GeneratedColumn> getGeneratedColumnList() {
		return generatedColumnList;
	}

	public void setGeneratedColumnList(List<GeneratedColumn> generatedColumnList) {
		this.generatedColumnList = generatedColumnList;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	private boolean checkProjectDataSampleId(List<Datasamplemodel> list) {
		boolean returnValue = false;
		Iterator<Datasamplemodel> iterator = list.iterator();
		while (iterator.hasNext()) {
			Datasamplemodel datasamplemodel = iterator.next();
			if (datasamplemodel.getProjectdetail().getIdproject() != projectId) {
				iterator.remove();
			} else {
				returnValue = true;
			}
		}
		return returnValue;
	}

	private boolean checkPrjectCondition(List<Conditions> conditions) {
		boolean returnValue = false;
		Iterator<Conditions> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Conditions conditions2 = iterator.next();
			if (conditions2.getProjectdetail().getIdproject() != projectId) {
				iterator.remove();
			} else {
				returnValue = true;
			}
		}
		return returnValue;
	}

}
