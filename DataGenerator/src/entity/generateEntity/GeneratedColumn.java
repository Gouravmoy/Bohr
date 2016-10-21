package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;
import enums.ColumnType;

abstract class GeneratedColumn implements GenerateColumnInterface {
	String colName;
	long cilLength;
	ColumnType columnType;
	String filePath;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public long getCilLength() {
		return cilLength;
	}

	public void setCilLength(long cilLength) {
		this.cilLength = cilLength;
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

}
