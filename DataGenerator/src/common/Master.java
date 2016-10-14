package common;

import enums.Environment;

public enum Master {
	INSTANCE;

	private  Environment environment;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
