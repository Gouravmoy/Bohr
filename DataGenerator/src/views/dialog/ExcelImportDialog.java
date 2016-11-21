package views.dialog;

import org.apache.tools.ant.BuildException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import enums.DBType;
import enums.ImportType;
import exceptions.PersistException;
import job.FirstJob;

public class ExcelImportDialog extends Dialog {

	public ExcelImportDialog(Shell shell) {
		super(shell);
	}

	private Text importFileLoc;
	private Text databaseName;

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);

		Label lblDatabaseName = new Label(container, SWT.NONE);
		lblDatabaseName.setBounds(5, 8, 83, 15);
		lblDatabaseName.setText("Database Name");

		databaseName = new Text(container, SWT.BORDER);
		databaseName.setBounds(135, 5, 324, 21);

		Label lblSchemaImportFile = new Label(container, SWT.NONE);
		lblSchemaImportFile.setBounds(5, 32, 102, 15);
		lblSchemaImportFile.setText("Schema Import File");

		importFileLoc = new Text(container, SWT.BORDER);
		importFileLoc.setBounds(135, 29, 324, 21);

		Button btnImportColumns = new Button(container, SWT.NONE);
		btnImportColumns.setBounds(135, 56, 93, 25);
		btnImportColumns.setText("Import Schema");
		btnImportColumns.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xlsm" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				if (selected != null) {
					importFileLoc.setText(selected);
				}
			}
		});
		container.getShell().setText("Import Schema");
		return container;
	}

	@Override
	protected void okPressed() {
		FirstJob firstJob = new FirstJob("My Job");
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		Databasedetail databasedetail = new Databasedetail();
		databasedetail.setConnectionName(databaseName.getText());
		databasedetail.setDescription("");
		databasedetail.setUsername("");
		databasedetail.setPassword("");
		databasedetail.setType(DBType.MYSQL);
		try {
			databaseDao.saveDatabse(databasedetail);
		} catch (PersistException W) {
			showError(W.getMessage(), getShell());
		}
		firstJob.setDatabasedetail(databasedetail);
		firstJob.setImportType(ImportType.EXCEL);
		firstJob.setImportFileLocation(importFileLoc.getText());
		System.out.println(firstJob.getResult());
		try {
			firstJob.schedule();
		} catch (BuildException err) {
			showError(err.getMessage(), getShell());
		}
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	public void showError(String msg, Composite parent) {
		MessageDialog.openError(parent.getShell(), "Error", msg);
	}
}
