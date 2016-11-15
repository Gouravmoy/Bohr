package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnPreDefined extends GeneratedColumn implements GenerateColumnInterface {
	String preDefinedValues;

	public void generateColumn() {
		StringBuilder builder;
		try {
			int rowCount = this.numberOfRows;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			if (this.fileReopen) {
				String[] preDfValue = preDefinedValues.split("\\,");
				while (rowCount > 0) {
					builder = new StringBuilder();
					builder.append("\'");
					builder.append(preDfValue[rowCount % preDfValue.length]);
					builder.append("\'\n");
					bufferedWriter.write(builder.toString());
					if (rowCount % 50 == 0) {
						bufferedWriter.flush();
					}
					rowCount--;

				}
			} else {
				String[] preDfValue = preDefinedValues.split("\\,");
				int index = preDfValue.length;
				while (index > 0) {
					index--;
					builder = new StringBuilder();
					builder.append("\'");
					builder.append(preDfValue[index]);
					builder.append("\'\n");
					bufferedWriter.write(builder.toString());
					if (rowCount % 50 == 0) {
						bufferedWriter.flush();
					}
					rowCount--;
				}
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