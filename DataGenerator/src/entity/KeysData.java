package entity;

import java.util.List;

import enums.KeyType;

public class KeysData {

	String keyName;
	KeyType keyType;
	List<String> values;

	public KeysData(String keyName, KeyType keyType) {
		super();
		this.keyName = keyName;
		this.keyType = keyType;
	}
	
	

	public KeysData() {
	}



	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public KeyType getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "KeysData [keyName=" + keyName + ", keyType=" + keyType + ", values=" + values + "]";
	}

}
