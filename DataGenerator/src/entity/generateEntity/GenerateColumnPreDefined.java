package entity.generateEntity;

import java.util.Random;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnPreDefined extends GeneratedColumn implements GenerateColumnInterface {
	String preDefinedValues;

	public void generateColumn() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		try {
			String[] preDfValue = preDefinedValues.split("\\,");
			int index = random.nextInt(preDfValue.length);
			builder.append("\'");
			builder.append(preDfValue[index]);
			builder.append("\'\n");
			writeToFile(builder.toString());
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public String getPreDefinedValues() {
		return preDefinedValues;
	}

	public void setPreDefinedValues(String preDefinedValues) {
		this.preDefinedValues = preDefinedValues;
	}

}