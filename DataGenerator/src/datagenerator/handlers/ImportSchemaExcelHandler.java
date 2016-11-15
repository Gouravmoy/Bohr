package datagenerator.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import views.dialog.ExcelImportDialog;

public class ImportSchemaExcelHandler {
	@Execute
	public void execute(Shell shell) {

		Dialog dialog = new ExcelImportDialog(shell);
		dialog.open();
	}
}
