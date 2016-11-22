package views.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

public class ImportDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;

	public ImportDialog(Shell parentShell) {
		super(parentShell);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Import Connections");
		container.setLayout(new GridLayout(3, false));

		Label lblDatabaseType = new Label(container, SWT.NONE);
		lblDatabaseType.setText("Database Type");
		new Label(container, SWT.NONE);

		Combo dbTypecombo = new Combo(container, SWT.NONE);
		for (DBType dbType : DBType.values()) {
			dbTypecombo.add(dbType.toString());
		}
		dbTypecombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDtabaseUrl = new Label(container, SWT.NONE);
		lblDtabaseUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblDtabaseUrl.setText("Dtabase Url");
		new Label(container, SWT.NONE);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblUserName = new Label(container, SWT.NONE);
		lblUserName.setText("User Name");
		new Label(container, SWT.NONE);

		text_1 = new Text(container, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setText("Password");
		new Label(container, SWT.NONE);

		text_2 = new Text(container, SWT.BORDER | SWT.PASSWORD);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return parent;

	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		// TODO Auto-generated method stub
		return super.getInitialLocation(initialSize);
	}

	@Override
	protected void okPressed() {
		FirstJob firstJob = new FirstJob("My Job");
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		Databasedetail databasedetail = new Databasedetail();
		databasedetail.setConnectionName(text.getText());
		databasedetail.setDescription("jdbc:mysql://localhost:3306/hr");
		databasedetail.setUsername("root");
		databasedetail.setPassword("root");
		databasedetail.setType(DBType.MYSQL);
		try {
			databaseDao.saveDatabse(databasedetail);
		} catch (PersistException W) {
			W.printStackTrace();
		}
		firstJob.setDatabasedetail(databasedetail);
		firstJob.setImportType(ImportType.DATABASE);
		firstJob.setImportDialog(this);
		firstJob.schedule();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

}
