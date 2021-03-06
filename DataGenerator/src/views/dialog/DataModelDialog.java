package views.dialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import dao.DataSampleDao;
import dao.DatabaseDao;
import dao.impl.DataSampleDaoImpl;
import dao.impl.DatabaseDAOImpl;
import entity.Columnsdetail;
import entity.DataSampleModelKey;
import entity.Databasedetail;
import entity.Datasamplemodel;
import entity.Projectdetails;
import enums.SampleType;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;
import jobs.tasks.ConnectonCreateTask;
import jobs.tasks.RefrehTreeTask;
import jobs.tasks.create.QueryExecuteTask;

public class DataModelDialog extends Dialog {
	private Text text;
	private Text seedList;
	private Text seedQuery;
	private Table table;
	private Button previewButton;
	private Combo databaseList;
	private Combo modelType;
	Columnsdetail columnsdetail;
	Button buttonIsUnique;
	Datasamplemodel datasamplemodel;
	Projectdetails projectdetails;

	List<String> modelValues;

	String[] titles = { "Sl No", "Model Values" };

	DatabaseDao databaseDao = new DatabaseDAOImpl();
	DataSampleDao dataSampleDao = new DataSampleDaoImpl();
	private Text tableNameText;
	private Text columnNameText;
	private Text projectName;

	public DataModelDialog(Shell parentShell, Columnsdetail sourceColumn, Projectdetails projectdetails) {
		super(parentShell);
		this.columnsdetail = sourceColumn;
		this.projectdetails = projectdetails;
	}

	public DataModelDialog(Shell parentShell, Datasamplemodel datasamplemodel) {
		super(parentShell);
		this.datasamplemodel = datasamplemodel;
		this.columnsdetail = datasamplemodel.getColumnsdetail();
		this.projectdetails = datasamplemodel.getProjectdetail();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Create Data Model");
		container.setLayout(null);

		table = new Table(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		table.setBounds(111, 426, 357, 147);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		previewButton = new Button(container, SWT.NONE);
		previewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SampleType sampleType = (SampleType) modelType.getData(modelType.getText());
				modelValues = new ArrayList<>();
				table.removeAll();
				if (sampleType == SampleType.USER_DEFINED) {
					if (seedList.getText() != null) {
						modelValues = Arrays.asList(seedList.getText().split(","));
					} else {
						showError("Seed List is empty", parent);
					}
				} else {
					if (seedQuery.getText() != null) {
						try {
							modelValues = executeQueryAndGetValues(seedQuery.getText(),
									(Databasedetail) databaseList.getData(databaseList.getText()));
						} catch (SQLException e1) {
							e1.printStackTrace();
							showError("Seed Query Error - " + e1.getMessage(), parent);
						}
					} else {
						showError("Seed Query is empty", parent);
					}
				}
				if (modelValues.size() != 0) {
					for (int i = 0; i < modelValues.size(); i++) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(0, i + 1 + "");
						item.setText(1, modelValues.get(i));
					}
				}
				for (int i = 0; i < titles.length; i++) {
					table.getColumn(i).pack();
				}
			}

		});
		previewButton.setBounds(111, 387, 75, 25);
		previewButton.setText("Preview");
		previewButton.setEnabled(false);

		Group grpUserInput = new Group(container, SWT.NONE);
		grpUserInput.setText("USER INPUT");
		grpUserInput.setBounds(10, 186, 470, 103);

		Label lblNewLabel = new Label(grpUserInput, SWT.NONE);
		lblNewLabel.setBounds(10, 22, 62, 15);
		lblNewLabel.setText("Seed List");

		seedList = new Text(grpUserInput, SWT.BORDER);
		seedList.setBounds(102, 19, 358, 50);
		seedList.setToolTipText("Provide a list of values separated by comma (,).");

		Group grpQueryFromDatabase = new Group(grpUserInput, SWT.NONE);
		grpQueryFromDatabase.setBounds(0, 103, 470, 107);
		grpQueryFromDatabase.setText("QUERY FROM DATABASE");

		seedQuery = new Text(grpQueryFromDatabase, SWT.BORDER);
		seedQuery.setBounds(103, 53, 357, 45);

		Label lblSeedQuery = new Label(grpQueryFromDatabase, SWT.NONE);
		lblSeedQuery.setBounds(10, 56, 60, 15);
		lblSeedQuery.setText("Seed Query");

		Label lblConnection = new Label(grpQueryFromDatabase, SWT.NONE);
		lblConnection.setBounds(10, 24, 76, 15);
		lblConnection.setText("Connection");

		databaseList = new Combo(grpQueryFromDatabase, SWT.NONE);
		databaseList.setBounds(103, 24, 357, 23);

		Label lblIsUnique = new Label(grpUserInput, SWT.NONE);
		lblIsUnique.setBounds(10, 78, 86, 15);
		lblIsUnique.setText("Is Repeateable");

		buttonIsUnique = new Button(grpUserInput, SWT.CHECK);
		buttonIsUnique.setBounds(102, 81, 13, 16);

		Group grpColumnInfo = new Group(container, SWT.NONE);
		grpColumnInfo.setText("Column Info");
		grpColumnInfo.setBounds(10, 0, 458, 170);

		Label lblDataModelName = new Label(grpColumnInfo, SWT.NONE);
		lblDataModelName.setBounds(10, 114, 96, 15);
		lblDataModelName.setText("Data Model Name");

		Label lblDataModelType = new Label(grpColumnInfo, SWT.NONE);
		lblDataModelType.setBounds(10, 141, 90, 15);
		lblDataModelType.setText("Data Model Type");

		modelType = new Combo(grpColumnInfo, SWT.NONE);
		modelType.setBounds(111, 137, 337, 23);

		for (SampleType sampleType : SampleType.values()) {
			if (sampleType != SampleType.PRE_DEFINED) {
				modelType.setData(sampleType.toString(), sampleType);
				modelType.add(sampleType.toString());
			}
		}

		text = new Text(grpColumnInfo, SWT.BORDER);
		text.setBounds(111, 111, 337, 21);

		Label lblTableName = new Label(grpColumnInfo, SWT.NONE);
		lblTableName.setBounds(10, 58, 78, 15);
		lblTableName.setText("Table Name");

		Label lblClumnName = new Label(grpColumnInfo, SWT.NONE);
		lblClumnName.setBounds(10, 82, 96, 15);
		lblClumnName.setText("Column Name ");

		tableNameText = new Text(grpColumnInfo, SWT.BORDER);
		tableNameText.setBounds(111, 55, 337, 21);

		columnNameText = new Text(grpColumnInfo, SWT.BORDER);
		columnNameText.setBounds(111, 79, 337, 21);

		Label lblProjectname = new Label(grpColumnInfo, SWT.NONE);
		lblProjectname.setBounds(10, 30, 78, 15);
		lblProjectname.setText("ProjectName");

		projectName = new Text(grpColumnInfo, SWT.BORDER);
		projectName.setBounds(111, 27, 337, 21);
		if (projectdetails != null) {
			projectName.setData(projectdetails.getProjectName(), projectdetails);
			projectName.setText(projectdetails.getProjectName());
		}

		if (columnsdetail != null) {
			tableNameText.setText(columnsdetail.getTabledetail().getTableName());
			columnNameText.setText(columnsdetail.getName());
			tableNameText.setEnabled(false);
			columnNameText.setEnabled(false);
		}

		modelType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SampleType sampleType = (SampleType) modelType.getData(modelType.getText());
				if (sampleType == SampleType.USER_DEFINED) {
					seedQuery.setEnabled(false);
					databaseList.setEnabled(false);
					seedList.setEnabled(true);
				} else {
					seedList.setEnabled(false);
					seedQuery.setEnabled(true);
					databaseList.setEnabled(true);
				}
				previewButton.setEnabled(true);
			}
		});
		try {
			List<Databasedetail> databasedetails = databaseDao.getAllDatabaseinDB();
			for (Databasedetail databasedetail : databasedetails) {
				databaseList.setData(databasedetail.getConnectionName(), databasedetail);
				databaseList.add(databasedetail.getConnectionName());
			}
		} catch (ReadEntityException e1) {
			showError("Error in fetching Database List" + e1.getMessage(), getShell());
		}

		if (this.datasamplemodel != null) {
			seedList.setText(datasamplemodel.getDatasamplemodelcol());
			buttonIsUnique.setSelection(datasamplemodel.isRepeteableIndex());
		}
		return parent;

	}

	@Override
	protected void okPressed() {
		RefrehTreeTask refrehTreeTask;
		if (this.datasamplemodel != null) {
			StringBuilder sb = new StringBuilder();
			for (String modelValue : modelValues) {
				sb.append(modelValue + ",");
			}
			datasamplemodel.setDatasamplemodelcol(sb.toString());
			datasamplemodel.setRepeteableIndex(buttonIsUnique.getSelection());
			try {
				dataSampleDao.update(datasamplemodel);
			} catch (EntityNotPresent e) {
				showError("Unable to Update", getShell());
				e.printStackTrace();
			}
		} else {
			Datasamplemodel datasamplemodel = new Datasamplemodel();
			StringBuilder sb = new StringBuilder();
			for (String modelValue : modelValues) {
				sb.append(modelValue + ",");
			}
			datasamplemodel.setDatasamplemodelcol(sb.toString());
			datasamplemodel.setSampletype(SampleType.USER_DEFINED);
			projectName.getData(projectName.getText());
			Projectdetails projectdetails = (Projectdetails) projectName.getData(projectName.getText());
			datasamplemodel.setProjectdetail(projectdetails);
			datasamplemodel.setColumnsdetail(columnsdetail);
			DataSampleModelKey dataSampleModelKey = new DataSampleModelKey(columnsdetail.getIdcolumnsdetails(),
					projectdetails.getIdproject());
			datasamplemodel.setRepeteableIndex(buttonIsUnique.getSelection());
			datasamplemodel.setConditionKey(dataSampleModelKey);
			try {
				dataSampleDao.saveDatasamplemodel(datasamplemodel);
			} catch (PersistException e) {
				showError("Unable to Save", getShell());
				e.printStackTrace();
			}
		}
		refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
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

	public List<String> executeQueryAndGetValues(String query, Databasedetail databasedetail) throws SQLException {
		List<String> returnlist = new ArrayList<>();
		int count = 0;
		ConnectonCreateTask conectiontask = new ConnectonCreateTask(databasedetail);
		conectiontask.execute();
		QueryExecuteTask queryExecuteTask = new QueryExecuteTask(conectiontask.getConnection(), query);
		queryExecuteTask.execute();
		ResultSet resultSet = queryExecuteTask.getResultSet();
		while (resultSet.next()) {
			if (count != 500)
				returnlist.add(resultSet.getString(1));
			else
				break;
		}
		return returnlist;
	}
}