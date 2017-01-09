package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import entity.Conditions;
import entity.Relationsdetail;
import entity.Tabledetail;
import entity.Interface.GenerateColumnInterface;
import enums.ColumnType;
import enums.KeyType;
import enums.RelationType;

public abstract class GeneratedColumn implements GenerateColumnInterface {
	String colName;
	long colLength;
	int colDecLenght;
	ColumnType columnType;
	KeyType keyType;
	String filePath;
	int numberOfRows;
	boolean fileReopen;
	Tabledetail tabledetail;
	Relationsdetail relationsdetail;
	RelationType relationType;
	Conditions condition;

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

	public KeyType getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}

	@Override
	public String toString() {
		return "GeneratedColumn [colName=" + colName + ", colLength=" + colLength + ", columnType=" + columnType
				+ ", keyType=" + keyType + ", filePath=" + filePath + ", numberOfRows=" + numberOfRows + ", fileReopen="
				+ fileReopen + "]";
	}

	public int getColDecLenght() {
		return colDecLenght;
	}

	public void setColDecLenght(int colDecLenght) {
		this.colDecLenght = colDecLenght;
	}

	public Relationsdetail getRelationsdetail() {
		return relationsdetail;
	}

	public void setRelationsdetail(Relationsdetail relationsdetail) {
		this.relationsdetail = relationsdetail;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public Tabledetail getTabledetail() {
		return tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

	public Conditions getCondition() {
		return condition;
	}

	public void setCondition(Conditions condition) {
		this.condition = condition;
	}
	
	
	
	

}
