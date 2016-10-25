package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;

public class GeneratedColumnEnum extends GeneratedColumn implements GenerateColumnInterface {

	String enumValues;

	public void generateColumn() {

	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	@Override
	public String toString() {
		return "GeneratedColumnEnum [enumValues=" + enumValues + "]";
	}

}
