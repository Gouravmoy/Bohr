package views.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.PatternDao;
import dao.impl.PatternDAOImpl;
import entity.Patterndetail;
import exceptions.PersistException;

public class PatternDialog extends TitleAreaDialog {
	private Text textName;
	private Text textDesc;
	private Text textPreFix;
	Patterndetail patterndetail;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public PatternDialog(Shell parentShell, Patterndetail patterndetail) {
		super(parentShell);
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
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 581;
		container.setLayoutData(gd_container);

		Label lblPatternName = new Label(container, SWT.NONE);
		lblPatternName.setBounds(33, 62, 83, 15);
		lblPatternName.setText("Pattern Name");

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(33, 97, 83, 15);
		lblNewLabel.setText("Description");

		Label lblRegExp = new Label(container, SWT.NONE);
		lblRegExp.setBounds(33, 136, 83, 15);
		lblRegExp.setText("Reg Exp");

		textName = new Text(container, SWT.BORDER);
		textName.setBounds(297, 56, 162, 21);

		textDesc = new Text(container, SWT.BORDER);
		textDesc.setBounds(297, 91, 162, 21);

		textPreFix = new Text(container, SWT.BORDER);
		textPreFix.setBounds(297, 130, 162, 21);

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
		return new Point(700, 500);
	}

	@Override
	protected void okPressed() {
		PatternDao patternDao = new PatternDAOImpl();
		patterndetail.setPatternDescription(textDesc.getText());
		patterndetail.setPatternName(textName.getText());
		patterndetail.setRegexpString(textPreFix.getText());
		try {
			patternDao.savePattern(patterndetail);
		} catch (PersistException e) {
		}
		super.okPressed();
	}
}
