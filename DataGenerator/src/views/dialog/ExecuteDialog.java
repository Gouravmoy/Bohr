package views.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import dao.ProjectDao;
import dao.impl.ProjectDAOImpl;
import entity.Projectdetails;
import entity.Tabledetail;
import exceptions.ReadEntityException;
import job.GenerateDataJob2;

public class ExecuteDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Spinner spinnerAll;
	private Table table;
	public List<Tabledetail> tabledetails;
	public List<Tabledetail> selectedTabledetails;
	List<Projectdetails> projectdetails;
	ProjectDao projectDao;
	boolean createColumn = true;
	TableEditor editor;
	private static String COLUMN_NAMES[] = { "Table Name", "No of Rows" };
	Map<String, Integer> tableCount;

	public ExecuteDialog(Shell parentShell) {
		super(parentShell);
		tabledetails = new ArrayList<>();
		projectdetails = new ArrayList<>();
		selectedTabledetails = new ArrayList<>();
		projectDao = new ProjectDAOImpl();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		tableCount = new HashMap<String, Integer>();
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Import Connections");
		container.setLayout(null);

		Label lblProject_1 = new Label(container, SWT.NONE);
		lblProject_1.setBounds(10, 179, 37, 15);
		lblProject_1.setText("Project");

		Combo combo = new Combo(container, SWT.NONE);
		combo.setBounds(176, 176, 328, 23);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Projectdetails projectdetail = (Projectdetails) combo.getData(combo.getText());
				tabledetails.clear();
				tabledetails.addAll(projectdetail.getSchemadetail().getTabledetails());
				selectedTabledetails.addAll(tabledetails);
				createTable(parent);
			}
		});

		try {
			projectdetails = projectDao.getAllProjectdetailsinDB();
			for (Projectdetails projectdetail : projectdetails) {
				combo.setData(projectdetail.getProjectName(), projectdetail);
				combo.add(projectdetail.getProjectName());
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

		Label lblSelectTables = new Label(container, SWT.NONE);
		lblSelectTables.setBounds(10, 208, 68, 15);
		lblSelectTables.setText("Select Tables");

		table = new Table(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		editor = new TableEditor(table);
		table.setBounds(176, 205, 328, 210);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		Button btnSelectAll = new Button(container, SWT.NONE);
		btnSelectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTabledetails.clear();
				selectedTabledetails.addAll(tabledetails);
			}
		});
		btnSelectAll.setBounds(174, 421, 75, 25);
		btnSelectAll.setText("Select All");

		Button btnDeselectAll = new Button(container, SWT.NONE);
		btnDeselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTabledetails.clear();
				table.removeAll();
				createTable(parent);
			}
		});
		btnDeselectAll.setBounds(263, 421, 75, 25);
		btnDeselectAll.setText("Deselect All");
		// createTable(parent);

		Group grpUserInput = new Group(container, SWT.NONE);
		grpUserInput.setText("EXECUTION");
		grpUserInput.setBounds(5, 10, 504, 152);

		Label lblProject = new Label(grpUserInput, SWT.NONE);
		lblProject.setBounds(10, 30, 133, 15);
		lblProject.setText("Execution Instance Name");

		text = new Text(grpUserInput, SWT.BORDER);
		text.setBounds(171, 27, 323, 21);

		text_1 = new Text(grpUserInput, SWT.BORDER);
		text_1.setBounds(171, 54, 323, 59);

		Label lblDescription = new Label(grpUserInput, SWT.NONE);
		lblDescription.setBounds(10, 57, 60, 15);
		lblDescription.setText("Description");

		Label lblNoOfRows = new Label(grpUserInput, SWT.NONE);
		lblNoOfRows.setBounds(10, 124, 156, 15);
		lblNoOfRows.setText("No of Rows to Generate");

		spinnerAll = new Spinner(grpUserInput, SWT.BORDER);
		spinnerAll.setBounds(171, 119, 60, 22);
		spinnerAll.setMaximum(500);
		spinnerAll.setMinimum(0);
		spinnerAll.setIncrement(10);

		return container;
	}

	private void createTable(Composite parent) {

		if (createColumn) {
			for (int i = 0; i < COLUMN_NAMES.length; i++) {
				TableColumn column = new TableColumn(table, SWT.CENTER, i);
				column.setText(COLUMN_NAMES[i]);
				column.setWidth(200);
			}
			createColumn = false;
		}
		for (int i = 0; i < tabledetails.size(); i++) {
			Tabledetail tabledetail = tabledetails.get(i);
			TableItem items = new TableItem(table, SWT.NONE);
			TableEditor editor = new TableEditor(table);
			editor = new TableEditor(table);
			Spinner spinner = new Spinner(table, SWT.NONE);
			spinner.setIncrement(5);
			text.setText(spinner.getText());
			editor.grabHorizontal = true;
			editor.setEditor(spinner, items, 1);
			editor = new TableEditor(table);
			items.setText(0, tabledetail.getTableName());
			spinner.setSelection(Integer.parseInt(spinnerAll.getText()));
			spinner.setData(tabledetail);
			spinner.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					Spinner spinner = (Spinner) arg0.getSource();
					Tabledetail tabledetail = (Tabledetail) spinner.getData();
					System.out.println(tabledetail);
					System.out.println(tabledetail.getTableName() + "  :  " + Integer.parseInt(spinner.getText()));
					tableCount.put(tabledetail.getTableName(), Integer.parseInt(spinner.getText()));

				}
			});
			if (selectedTabledetails.contains(tabledetail)) {
				items.setData(tabledetail);
				items.setChecked(true);
			}
		}
		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean isRemove = false;
				int pos = 0;
				int count = 0;
				if (event.detail == SWT.CHECK) {
					Tabledetail tabledetail = (Tabledetail) event.item.getData();
					for (Tabledetail tabledetail2 : selectedTabledetails) {
						System.out.println(tabledetail2);
						System.out.println(tabledetail + "Table details");
						if (tabledetail2.getIdtabledetails() == (tabledetail.getIdtabledetails())) {
							isRemove = true;
							pos = count;
							break;
						}
						count++;
					}
					if (isRemove) {
						selectedTabledetails.remove(pos);
					} else {
						selectedTabledetails.add(tabledetail);
					}
					System.out.println(selectedTabledetails);
				} else {
					System.out.println("You not selected " + event.item);
				}
			}
		});

	}

	@Override
	protected void okPressed() {
		GenerateDataJob2 generateDataJob2 = new GenerateDataJob2("Generate Job");
		generateDataJob2.setSelectedTableDetails(selectedTabledetails);
		generateDataJob2.setNoOfRows(spinnerAll.getSelection());
		generateDataJob2.setTableCount(tableCount);
		generateDataJob2.schedule();
		StatusDialog dialog = new StatusDialog(getParentShell(), "Generating Test Data - ");
		dialog.open();
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}