package dataProvider;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

import entity.GeneratedTableData;

public class TwoDimensionalArrayDataProvider implements IDataProvider {
	String[][] dataTable;
	GeneratedTableData generatedData;

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		// TODO Auto-generated method stub
		return dataTable[columnIndex][rowIndex];
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		dataTable[columnIndex][rowIndex] = (String) newValue;

	}

	@Override
	public int getColumnCount() {
		return generatedData.getTable().getColumnsdetails().size();
	}

	@Override
	public int getRowCount() {
		return generatedData.getRows().size();
	}

	public TwoDimensionalArrayDataProvider(GeneratedTableData generatedData) {
		super();
		this.generatedData = generatedData;
		dataTable = new String[generatedData.getTable().getColumnsdetails().size()][generatedData.getRows().size()];
		int rowCount = 0;
		int colCount = 0;
		for (String rowValue : generatedData.getRows()) {
			colCount = 0;
			for (String colValue : rowValue.split("\\,")) {
				setDataValue(colCount, rowCount, colValue);
				colCount++;
			}
			rowCount++;
		}
	}

}
