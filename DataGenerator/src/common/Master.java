package common;

import java.util.List;

import entity.GeneratedTableData;
import enums.Environment;

public enum Master {
	INSTANCE;

	private Environment environment;

	private List<GeneratedTableData> generatedTableData;
	private GeneratedTableData currentGeneratedData;
	public boolean clearAll;

	public boolean isClearAll() {
		return clearAll;
	}

	public void setClearAll(boolean clearAll) {
		this.clearAll = clearAll;
	}

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
