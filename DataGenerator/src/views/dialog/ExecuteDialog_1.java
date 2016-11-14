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

public class ExecuteDialog_1 extends Dialog {
	Combo projectCombo;
	public List<Tabledetail> tabledetails;
	public List<Tabledetail> selectedTabledetails;
	List<Projectdetails> projectdetails;
	ProjectDao projectDao;
	private static String COLUMN_NAMES[] = { "Table Name", "No of Rows" };

	public ExecuteDialog_1(Shell parentShell) {
		super(parentShell);
		tabledetails = new ArrayList<>();
		selectedTabledetails = new ArrayList<>();
		projectDao = new ProjectDAOImpl();
		try {
			projectdetails = projectDao.getAllProjectdetailsinDB();
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		projectCombo = new Combo(parent, SWT.SELECTED);
		for (Projectdetails projectdetails : projectdetails) {
			projectCombo.setData(projectdetails.getProjectName(), projectdetails);
		}

		return parent;

	}

	@Override
	protected void okPressed() {
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}
