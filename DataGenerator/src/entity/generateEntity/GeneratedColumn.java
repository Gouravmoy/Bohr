package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;
import enums.ColumnType;

public abstract class GeneratedColumn implements GenerateColumnInterface {
	String colName;
	long colLength;
	ColumnType columnType;
	String filePath;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public long getColLength() {
		return colLength;
	}

	public void setColLength(long cilLength) {
		this.colLength = cilLength;
	}

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "GeneratedColumn [colName=" + colName + ", colLength=" + colLength + ", columnType=" + columnType
				+ ", filePath=" + filePath + "]";
	}

}
