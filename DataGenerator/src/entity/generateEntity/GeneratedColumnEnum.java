package entity.generateEntity;

import java.util.Random;

public class GeneratedColumnEnum extends GeneratedColumn {

	String enumValues;

	@Override
	public void generateColumn() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		try {
			String[] enums = enumValues.split("\\,");
			int index = random.nextInt(enums.length);
			builder.append("\'");
			builder.append(enums[index]);
			builder.append("\'\n");
			writeToFile(builder.toString());
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	@Override
	public String toString() {
		return "GeneratedColumnEnum [enumValues=" + enumValues + "]";
	}

}
