
package datagenerator.parts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import common.Master;
import entity.GeneratedTableData;
import jobs.tasks.AddPartTask;

public class DisplayTablePart {
	Table table;
	TableViewer tableViewer;

	@Inject
	public DisplayTablePart() {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		table = new Table(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		table.setBounds(0, 0, 517, 298);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		TableViewerColumn columnOne = new TableViewerColumn(tableViewer, SWT.NONE);
		columnOne.getColumn().setWidth(227);
		columnOne.getColumn().setText("Table Name");

		TableViewerColumn colSixthName = new TableViewerColumn(tableViewer, SWT.NONE);
		colSixthName.getColumn().setWidth(221);
		colSixthName.getColumn().setText("View/Edit values");

		columnOne.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				GeneratedTableData generatedTableData = (GeneratedTableData) element;
				return generatedTableData.getTable().getTableName();
			}
		});
		colSixthName.setLabelProvider(new CellLabelProvider() {
			Map<Object, Button> buttons = new HashMap<Object, Button>();

			@Override
			public void update(ViewerCell cell) {
				GeneratedTableData generatedTableData = (GeneratedTableData) cell.getViewerRow().getElement();
				TableItem item = (TableItem) cell.getItem();
				Button button;
				if (buttons.containsKey(cell.getElement())) {
					button = buttons.get(cell.getElement());
				} else {
					button = new Button((Composite) cell.getViewerRow().getControl(), SWT.NONE);
					button.setText("Get Report");
					button.setData(generatedTableData);
					buttons.put(cell.getElement(), button);
					button.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent arg0) {
							Master.INSTANCE.setCurrentGeneratedData((GeneratedTableData) button.getData());
							AddPartTask addPartTask = new AddPartTask("bundleclass://DataGenerator/datagenerator.parts.DisplayTableValuesPart");
							addPartTask.execute();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
						}
					});
				}
				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(button, item, cell.getColumnIndex());
				editor.layout();
			}
		});
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(Master.INSTANCE.getGeneratedTableData());

	}

}