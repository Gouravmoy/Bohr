package dataProvider;

public interface IDataProvider {
	Object getDataValue(int columnIndex, int rowIndex);

	void setDataValue(int columnIndex, int rowIndex, Object newValue);

	int getColumnCount();

}
