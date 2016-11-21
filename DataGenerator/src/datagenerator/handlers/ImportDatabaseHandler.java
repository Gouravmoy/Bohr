package datagenerator.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import views.dialog.ImportDialog;

class ImportDatabaseHandler {
	@Execute
	public void execute(Shell shell) {

		Dialog dialog = new ImportDialog(shell);
		dialog.open();
	}
}
