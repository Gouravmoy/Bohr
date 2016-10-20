package datagenerator.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import views.dialog.DataModelDialog;

public class AddDataModelHandler {
	@Execute
	public void execute(Shell shell) {

		Dialog dialog = new DataModelDialog(shell);
		dialog.open();
	}
}
