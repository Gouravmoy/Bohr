package entity.generateEntity;

import entity.Interface.GenerateColumnInterface;

public class GeneratedColumnPredefined extends GeneratedColumn implements GenerateColumnInterface {
	String predefinedValues;

	@Override
	public void generateColumn() {

	}

	public String getPredefinedValues() {
		return predefinedValues;
	}

	public void setPredefinedValues(String predefinedValues) {
		this.predefinedValues = predefinedValues;
	}

	@Override
	public String toString() {
		return "GeneratedColumnPredefined [predefinedValues=" + predefinedValues + "]";
	}

}
