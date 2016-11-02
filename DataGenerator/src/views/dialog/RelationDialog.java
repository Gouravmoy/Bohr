package views.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.Master;
import dao.ColumnsDao;
import dao.ProjectDao;
import dao.RealationsDao;
import dao.impl.ColumnsDAOImpl;
import dao.impl.ProjectDAOImpl;
import dao.impl.RelationsDAOImpl;
import entity.Columnsdetail;
import entity.Projectdetails;
import entity.Relationsdetail;
import entity.Schemadetail;
import entity.Tabledetail;
import enums.RelationType;
import exceptions.PersistException;
import exceptions.ReadEntityException;
import jobs.tasks.RefrehTreeTask;

public class RelationDialog extends Dialog {
	private Combo projectNameCombo;
	private Combo schemaNameCombo;
	private Combo sourceTableCombo;
	private Combo targetTableCombo;
	private Combo sourceColumn;
	private Combo targetColCombo;
	private Combo relationTypeCombo;

	Relationsdetail relationsdetail;
	Projectdetails projectdetails;
	Columnsdetail columnsdetail;
	String projectName;
	ProjectDao projectDao;
	RealationsDao realationsDao;
	ColumnsDao columnsDao;
	private Text text;

	/**
	 * @wbp.parser.constructor
	 */
	public RelationDialog(Shell parentShell, Relationsdetail relationsdetail) {
		super(parentShell);
		this.relationsdetail = relationsdetail;
		projectDao = new ProjectDAOImpl();
		realationsDao = new RelationsDAOImpl();
		columnsDao = new ColumnsDAOImpl();
	}

	public RelationDialog(Shell parentShell, Projectdetails projectdetails) {
		super(parentShell);
		this.projectdetails = projectdetails;
		projectDao = new ProjectDAOImpl();
		realationsDao = new RelationsDAOImpl();
		columnsDao = new ColumnsDAOImpl();
	}

	public RelationDialog(Shell parentShell, Columnsdetail sourceColumn) {
		super(parentShell);
		this.columnsdetail = sourceColumn;
		projectDao = new ProjectDAOImpl();
		realationsDao = new RelationsDAOImpl();
		columnsDao = new ColumnsDAOImpl();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Import Connections");
		container.setLayout(null);

		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setBounds(5, 8, 72, 15);
		lblProjectName.setText("Project Name");

		projectNameCombo = new Combo(container, SWT.NONE);
		projectNameCombo.setBounds(87, 5, 439, 21);

		projectNameCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				assignSchemaValues();

			}
		});

		addProjectsToCombo();

		Label lblSchemaName = new Label(container, SWT.NONE);
		lblSchemaName.setBounds(5, 34, 77, 15);
		lblSchemaName.setText("Schema Name");

		schemaNameCombo = new Combo(container, SWT.NONE);
		schemaNameCombo.setBounds(87, 31, 439, 21);

		schemaNameCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//assignSourceTableCombo();
			}
		});

		if (relationsdetail != null)
			fillByEditOption();

		Label lblSourceTable = new Label(container, SWT.NONE);
		lblSourceTable.setBounds(5, 115, 68, 15);
		lblSourceTable.setText("Source Table");

		sourceTableCombo = new Combo(container, SWT.NONE);
		sourceTableCombo.setBounds(5, 138, 132, 23);

		sourceTableCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				assignSourceColumn();
			}

		});

		Label lblSourceColumn = new Label(container, SWT.NONE);
		lblSourceColumn.setBounds(5, 172, 91, 15);
		lblSourceColumn.setText("Source Column");

		Label lblRelationshipDefination = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblRelationshipDefination.setText("Relationship Defination");
		lblRelationshipDefination.setBounds(5, 90, 508, 2);

		sourceColumn = new Combo(container, SWT.NONE);
		sourceColumn.setBounds(5, 199, 132, 23);

		Label lblRelationType = new Label(container, SWT.NONE);
		lblRelationType.setBounds(227, 138, 82, 15);
		lblRelationType.setText("Relation Type");

		relationTypeCombo = new Combo(container, SWT.NONE);
		relationTypeCombo.setBounds(206, 164, 132, 23);

		for (RelationType type : RelationType.values()) {
			relationTypeCombo.add(type.toString());
			relationTypeCombo.setData(type.toString(), type);
		}

		Label lblTargetTable = new Label(container, SWT.NONE);
		lblTargetTable.setText("Target Table");
		lblTargetTable.setBounds(389, 115, 68, 15);

		targetTableCombo = new Combo(container, SWT.NONE);
		targetTableCombo.setBounds(389, 138, 132, 23);

		targetTableCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				targetColCombo.removeAll();
				List<Columnsdetail> columnsdetails = new ArrayList<>();
				Tabledetail tabledetail = (Tabledetail) targetTableCombo.getData(targetTableCombo.getText());
				columnsdetails = tabledetail.getColumnsdetails();
				for (Columnsdetail columnsdetail : columnsdetails) {
					targetColCombo.add(columnsdetail.getName());
					targetColCombo.setData(columnsdetail.getName(), columnsdetail);
				}
			}
		});

		Label lblTargetColumn = new Label(container, SWT.NONE);
		lblTargetColumn.setText("Target Column");
		lblTargetColumn.setBounds(389, 172, 91, 15);

		targetColCombo = new Combo(container, SWT.NONE);
		targetColCombo.setBounds(389, 199, 132, 23);

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setBounds(5, 63, 77, 15);
		lblDescription.setText("Description");

		text = new Text(container, SWT.BORDER);
		text.setBounds(87, 60, 439, 21);

		assignColumnPreDefinedValues();
		assignProjectPreDefinedValues();

		return container;
	}

	private void assignColumnPreDefinedValues() {
		if (columnsdetail != null) {
			Projectdetails projectdetails = columnsdetail.getTabledetail().getSchemadetail()
					.getAssociatedProjectDetail();
			if (projectdetails != null) {
				projectNameCombo.setText(projectdetails.getProjectName());
				projectNameCombo.setEnabled(false);
			}
			assignSchemaValues();
			if (projectdetails.getSchemadetail() != null) {
				schemaNameCombo.setText(projectdetails.getSchemadetail().getName());
				schemaNameCombo.setEnabled(false);
			}
			assignSourceTableCombo();
			if (columnsdetail != null) {
				sourceTableCombo.setText(columnsdetail.getTabledetail().getTableName());
				sourceTableCombo.setEnabled(false);
			}
			assignSourceColumn();
			if (columnsdetail != null) {
				sourceColumn.setText(columnsdetail.getName());
				sourceColumn.setEnabled(false);
			}
			//assignRelationTargetTable();
		}
	}

	private void assignProjectPreDefinedValues() {
		if (projectdetails != null) {
			if (projectdetails != null) {
				projectNameCombo.setText(projectdetails.getProjectName());
				projectNameCombo.setEnabled(false);
			}
			if (projectdetails.getSchemadetail() != null) {
				schemaNameCombo.setText(relationsdetail.getProjectdetail().getSchemadetail().getName());
				schemaNameCombo.setEnabled(false);
			}
		}
	}

	private void fillByEditOption() {
		if (relationsdetail.getProjectdetail() != null) {
			projectNameCombo.setText(relationsdetail.getProjectdetail().getProjectName());
			projectNameCombo.setEnabled(false);
			if (relationsdetail.getProjectdetail().getSchemadetail() != null) {
				schemaNameCombo.setText(relationsdetail.getProjectdetail().getSchemadetail().getName());
				schemaNameCombo.setEnabled(false);

			}
		}
	}

	private void addProjectsToCombo() {
		List<Projectdetails> projectDetails = new ArrayList<>();
		try {
			projectDetails = projectDao.getAllProjectdetailsinDB();
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}
		projectNameCombo.removeAll();
		for (Projectdetails projectdetails : projectDetails) {
			projectNameCombo.add(projectdetails.getProjectName());
			projectNameCombo.setData(projectdetails.getProjectName(), projectdetails);
		}
	}

	public void assignSourceColumn() {
		Tabledetail tabledetail = (Tabledetail) sourceTableCombo.getData(sourceTableCombo.getText());
		sourceColumn.removeAll();
		for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
			sourceColumn.add(columnsdetail.getName());
			sourceColumn.setData(columnsdetail.getName(), columnsdetail);

		}
	}

	public void assignSchemaValues() {
		Schemadetail schemadetail;
		Projectdetails projectdetails = (Projectdetails) projectNameCombo.getData(projectNameCombo.getText());
		schemadetail = projectdetails.getSchemadetail();
		schemaNameCombo.removeAll();
		schemaNameCombo.add(schemadetail.getName());
		schemaNameCombo.setData(schemadetail.getName(), schemadetail);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		try {

			Relationsdetail relationsdetail = new Relationsdetail();
			relationsdetail.setType((RelationType) relationTypeCombo.getData(relationTypeCombo.getText()));
			relationsdetail.setProjectdetail((Projectdetails) projectNameCombo.getData(projectNameCombo.getText()));
			relationsdetail.setTabledetail((Tabledetail) targetTableCombo.getData(targetTableCombo.getText()));
			relationsdetail.setRelatedColumndetail((Columnsdetail) targetColCombo.getData(targetColCombo.getText()));
			relationsdetail.setColumnsdetail((Columnsdetail) sourceColumn.getData(sourceColumn.getText()));
			relationsdetail.setDescription(text.getText());
			realationsDao.saveRelation(relationsdetail);
			RefrehTreeTask refrehTreeTask = new RefrehTreeTask();
			refrehTreeTask.execute();
			super.okPressed();
		} catch (PersistException e) {
			e.printStackTrace();
		}
	}

	public void assignSourceTableCombo() {
		Schemadetail schemadetail = (Schemadetail) schemaNameCombo.getData(schemaNameCombo.getText());
		List<Tabledetail> tabledetails = new ArrayList<>();
		tabledetails.addAll(schemadetail.getTabledetails());
		targetTableCombo.removeAll();
		sourceTableCombo.removeAll();
		for (Tabledetail tabledetail : tabledetails) {
			sourceTableCombo.add(tabledetail.getTableName());
			sourceTableCombo.setData(tabledetail.getTableName(), tabledetail);
			targetTableCombo.add(tabledetail.getTableName());
			targetTableCombo.setData(tabledetail.getTableName(), tabledetail);
		}
	}

	private void assignRelationTargetTable() {
		targetTableCombo.removeAll();
		for (Tabledetail tabledetail : Master.INSTANCE.getSortedTableInLoadOrder()) {
			if(tabledetail.getTableName().equals(sourceTableCombo.getText())){
				return;
			}
			targetTableCombo.add(tabledetail.getTableName());
			targetTableCombo.setData(tabledetail.getTableName(), tabledetail);
		}

	}
}
