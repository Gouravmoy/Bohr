package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnPrimaryKey extends GeneratedColumn implements GenerateColumnInterface {
	int startValue;
	String startValueString;
	boolean foreignKey;

	public void generateColumn() {

	}

	public int getStartValue() {
		return startValue;
	}

	public void setStartValue(int startValue) {
		this.startValue = startValue;
	}

	public String getStartValueString() {
		return startValueString;
	}

	public void setStartValueString(String startValueString) {
		this.startValueString = startValueString;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	@Override
	public String toString() {
		return "GenerateColumnPrimaryKey [startValue=" + startValue + ", startValueString=" + startValueString
				+ ", foreignKey=" + foreignKey + "]" + super.toString();
	}

}
