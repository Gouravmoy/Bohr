package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

import entity.generateEntity.GeneratedTable;

public class TwoDimensionalArrayDataProvider implements IDataProvider {
	String[][] dataTable;
	GeneratedTable generatedData;
	BufferedReader buffredReader;

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
		return generatedData.getGeneratedColumn().size();
	}

	@Override
	public int getRowCount() {
		return 50;
	}

	public TwoDimensionalArrayDataProvider(GeneratedTable generatedTable) {
		this.generatedData = generatedTable;
		dataTable = new String[getColumnCount()][getRowCount()];
		try {
			buffredReader = new BufferedReader(new FileReader(generatedData.getTablePath()));
			String rowValue = "";
			int rowCount = 0;
			int colCount = 0;
			while ((rowValue = buffredReader.readLine()) != null) {
				colCount = 0;
				for (String colValue : rowValue.split("\\,")) {
					setDataValue(colCount, rowCount, colValue);
					colCount++;
				}
				rowCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
