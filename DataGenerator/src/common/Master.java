package common;

import java.util.List;

import entity.GeneratedTableData;
import enums.Environment;

public enum Master {
	INSTANCE;

	private Environment environment;

	private List<GeneratedTableData> generatedTableData;
	private GeneratedTableData currentGeneratedData;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public List<GeneratedTableData> getGeneratedTableData() {
		return generatedTableData;
	}

	public void setGeneratedTableData(List<GeneratedTableData> generatedTableData) {
		this.generatedTableData = generatedTableData;
	}

	public GeneratedTableData getCurrentGeneratedData() {
		return currentGeneratedData;
	}

	public void setCurrentGeneratedData(GeneratedTableData currentGeneratedData) {
		this.currentGeneratedData = currentGeneratedData;
	}

}
