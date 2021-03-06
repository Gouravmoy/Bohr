package job;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import common.Master;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import entity.generateEntity.RegenerateRelationCols;
import entity.generateEntity.RegenerateUKForFK;
import enums.ExportType;
import enums.KeyType;
import jobs.tasks.AddPartTask;
import jobs.tasks.SortTableTask;
import jobs.tasks.generate.GenerateColumnDataTask;
import jobs.tasks.generate.GenerateTableDataTask_1;
import jobs.tasks.generate.GenerateTableDataWithInsertQueryTask;
import views.dialog.StatusDialog;

public class GenerateDataJob2 extends Job {

	List<Tabledetail> selectedTableDetails;
	static int noOfRows = 0;
	Map<String, Integer> tableCount;
	long startTime = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	String exportPath;
	int projectId;
	ExportType exportType;

	public GenerateDataJob2(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor arg0) {
		System.out.println(tableCount);
		int rowCount;
		List<GeneratedColumn> ukFkColumns = new ArrayList<>();
		SortTableTask sortTableTask = new SortTableTask(selectedTableDetails);
		sortTableTask.execute();
		System.out.println(sortTableTask.getTabledetailListSorted());
		Master.INSTANCE.setSortedTableInLoadOrder(sortTableTask.getTabledetailListSorted());
		GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
		dataTask_1.setProjectId(projectId);
		dataTask_1.execute();
		Master.INSTANCE.printTimeElapsed(startTime, "GenerateColumnDataTask");
		System.out.println(dataTask_1.getGeneratedTableData());
		for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					StatusDialog.updateTableName(generatedTable.getTableName());
				}
			});
			ukFkColumns = new ArrayList<>();
			if (tableCount.containsKey(generatedTable.getTableName())) {
				rowCount = tableCount.get(generatedTable.getTableName());
			} else {
				if (generatedTable.getRowCount() == 0) {
					rowCount = noOfRows;
				} else {
					rowCount = generatedTable.getRowCount();
				}
			}
			generatedTable.setRowCount(rowCount);
			for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
				column.setNumberOfRows(rowCount);
				column.generateColumn();
				if (column.getKeyType() == KeyType.UK_FK)
					ukFkColumns.add(column);
			}
			if (!ukFkColumns.isEmpty())
				regenerateUKFKColumns(ukFkColumns);
		}
		for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
			regenerateRelationColumns(generatedTable.getGeneratedColumn(), dataTask_1.getGeneratedTableData());
			GenerateTableDataTask_1 dataTask_12 = new GenerateTableDataTask_1(generatedTable);
			dataTask_12.execute();
			File file = new File(exportPath + "//Result" + new SimpleDateFormat("yyyyMMddhhmm").format(new Date()));
			if (!file.exists()) {
				file.mkdirs();
			}
			GenerateTableDataWithInsertQueryTask dataWithInsertQueryTask = new GenerateTableDataWithInsertQueryTask(
					generatedTable, file.getPath(),exportType);
			dataWithInsertQueryTask.execute();
		}
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				StatusDialog.updateTableName("Completed!");
			}
		});
		Master.INSTANCE.setGeneratedTables(dataTask_1.getGeneratedTableData());
		AddPartTask addPartTask = new AddPartTask("bundleclass://DataGenerator/datagenerator.parts.DisplayTablePart");
		addPartTask.execute();
		return Status.OK_STATUS;
	}

	private void regenerateRelationColumns(List<GeneratedColumn> generatedColumn, List<GeneratedTable> list) {
		RegenerateRelationCols regenerateRelationCols = new RegenerateRelationCols();
		regenerateRelationCols.setGeneratedCol(generatedColumn);
		regenerateRelationCols.setGeneratedTables(list);
		regenerateRelationCols.regenerate();

	}

	public static void regenerateUKFKColumns(List<GeneratedColumn> ukFkColumns) {
		RegenerateUKForFK regenerateUKForFK = new RegenerateUKForFK();
		regenerateUKForFK.setUkFkColumns(ukFkColumns);
		regenerateUKForFK.setNumberOfRows(noOfRows);
		regenerateUKForFK.regenerate();
	}

	public List<Tabledetail> getSelectedTableDetails() {
		return selectedTableDetails;
	}

	public void setSelectedTableDetails(List<Tabledetail> selectedTableDetails) {
		this.selectedTableDetails = selectedTableDetails;
	}

	public int getNoOfRows() {
		return noOfRows;
	}

	@SuppressWarnings("static-access")
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}

	public Map<String, Integer> getTableCount() {
		return tableCount;
	}

	public void setTableCount(Map<String, Integer> tableCount) {
		this.tableCount = tableCount;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public ExportType getExportType() {
		return exportType;
	}

	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}
	
	
}
