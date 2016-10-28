
package datagenerator.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.validate.DataValidator;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditBindings;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import common.Master;
import dataProvider.TwoDimensionalArrayDataProvider;
import entity.generateEntity.GenerateColumnRandom;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;

public class DisplayTableValuesPart {
	public static final String EDITABLE_LABEL = "editableLabel";
	public static final String NON_EDITABLE_LABEL = "nonEditableLabel";
	public static final String SECURITY_ID_EDITOR = "SecurityIdEditor";
	public static final String SECURITY_ID_CONFIG_LABEL = "SecurityIdConfigLabel";

	GeneratedTable generatedTable;
	String[] propertyNames;
	Map<String, String> propertyToLabelMap;

	@Inject
	public DisplayTableValuesPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		generatedTable = Master.INSTANCE.getCurrentGeneratedTable();
		int coluCount = 0;
		propertyNames = new String[generatedTable.getGeneratedColumn().size()];
		propertyToLabelMap = new HashMap<>();
		for (GeneratedColumn columnsdetail : generatedTable.getGeneratedColumn()) {
			propertyNames[coluCount] = columnsdetail.getColName();
			propertyToLabelMap.put(columnsdetail.getColName(), columnsdetail.getColName().toUpperCase());
			coluCount++;
		}
		IDataProvider bodyDataProvider = new TwoDimensionalArrayDataProvider(generatedTable);
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
		final ColumnOverrideLabelAccumulator columnLabelAccumulator = new ColumnOverrideLabelAccumulator(bodyDataLayer);
		bodyDataLayer.setConfigLabelAccumulator(columnLabelAccumulator);

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
		natTable.addConfiguration(editableGridConfiguration(columnLabelAccumulator, bodyDataProvider,
				generatedTable.getGeneratedColumn()));
		natTable.configure();
	}

	protected static void registerConfigLabelsOnColumns(ColumnOverrideLabelAccumulator columnLabelAccumulator,
			List<GeneratedColumn> list) {
		int i = 0;
		for (GeneratedColumn columnsdetail : list) {
			if (columnsdetail instanceof GenerateColumnRandom) {
				columnLabelAccumulator.registerColumnOverrides(i++, EDITABLE_LABEL, SECURITY_ID_EDITOR,
						SECURITY_ID_CONFIG_LABEL);
			} else {
				columnLabelAccumulator.registerColumnOverrides(i++, NON_EDITABLE_LABEL);
			}
		}
	}

	public static AbstractRegistryConfiguration editableGridConfiguration(
			final ColumnOverrideLabelAccumulator columnLabelAccumulator, final IDataProvider dataProvider,
			List<GeneratedColumn> list) {

		return new AbstractRegistryConfiguration() {

			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				DisplayTableValuesPart.registerConfigLabelsOnColumns(columnLabelAccumulator, list);
				registerISINValidator(configRegistry);
				registerEditableRules(configRegistry, dataProvider);
			}

			private void registerEditableRules(IConfigRegistry configRegistry, IDataProvider dataProvider) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.ALWAYS_EDITABLE, DisplayMode.EDIT, EDITABLE_LABEL);
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.NEVER_EDITABLE, DisplayMode.EDIT, NON_EDITABLE_LABEL);
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.ALWAYS_EDITABLE, DisplayMode.EDIT, SECURITY_ID_CONFIG_LABEL);
			}

			private void registerISINValidator(IConfigRegistry configRegistry) {

				TextCellEditor textCellEditor = new TextCellEditor();
				textCellEditor.setErrorDecorationEnabled(true);
				textCellEditor.setErrorDecorationText(
						"Security Id must be 3 alpha characters optionally followed by numbers");
				textCellEditor.setDecorationPositionOverride(SWT.TOP | SWT.CENTER);
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, textCellEditor,
						DisplayMode.NORMAL, SECURITY_ID_EDITOR);
				configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, getSecurtityIdValidator(),
						DisplayMode.EDIT, SECURITY_ID_CONFIG_LABEL);

			}

		};
	}

	private static IDataValidator getSecurtityIdValidator() {
		return new DataValidator() {

			@Override
			public boolean validate(int columnIndex, int rowIndex, Object newValue) {
				if (newValue == null) {
					return false;
				}
				String value = (String) newValue;
				if (value.length() > 3) {
					String alphabeticPart = value.substring(0, 2);
					String numericPart = value.substring(3, value.length());
					return isAlpha(alphabeticPart) && isNumeric(numericPart);
				} else {
					String alphabeticPart = value.substring(0, value.length());
					return isAlpha(alphabeticPart);
				}
			}

			private boolean isAlpha(String str) {
				for (int i = 0; i < str.length(); i++) {
					if (!Character.isLetter(str.charAt(i)))
						return false;
				}
				return true;
			}

			private boolean isNumeric(String str) {
				for (int i = 0; i < str.length(); i++) {
					if (!Character.isDigit(str.charAt(i)))
						return false;
				}
				return true;
			}
		};
	}
}
