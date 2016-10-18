
package datagenerator.parts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditBindings;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import common.Master;
import dataProvider.TwoDimensionalArrayDataProvider;
import entity.Columnsdetail;
import entity.GeneratedTableData;

public class DisplayTableValuesPart {
	GeneratedTableData generatedTableData;
	String[] propertyNames;
	Map<String, String> propertyToLabelMap;

	@Inject
	public DisplayTableValuesPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		generatedTableData = Master.INSTANCE.getCurrentGeneratedData();
		int coluCount = 0;
		propertyNames = new String[generatedTableData.getTable().getColumnsdetails().size()];
		propertyToLabelMap = new HashMap<>();
		for (Columnsdetail columnsdetail : generatedTableData.getTable().getColumnsdetails()) {
			propertyNames[coluCount] = columnsdetail.getName();
			propertyToLabelMap.put(columnsdetail.getName(), columnsdetail.getName().toUpperCase());
			coluCount++;
		}
		IDataProvider bodyDataProvider = new TwoDimensionalArrayDataProvider(generatedTableData);
		DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);
		SelectionLayer selectionLayer = new SelectionLayer(bodyDataLayer);
		ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);
		viewportLayer.setRegionName(GridRegion.BODY);

		IDataProvider headerDataProvider = new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap);
		DataLayer headerDataLayer = new DataLayer(headerDataProvider);
		ILayer columnHeaderLayer = new ColumnHeaderLayer(headerDataLayer, viewportLayer, selectionLayer);

		CompositeLayer compositeLayer = new CompositeLayer(1, 2);
		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, columnHeaderLayer, 0, 0);
		compositeLayer.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		NatTable natTable = new NatTable(parent, NatTable.DEFAULT_STYLE_OPTIONS | SWT.BORDER, compositeLayer, false);

		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());

		bodyDataLayer.addConfiguration(new DefaultEditBindings());
		bodyDataLayer.addConfiguration(new DefaultEditConfiguration());
		bodyDataLayer.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.ALWAYS_EDITABLE);
			}

		});

		natTable.configure();
	}

}