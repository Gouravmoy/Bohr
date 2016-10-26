package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import entity.Interface.GenerateColumnInterface;
import enums.ColumnType;

public abstract class GeneratedColumn implements GenerateColumnInterface {
	String colName;
	long colLength;
	ColumnType columnType;
	String filePath;
	int numberOfRows;
	boolean fileReopen;

	@Override
	public void writeToFile(String value) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
			writer.write(value);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean isFileReopen() {
		return fileReopen;
	}

	public void setFileReopen(boolean fileReopen) {
		this.fileReopen = fileReopen;
	}

	@Override
	public String toString() {
		return "GeneratedColumn [colName=" + colName + ", colLength=" + colLength + ", columnType=" + columnType
				+ ", filePath=" + filePath + "]";
	}

}
