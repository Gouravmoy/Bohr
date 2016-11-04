package job;

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
		Master.INSTANCE.setSortedTableInLoadOrder(sortTableTask.getTabledetailListSorted());
		GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
		dataTask_1.execute();
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
				rowCount = noOfRows;
			}
			generatedTable.setRowCount(rowCount);
			System.out.println("Generating data for table " + generatedTable.getTableName());
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
			GenerateTableDataWithInsertQueryTask dataWithInsertQueryTask = new GenerateTableDataWithInsertQueryTask(
					generatedTable, "C:\\Users\\M1026352\\Desktop\\DataGn\\Export"
							+ new SimpleDateFormat("yyyyMMddhhmm").format(new Date()) + "\\");
			dataWithInsertQueryTask.execute();
		}
		Display.getDefault().asyncExec(new Runnable() {
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

}
