package dataProvider;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

import entity.GeneratedTableData;

public class TwoDimensionalArrayDataProvider implements IDataProvider {
	private String[][] data;

	public TwoDimensionalArrayDataProvider(GeneratedTableData generatedTableData) {
		data = new String[generatedTableData.getRows().size()][generatedTableData.getTable().getColumnsdetails()
				.size()];
		int row = 0;
		int col = 0;
		for (String columnValues : generatedTableData.getRows()) {
			col = 0;
			for (String columnValue : columnValues.split("\\,")) {
				setDataValue(row, col, columnValue);
				col++;
			}
			row++;
		}
	}

	@Override
	public void setDataValue(int rowIndex, int columnIndex, Object newValue) {
		this.data[rowIndex][columnIndex] = newValue != null ? newValue.toString() : null;
	}

	@Override
	public int getColumnCount() {
		return this.data[0].length;
	}

	@Override
	public int getRowCount() {
		return this.data.length;
	}

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		return this.data[rowIndex][columnIndex];
	}

}
