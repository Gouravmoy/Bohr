package job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import dao.ProjectDao;
import dao.impl.ProjectDAOImpl;
import entity.Projectdetails;
import exceptions.PersistException;
import jobs.tasks.DefaultModelsToColumnsTask;
import jobs.tasks.RefrehTreeTask;
import views.dialog.NewProjectDialog;

public class CreateProjectJob extends Job {
	Projectdetails projectdetails;
	NewProjectDialog projectDialog;

	public CreateProjectJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor arg0) {

		DefaultModelsToColumnsTask columnsTask = new DefaultModelsToColumnsTask();
		try {
			ProjectDao projectDao = new ProjectDAOImpl();
			projectDao.saveProjectdetails(projectdetails);
			columnsTask.setProject(projectdetails);
			columnsTask.execute();
		} catch (PersistException e) {
			e.printStackTrace();
		}
		RefrehTreeTask refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				projectDialog.close();
			}
		});
		return Status.OK_STATUS;
	}

	public void setProjectdetails(Projectdetails projectdetails) {
		this.projectdetails = projectdetails;
	}

	public void setProjectDialog(NewProjectDialog projectDialog) {
		this.projectDialog = projectDialog;
	}

}
