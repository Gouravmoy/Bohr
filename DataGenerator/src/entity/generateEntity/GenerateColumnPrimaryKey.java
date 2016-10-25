package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateColumnPrimaryKey extends GeneratedColumn {
	int startValue;
	String startValueString;
	boolean foreignKey;

	public void generateColumn() {
		if (!foreignKey) {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(filePath);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				int count = 0;
				while (numberOfRows > 0) {
					startValue++;
					bufferedWriter.write(startValue + "\n");
					if (count % 100 == 0) {
						bufferedWriter.flush();
					}
					numberOfRows--;
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public int getStartValue() {
		return startValue;
	}

	public void setStartValue(int startValue) {
		this.startValue = startValue;
	}

	public String getStartValueString() {
		return startValueString;
	}

	public void setStartValueString(String startValueString) {
		this.startValueString = startValueString;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	@Override
	public String toString() {
		return "GenerateColumnPrimaryKey [startValue=" + startValue + ", startValueString=" + startValueString
				+ ", foreignKey=" + foreignKey + "]" + super.toString();
	}

}
