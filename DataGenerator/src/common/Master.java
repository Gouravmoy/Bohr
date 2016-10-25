package common;

import java.util.List;

import entity.generateEntity.GeneratedTable;
import enums.Environment;

public enum Master {
	INSTANCE;

	List<GeneratedTable> generatedTables;
	GeneratedTable currentGeneratedTable;

	private Environment environment;
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

	public List<GeneratedTable> getGeneratedTables() {
		return generatedTables;
	}

	public void setGeneratedTables(List<GeneratedTable> generatedTables) {
		this.generatedTables = generatedTables;
	}

	public GeneratedTable getCurrentGeneratedTable() {
		return currentGeneratedTable;
	}

	public void setCurrentGeneratedTable(GeneratedTable currentGeneratedTable) {
		this.currentGeneratedTable = currentGeneratedTable;
	}

}
