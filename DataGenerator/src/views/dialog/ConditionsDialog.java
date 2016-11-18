package views.dialog;

import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.ConditionsDao;
import dao.impl.ConditionsDaoImpl;
import entity.Columnsdetail;
import entity.Conditions;
import entity.Projectdetails;
import exceptions.PersistException;
import jobs.tasks.RefrehTreeTask;
import views.listners.TestFeildVerifyListner;

public class ConditionsDialog extends Dialog {

	Columnsdetail columnsdetail;

	private Text stringStartsWith;
	private Text stringEndsWith;
	private Text numericLowLimit;
	private Text numericUpLimit;
	private Text numLength;
	private DateTime dateLowerLimit;
	private DateTime dateUpperLimit;

	Group grpStringConditions;
	Group grpNumberConditions;
	Group grpDateConditions;

	Label lblProjectNametxt;
	Label lblTablenametxt;
	Label lblColumnnametxt;
	private Label lblColumnType;
	private Label columnType;

	ConditionsDao conditionsDao;
	Projectdetails project;

	public ConditionsDialog(Shell parentShell, Columnsdetail columnsdetail, Projectdetails projectdetails) {
		super(parentShell);
		this.columnsdetail = columnsdetail;
		conditionsDao = new ConditionsDaoImpl();
		this.project = projectdetails;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);

		Group grpColumnDetails = new Group(container, SWT.NONE);
		grpColumnDetails.setText("Column Details");
		grpColumnDetails.setBounds(10, 10, 438, 82);

		Label lblProjectName = new Label(grpColumnDetails, SWT.NONE);
		lblProjectName.setBounds(10, 31, 72, 15);
		lblProjectName.setText("Project Name");

		Label lblTableName = new Label(grpColumnDetails, SWT.NONE);
		lblTableName.setBounds(218, 31, 64, 15);
		lblTableName.setText("Table Name");

		Label lblColumnName = new Label(grpColumnDetails, SWT.NONE);
		lblColumnName.setBounds(10, 58, 78, 15);
		lblColumnName.setText("Column Name");

		lblProjectNametxt = new Label(grpColumnDetails, SWT.NONE);
		lblProjectNametxt.setBounds(94, 31, 93, 15);
		lblProjectNametxt.setText("Project NameTxt");

		lblTablenametxt = new Label(grpColumnDetails, SWT.NONE);
		lblTablenametxt.setBounds(300, 31, 135, 15);
		lblTablenametxt.setText("TableNameTxt");

		lblColumnnametxt = new Label(grpColumnDetails, SWT.NONE);
		lblColumnnametxt.setBounds(94, 58, 148, 15);
		lblColumnnametxt.setText("ColumnNameTxt");

		lblColumnType = new Label(grpColumnDetails, SWT.NONE);
		lblColumnType.setBounds(218, 58, 78, 15);
		lblColumnType.setText("Column Type");

		columnType = new Label(grpColumnDetails, SWT.NONE);
		columnType.setBounds(300, 58, 55, 15);
		columnType.setText("New Label");

		grpStringConditions = new Group(container, SWT.NONE);
		grpStringConditions.setText("String Conditions");
		grpStringConditions.setBounds(10, 98, 438, 60);

		Label lblStartsWith = new Label(grpStringConditions, SWT.NONE);
		lblStartsWith.setBounds(10, 29, 74, 15);
		lblStartsWith.setText("Starts With");

		stringStartsWith = new Text(grpStringConditions, SWT.BORDER);
		stringStartsWith.setBounds(90, 29, 113, 21);

		Label lblNewLabel = new Label(grpStringConditions, SWT.NONE);
		lblNewLabel.setBounds(218, 29, 69, 15);
		lblNewLabel.setText("Ends With");

		stringEndsWith = new Text(grpStringConditions, SWT.BORDER);
		stringEndsWith.setBounds(293, 29, 135, 21);

		grpNumberConditions = new Group(container, SWT.NONE);
		grpNumberConditions.setText("Numeric Conditions");
		grpNumberConditions.setBounds(10, 164, 438, 82);

		Label lblLowerLimit = new Label(grpNumberConditions, SWT.NONE);
		lblLowerLimit.setText("Lower Limit");
		lblLowerLimit.setBounds(10, 51, 74, 15);

		numericLowLimit = new Text(grpNumberConditions, SWT.BORDER);
		numericLowLimit.setBounds(90, 51, 113, 21);
		numericLowLimit.addVerifyListener(new TestFeildVerifyListner());

		Label lblUpperLimit = new Label(grpNumberConditions, SWT.NONE);
		lblUpperLimit.setText("Upper Limit");
		lblUpperLimit.setBounds(218, 51, 69, 15);

		numericUpLimit = new Text(grpNumberConditions, SWT.BORDER);
		numericUpLimit.setBounds(293, 51, 135, 21);
		numericUpLimit.addVerifyListener(new TestFeildVerifyListner());

		Label lblLength = new Label(grpNumberConditions, SWT.NONE);
		lblLength.setBounds(10, 24, 55, 15);
		lblLength.setText("Length");

		numLength = new Text(grpNumberConditions, SWT.BORDER);
		numLength.setBounds(90, 24, 113, 21);
		numLength.addVerifyListener(new TestFeildVerifyListner());

		grpDateConditions = new Group(container, SWT.NONE);
		grpDateConditions.setText("Date Conditions");
		grpDateConditions.setBounds(10, 252, 438, 75);

		Label lblStartDate = new Label(grpDateConditions, SWT.NONE);
		lblStartDate.setText("Start Date");
		lblStartDate.setBounds(10, 34, 74, 15);

		Label lblEndDate = new Label(grpDateConditions, SWT.NONE);
		lblEndDate.setText("End Date");
		lblEndDate.setBounds(214, 34, 60, 15);

		dateUpperLimit = new DateTime(grpDateConditions, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		dateUpperLimit.setBounds(277, 34, 113, 24);

		setColumnDetails();
		recursiveSetEnabled(grpStringConditions, false);
		recursiveSetEnabled(grpNumberConditions, false);
		recursiveSetEnabled(grpDateConditions, false);

		dateLowerLimit = new DateTime(grpDateConditions, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		dateLowerLimit.setBounds(84, 34, 113, 24);
		checkDataType();
		container.getShell().setText("Create Conditions");
		return container;
	}

	private void setColumnDetails() {
		if (columnsdetail != null) {
			lblProjectNametxt.setText(project.getProjectName());
			lblTablenametxt.setText(columnsdetail.getTabledetail().getTableName());
			lblColumnnametxt.setText(columnsdetail.getName());
			columnType.setText(columnsdetail.getType().toString());
		}
	}

	private void checkDataType() {
		if (columnsdetail != null) {
			switch (columnsdetail.getType()) {
			case CHAR:
			case VARCHAR:
			case LONGTEXT:
				recursiveSetEnabled(grpStringConditions, true);
				break;
			case INTEGER:
			case FLOAT:
			case DECIMAL:
				recursiveSetEnabled(grpNumberConditions, true);
				break;
			case DATE:
			case YEAR:
				recursiveSetEnabled(grpDateConditions, true);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void okPressed() {
		Conditions conditions = new Conditions();
		conditions.setColumnsdetail(columnsdetail);
		conditions.setProjectdetail(project);
		assignConditions(conditions);
		try {
			conditionsDao.saveConditions(conditions);
		} catch (PersistException e) {
			showError("Condition Already Exists! Cannot Add new Condition", getShell());
			e.printStackTrace();
		}
		RefrehTreeTask refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
		super.okPressed();
	}

	public void assignConditions(Conditions conditions) {
		switch (columnsdetail.getType()) {
		case CHAR:
		case VARCHAR:
		case LONGTEXT:
			conditions.setStartWith(stringStartsWith.getText());
			conditions.setEndsWith(stringEndsWith.getText());
			break;
		case INTEGER:
		case FLOAT:
		case DECIMAL:
			conditions.setSizeLimit(Integer.parseInt(numLength.getText()));
			conditions.setUpperLimit(Double.parseDouble(numericUpLimit.getText()));
			conditions.setLowerLimit(Double.parseDouble(numericLowLimit.getText()));
			break;
		case DATE:
		case YEAR:
			Calendar instance = Calendar.getInstance();
			instance.set(Calendar.DAY_OF_MONTH, dateLowerLimit.getDay());
			instance.set(Calendar.MONTH, dateLowerLimit.getMonth());
			instance.set(Calendar.YEAR, dateLowerLimit.getYear());
			conditions.setDateLowerLimit(new java.sql.Date(instance.getTime().getTime()));
			instance = Calendar.getInstance();
			instance.set(Calendar.DAY_OF_MONTH, dateUpperLimit.getDay());
			instance.set(Calendar.MONTH, dateUpperLimit.getMonth());
			instance.set(Calendar.YEAR, dateUpperLimit.getYear());
			conditions.setDateUpperLimit(new java.sql.Date(instance.getTime().getTime()));
			break;
		default:
			break;
		}
	}

	public void recursiveSetEnabled(Control ctrl, boolean enabled) {
		if (ctrl instanceof Composite) {
			Composite comp = (Composite) ctrl;
			for (Control c : comp.getChildren())
				recursiveSetEnabled(c, enabled);
		} else {
			ctrl.setEnabled(enabled);
		}
	}

	public void showError(String msg, Composite parent) {
		MessageDialog.openError(parent.getShell(), "Error", msg);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}
