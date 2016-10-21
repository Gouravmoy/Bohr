package entity.generateEntity;

import java.util.Random;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnUserDefined extends GeneratedColumn implements GenerateColumnInterface {
	String userDefinedValues;

	public void generateColumn() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		try {
			String[] enums = userDefinedValues.split("\\,");
			int index = random.nextInt(enums.length);
			builder.append("\'");
			builder.append(enums[index]);
			builder.append("\'\n");
			writeToFile(builder.toString());
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public String getUserDefinedValues() {
		return userDefinedValues;
	}

	public void setUserDefinedValues(String userDefinedValues) {
		this.userDefinedValues = userDefinedValues;
	}

}
