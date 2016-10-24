package job;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import entity.Projectdetails;
import entity.Schemadetail;
import entity.Tabledetail;
import jobs.tasks.AddPartTask;
import jobs.tasks.GenerateScriptsTask;
import jobs.tasks.GenerateTableDataTask;
import jobs.tasks.SortTableTask;

public class GenerateDataJob extends Job {

	Projectdetails projectdetails;

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
		GenerateTableDataTask generateTableDataTask = new GenerateTableDataTask(
				sortTableTask.getTabledetailListSorted());
		generateTableDataTask.execute();
		GenerateScriptsTask generateScriptsTask = new GenerateScriptsTask(
				"C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG", GenerateTableDataTask.tableDatas);
		generateScriptsTask.execute();
		AddPartTask deleteTask = new AddPartTask("bundleclass://DataGenerator/datagenerator.parts.DisplayTablePart");
		deleteTask.execute();
		return Status.OK_STATUS;
	}

	public void setProjectdetails(Projectdetails projectdetails) {
		this.projectdetails = projectdetails;
	}

}
