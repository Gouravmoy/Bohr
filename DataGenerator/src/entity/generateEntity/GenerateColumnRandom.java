package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnRandom extends GeneratedColumn implements GenerateColumnInterface {
	boolean isNullable;

	public void generateColumn() {

	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

}
