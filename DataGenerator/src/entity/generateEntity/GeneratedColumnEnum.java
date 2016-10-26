package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratedColumnEnum extends GeneratedColumn {

	String enumValues;
	BufferedWriter bufferedWriter;

	public void generateColumn() {
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			int rowCount = this.numberOfRows;
			String[] enumValuesList = enumValues.split("\\,");
			while (rowCount > 0) {
				bufferedWriter.write(enumValuesList[rowCount % enumValuesList.length]);
				if (rowCount % 50 == 0) {
					bufferedWriter.flush();
				}
			}
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
