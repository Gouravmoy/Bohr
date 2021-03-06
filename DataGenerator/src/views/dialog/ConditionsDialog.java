package views.dialog;

import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
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
import entity.ConditionKey;
import entity.Conditions;
import entity.Projectdetails;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import jobs.tasks.RefrehTreeTask;
import views.listners.TestFeildVerifyListner;

public class ConditionsDialog extends Dialog {

	Columnsdetail columnsdetail;
	Conditions conditions;

	private Text stringStartsWith;
	private Text stringEndsWith;
	private Text numericLowLimit;
	private Text numericUpLimit;
	// private Text numLength;
	private DateTime dateLowerLimit;
	private DateTime dateUpperLimit;

	Group grpStringConditions;
	Group grpNumberConditions;
	Group grpDateConditions;
	Group grpGenerateSequence;

	Button seqPostfix;
	Button seqPreFix;
	Button btnGenerateSequence;

	Label lblProjectNametxt;
	Label lblTablenametxt;
	Label lblColumnnametxt;
	private Label lblColumnType;
	private Label columnType;

	ConditionsDao conditionsDao;
	Projectdetails project;
	private Text sequenceStartNumber;
	private Label lblStringLength;
	private Text stringLength;
	private Label lblMaxLengthReplace;

	/**
	 * @wbp.parser.constructor
	 */
	public ConditionsDialog(Shell parentShell, Columnsdetail columnsdetail, Projectdetails projectdetails) {
		super(parentShell);
		this.columnsdetail = columnsdetail;
		conditionsDao = new ConditionsDaoImpl();
		this.project = projectdetails;
	}

	public ConditionsDialog(Shell parentShell, Conditions conditions) {
		super(parentShell);
		this.conditions = conditions;
		this.columnsdetail = conditions.getColumnsdetail();
		conditionsDao = new ConditionsDaoImpl();
		this.project = conditions.getProjectdetail();
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
		grpStringConditions.setBounds(10, 98, 424, 264);

		grpNumberConditions = new Group(container, SWT.NONE);
		grpNumberConditions.setText("Numeric Conditions");
		grpNumberConditions.setBounds(10, 380, 438, 82);

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

		/*
		 * numLength = new Text(grpNumberConditions, SWT.BORDER);
		 * numLength.setBounds(90, 24, 113, 21); numLength.addVerifyListener(new
		 * TestFeildVerifyListner());
		 */

		grpDateConditions = new Group(container, SWT.NONE);
		grpDateConditions.setText("Date Conditions");
		grpDateConditions.setBounds(10, 468, 438, 75);

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
		recursiveSetEnabled(grpStringConditions, false);

		grpGenerateSequence = new Group(grpStringConditions, SWT.NONE);
		grpGenerateSequence.setText("Generate Sequence");
		grpGenerateSequence.setBounds(10, 160, 404, 82);

		seqPostfix = new Button(grpGenerateSequence, SWT.RADIO);
		seqPostfix.setBounds(10, 29, 76, 16);
		seqPostfix.setText("Postfix");
		seqPostfix.setSelection(true);

		seqPreFix = new Button(grpGenerateSequence, SWT.RADIO);
		seqPreFix.setBounds(100, 29, 59, 16);
		seqPreFix.setText("Prefix");

		sequenceStartNumber = new Text(grpGenerateSequence, SWT.BORDER);
		sequenceStartNumber.setBounds(100, 51, 76, 21);

		Label lblSequenceStartNumber = new Label(grpGenerateSequence, SWT.NONE);
		lblSequenceStartNumber.setBounds(10, 54, 85, 15);
		lblSequenceStartNumber.setText("Sequence Start");

		Group grpConcatenation = new Group(grpStringConditions, SWT.NONE);
		grpConcatenation.setText("Concatenation");
		grpConcatenation.setBounds(10, 30, 404, 109);

		lblStringLength = new Label(grpConcatenation, SWT.NONE);
		lblStringLength.setBounds(10, 19, 74, 15);
		lblStringLength.setText("String Length");

		stringLength = new Text(grpConcatenation, SWT.BORDER);
		stringLength.setBounds(90, 19, 74, 21);

		lblMaxLengthReplace = new Label(grpConcatenation, SWT.NONE);
		lblMaxLengthReplace.setBounds(170, 19, 117, 15);
		lblMaxLengthReplace.setText("Max Length - " + this.columnsdetail.getLength());

		Label lblStartsWith = new Label(grpConcatenation, SWT.NONE);
		lblStartsWith.setBounds(10, 49, 74, 15);
		lblStartsWith.setText("Starts With");

		stringStartsWith = new Text(grpConcatenation, SWT.BORDER);
		stringStartsWith.setBounds(90, 46, 135, 21);

		Label lblNewLabel = new Label(grpConcatenation, SWT.NONE);
		lblNewLabel.setBounds(10, 76, 69, 15);
		lblNewLabel.setText("Ends With");

		stringEndsWith = new Text(grpConcatenation, SWT.BORDER);
		stringEndsWith.setBounds(90, 73, 135, 21);

		btnGenerateSequence = new Button(grpConcatenation, SWT.CHECK);
		btnGenerateSequence.setBounds(246, 78, 135, 16);
		btnGenerateSequence.setText("Generate Sequence");
		btnGenerateSequence.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Button btn = (Button) event.getSource();
				if (btn.getSelection()) {
					recursiveSetEnabled(grpGenerateSequence, true);
				} else {
					recursiveSetEnabled(grpGenerateSequence, false);
				}
			}
		});

		stringLength.addVerifyListener(new TestFeildVerifyListner());
		sequenceStartNumber.addVerifyListener(new TestFeildVerifyListner());

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
				recursiveSetEnabled(grpGenerateSequence, false);
				break;
			case INTEGER:
			case FLOAT:
			case TINYINT:
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
		if (this.conditions != null) {
			assignConditions(this.conditions);
			try {
				conditionsDao.update(conditions);
			} catch (EntityNotPresent e) {
				showError("Unable to update", getShell());
				e.printStackTrace();
			}
		} else {
			Conditions conditions = new Conditions();
			conditions.setColumnsdetail(columnsdetail);
			conditions.setProjectdetail(project);
			ConditionKey conditionKey = new ConditionKey(columnsdetail.getIdcolumnsdetails(), project.getIdproject());
			conditions.setGenerateRandom(seqPreFix.getSelection());
			conditions.setSizeLimit((int) columnsdetail.getLength());
			conditions.setConditionKey(conditionKey);
			assignConditions(conditions);
			try {
				conditionsDao.saveConditions(conditions);
			} catch (PersistException e) {
				showError("Condition Already Exists! Cannot Add new Condition", getShell());
				e.printStackTrace();
			}
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
			conditions.setStartWith(stringStartsWith.getText().length() > 0 ? stringStartsWith.getText() : null);
			conditions.setEndsWith(stringEndsWith.getText().length() > 0 ? stringEndsWith.getText() : null);
			if (btnGenerateSequence.getSelection()) {
				if (seqPreFix.getSelection())
					conditions.setSequencePreFix(true);
				else
					conditions.setSequencePreFix(false);
			}
			conditions.setSequenceNo(
					sequenceStartNumber.getText().length() != 0 ? Integer.parseInt(sequenceStartNumber.getText()) : -1);
			if (stringLength.getText().length() != 0)
				conditions.setSizeLimit(Integer.parseInt(stringLength.getText()));
			else
				conditions.setSizeLimit(Integer.parseInt(columnsdetail.getLength() + ""));
			break;
		case TINYINT:
		case INTEGER:
		case FLOAT:
		case DECIMAL:
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
