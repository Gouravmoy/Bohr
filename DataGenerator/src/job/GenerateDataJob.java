package job;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import common.Master;
import entity.Projectdetails;
import entity.Schemadetail;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import jobs.tasks.AddPartTask;
import jobs.tasks.GenerateColumnDataTask;
import jobs.tasks.GenerateTableDataTask_1;
import jobs.tasks.SortTableTask;

public class GenerateDataJob extends Job {

	Projectdetails projectdetails;
	int rowCount;

	public GenerateDataJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor arg0) {
		Schemadetail schemadetail = projectdetails.getSchemadetail();
		List<Tabledetail> tabledetails = new ArrayList<>();
		tabledetails.addAll(schemadetail.getTabledetails());
		SortTableTask sortTableTask = new SortTableTask(tabledetails);
		sortTableTask.execute();
		List<Tabledetail> tabledetails1 = new ArrayList<>();
		tabledetails.addAll(schemadetail.getTabledetails());
		SortTableTask sortTableTask1 = new SortTableTask(tabledetails1);
		sortTableTask1.execute();
		GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
		dataTask_1.execute();
		Master.INSTANCE.setGeneratedTables(dataTask_1.getGeneratedTableData());
		for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
			if (generatedTable.getTableName().equals("country")) {
				generatedTable.setRowCount(rowCount);
				for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
					column.setNumberOfRows(rowCount);
					column.generateColumn();
				}
				GenerateTableDataTask_1 dataTask_12 = new GenerateTableDataTask_1(generatedTable);
				dataTask_12.execute();
			}
		}
		AddPartTask addPartTask = new AddPartTask("bundleclass://DataGenerator/datagenerator.parts.DisplayTablePart");
		addPartTask.execute();
		return Status.OK_STATUS;
	}

	public void setProjectdetails(Projectdetails projectdetails) {
		this.projectdetails = projectdetails;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
