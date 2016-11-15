package views.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.PatternDao;
import dao.impl.PatternDAOImpl;
import entity.Columnsdetail;
import entity.Patterndetail;
import entity.Projectdetails;
import enums.PatternType;
import exceptions.PersistException;
import jobs.tasks.RefrehTreeTask;

public class PatternDialog extends TitleAreaDialog {
	private Text name;
	private Text description;
	private Text patternText;
	private Combo patternType;
	Columnsdetail columnsdetail;
	Projectdetails associatedProjectDetail;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public PatternDialog(Shell parentShell, Columnsdetail columnsdetail, Projectdetails associatedProjectDetail) {
		super(parentShell);
		this.columnsdetail = columnsdetail;
		this.associatedProjectDetail = associatedProjectDetail;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */

	@Override
	protected Control createDialogArea(Composite parent) {
		super.setTitle("Create Pattern");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 581;
		container.setLayoutData(gd_container);

		Group grpPatternDetails = new Group(container, SWT.NONE);
		grpPatternDetails.setLayout(new GridLayout(3, false));
		FormData fd_grpPatternDetails = new FormData();
		fd_grpPatternDetails.top = new FormAttachment(0, 10);
		fd_grpPatternDetails.left = new FormAttachment(0, 10);
		fd_grpPatternDetails.bottom = new FormAttachment(0, 146);
		fd_grpPatternDetails.right = new FormAttachment(0, 443);
		grpPatternDetails.setLayoutData(fd_grpPatternDetails);
		grpPatternDetails.setText("Pattern Details");

		Label lblPatternName = new Label(grpPatternDetails, SWT.NONE);
		lblPatternName.setText("Pattern Name");
		new Label(grpPatternDetails, SWT.NONE);

		name = new Text(grpPatternDetails, SWT.BORDER);
		name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDescription = new Label(grpPatternDetails, SWT.NONE);
		lblDescription.setText("Description");
		new Label(grpPatternDetails, SWT.NONE);

		description = new Text(grpPatternDetails, SWT.BORDER);
		description.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblType = new Label(grpPatternDetails, SWT.NONE);
		lblType.setText("Type");
		new Label(grpPatternDetails, SWT.NONE);

		patternType = new Combo(grpPatternDetails, SWT.NONE);
		patternType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (PatternType type : PatternType.values()) {
			patternType.add(type.toString());
		}

		Label lblPattern = new Label(grpPatternDetails, SWT.NONE);
		lblPattern.setText("Pattern");
		new Label(grpPatternDetails, SWT.NONE);

		patternText = new Text(grpPatternDetails, SWT.BORDER);
		patternText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(459, 412);
	}

	@Override
	protected void okPressed() {
		PatternDao patternDao = new PatternDAOImpl();
		Patterndetail patterndetail = new Patterndetail();
		patterndetail.setPatternDescription(description.getText());
		patterndetail.setPatternName(name.getText());
		patterndetail.setRegexpString(patternText.getText());
		patterndetail.setPatternType(PatternType.valueOf(patternType.getText()));
		patterndetail.setProjectdetail(associatedProjectDetail);
		patterndetail.setColumnsdetail(columnsdetail);
		try {
			patternDao.savePattern(patterndetail);
		} catch (PersistException e) {
		}
		RefrehTreeTask refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
		super.okPressed();
	}
}
