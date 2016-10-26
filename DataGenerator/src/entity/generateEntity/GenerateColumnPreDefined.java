package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnPreDefined extends GeneratedColumn implements GenerateColumnInterface {
	String preDefinedValues;

	public void generateColumn() {
		StringBuilder builder;
		Random random = new Random();
		try {
			int rowCount = this.numberOfRows;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			while (rowCount > 0) {
				builder = new StringBuilder();
				String[] preDfValue = preDefinedValues.split("\\,");
				int index = random.nextInt(preDfValue.length);
				builder.append("\'");
				builder.append(preDfValue[index]);
				builder.append("\'\n");
				bufferedWriter.write(builder.toString());
				if (rowCount % 50 == 0) {
					bufferedWriter.flush();
				}
				rowCount--;
			}
			bufferedWriter.flush();
			bufferedWriter.close();
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