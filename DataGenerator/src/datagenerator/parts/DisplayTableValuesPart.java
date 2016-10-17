
package datagenerator.parts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
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

		NatTable natTable = new NatTable(parent, compositeLayer);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
	}

}