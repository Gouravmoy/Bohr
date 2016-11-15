package common;

import java.util.List;

import entity.Tabledetail;
import entity.generateEntity.GeneratedTable;
import enums.Environment;

public enum Master {
	INSTANCE;

	List<GeneratedTable> generatedTables;
	GeneratedTable currentGeneratedTable;
	
	List<Tabledetail> sortedTableInLoadOrder;

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

	public List<Tabledetail> getSortedTableInLoadOrder() {
		return sortedTableInLoadOrder;
	}

	public void setSortedTableInLoadOrder(List<Tabledetail> sortedTableInLoadOrder) {
		this.sortedTableInLoadOrder = sortedTableInLoadOrder;
	}
	
	public void printTimeElapsed(long startTime, String model) {
		long endTime = System.currentTimeMillis();
		System.out.println("Time for " + model + " Execution - " + (endTime - startTime) / 1000);

	}
	
	

}
