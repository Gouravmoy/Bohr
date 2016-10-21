package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnRandom extends GeneratedColumn implements GenerateColumnInterface {
	boolean isNullable = false;
	boolean isUnique = false;

	public void generateColumn() {

	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	@Override
	public String toString() {
		return "GenerateColumnRandom [isNullable=" + isNullable + ", isUnique=" + isUnique + "]";
	}

}
