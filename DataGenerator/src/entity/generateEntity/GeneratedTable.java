package entity.generateEntity;

import java.util.List;

public class GeneratedTable {

	String tableName;
	List<GeneratedColumn> generatedColumn;
	String tablePath;
	int rowCount;

	void generateTableData() {

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<GeneratedColumn> getGeneratedColumn() {
		return generatedColumn;
	}

	public void setGeneratedColumn(List<GeneratedColumn> generatedColumn) {
		this.generatedColumn = generatedColumn;
	}

	public String getTablePath() {
		return tablePath;
	}

	public void setTablePath(String tablePath) {
		this.tablePath = tablePath;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public String toString() {
		return "GeneratedTable [tableName=" + tableName + ", generatedColumn=" + generatedColumn + ", tablePath="
				+ tablePath + "]";
	}

}
