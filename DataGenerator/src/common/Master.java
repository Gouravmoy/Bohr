package common;

import enums.Environment;

public enum Master {
	INSTANCE;

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

}
