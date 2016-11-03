package views.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class StatusDialog extends Dialog {

	String baseMessage;
	static Label lblNewLabel;

	protected StatusDialog(Shell parentShell,String baseMessage) {
		super(parentShell);
		this.baseMessage = baseMessage;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.getShell().setText("Table Generation");
		container.setLayout(null);

		Label lblGeneratingTestData = new Label(container, SWT.NONE);
		lblGeneratingTestData.setBounds(10, 22, 121, 15);
		lblGeneratingTestData.setText(baseMessage);

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(137, 22, 213, 15);
		lblNewLabel.setText("Starting..");
		return container;
	}


	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}

	public static void updateTableName(String tableName) {
		lblNewLabel.setText(tableName);
	}

}
