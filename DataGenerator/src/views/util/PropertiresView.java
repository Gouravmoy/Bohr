package views.util;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;

public class PropertiresView {
	Composite composite;
	private Table table;
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new FormLayout());
		composite = new Composite(parent, SWT.EMBEDDED);
		composite.setLayout(null);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 298);
		fd_composite.right = new FormAttachment(0, 448);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lblEntityType = new Label(composite, SWT.NONE);
		lblEntityType.setBounds(5, 5, 59, 15);
		lblEntityType.setText("Entity Type");
		
		Label lblEntityName = new Label(composite, SWT.NONE);
		lblEntityName.setBounds(111, 5, 65, 15);
		lblEntityName.setText("Entity Name");
		
		Label lblEntityProperty = new Label(composite, SWT.NONE);
		lblEntityProperty.setBounds(5, 45, 78, 15);
		lblEntityProperty.setText("Entity Property");
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(5, 76, 166, 212);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}
}
