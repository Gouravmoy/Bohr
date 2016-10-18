package views.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.DatabaseDao;
import dao.ProjectDao;
import dao.impl.DatabaseDAOImpl;
import dao.impl.ProjectDAOImpl;
import entity.Databasedetail;
import entity.Projectdetails;
import entity.Schemadetail;
import exceptions.PersistException;
import exceptions.ReadEntityException;
import jobs.tasks.RefrehTreeTask;
import service.SchemaService;
import service.impl.SchemaServiceImpl;

public class NewProjectDialog extends Dialog {
	private Text text;
	DatabaseDao databaseDao;
	ProjectDao projectDao;
	SchemaService schemaService;
	Combo schemaCombo;
	Combo databaseListCombo;

	public NewProjectDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Create Project");
		container.setLayout(new GridLayout(3, false));

		databaseDao = new DatabaseDAOImpl();
		schemaService = new SchemaServiceImpl();
		projectDao = new ProjectDAOImpl();

		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setText("Project Name");
		new Label(container, SWT.NONE);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDatabase = new Label(container, SWT.NONE);
		lblDatabase.setText("Database");
		new Label(container, SWT.NONE);

		databaseListCombo = new Combo(container, SWT.NONE);
		databaseListCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Databasedetail databasedetail = (Databasedetail) databaseListCombo.getData(databaseListCombo.getText());
				List<Schemadetail> schemadetails = schemaService.getAllSchemaForDBID(databasedetail.getIddatabase());
				for (Schemadetail schemadetail : schemadetails) {
					schemaCombo.add(schemadetail.getName());
					schemaCombo.setData(schemadetail.getName(), schemadetail);
				}
			}
		});
		try {
			List<Databasedetail> databasedetails = databaseDao.getAllDatabaseinDB();
			for (Databasedetail databasedetail : databasedetails) {
				databaseListCombo.add(databasedetail.getConnectionName());
				databaseListCombo.setData(databasedetail.getConnectionName(), databasedetail);
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}
		databaseListCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblSchema = new Label(container, SWT.NONE);
		lblSchema.setText("Schema");
		new Label(container, SWT.NONE);

		schemaCombo = new Combo(container, SWT.NONE);
		schemaCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return parent;

	}

	@Override
	protected void okPressed() {
		// GenerateDataJob generateDataJob = new GenerateDataJob("My Job");
		Projectdetails projectdetails = new Projectdetails();
		projectdetails.setProjectName(text.getText());
		projectdetails.setSchemadetail((Schemadetail) schemaCombo.getData(schemaCombo.getText()));
		try {
			projectDao.saveProjectdetails(projectdetails);
		} catch (PersistException e) {
			e.printStackTrace();
		}
		RefrehTreeTask refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
		// generateDataJob.setProjectdetails(projectdetails);
		// generateDataJob.schedule();
		super.okPressed();
	}

}
