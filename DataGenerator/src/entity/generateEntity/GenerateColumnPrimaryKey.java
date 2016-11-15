package entity.generateEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import enums.ColumnType;

public class GenerateColumnPrimaryKey extends GeneratedColumn {
	int startValue;
	String startValueString = "";
	boolean foreignKey;

	public void generateColumn() {
		if (columnType == ColumnType.DATE)
			generateDate();
		else if (columnType == ColumnType.INTEGER)
			generateInteger();
		else
			generateVarchar();

	}

	private void generateDate() {
		if (!foreignKey) {
			FileWriter fileWriter;
			try {
				int rowCount = numberOfRows;
				StringBuilder builder = new StringBuilder();
				String startDate = "1000-02-08 00:00:00";
				String endDate = "3000-02-08 00:58:00";
				String finalDateFormat = "yyyy-MM-dd hh:mm:ss";
				long rangebegin = Timestamp.valueOf(startDate).getTime();
				long rangeend = Timestamp.valueOf(endDate).getTime();
				long diff = rangeend - rangebegin + 1;
				Timestamp rand = new Timestamp(rangebegin + (long) (Math.random() * diff));
				Date date = new Date(rand.getTime());
				SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateFormat);
				fileWriter = new FileWriter(filePath);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				while (rowCount > 0) {
					builder.append("\"");
					builder.append("" + dateFormat.format(date));
					builder.append("\"");
					if (rowCount % 100 == 0) {
						bufferedWriter.flush();
					}
					rowCount--;
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void generateVarchar() {
		if (!foreignKey) {
			FileWriter fileWriter;
			try {
				final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				final int N = alphabet.length();
				fileWriter = new FileWriter(filePath);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				Random r = new Random();
				int rowCount = numberOfRows;
				StringBuilder builder = new StringBuilder();
				colLength = colLength + startValueString.length();
				while (rowCount > 0) {
					int sizeVarchar = (int) (colLength <= 10 ? colLength : 10);
					builder.append("\"");
					for (int i = 0; i < sizeVarchar; i++) {
						builder.append(startValueString + alphabet.charAt(r.nextInt(N)));
					}
					builder.append("\"");
					if (rowCount % 100 == 0) {
						bufferedWriter.flush();
					}
					rowCount--;
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void generateInteger() {
		if (!foreignKey) {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(filePath);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				int rowCount = numberOfRows;
				while (rowCount > 0) {
					startValue++;
					bufferedWriter.write(startValue + "\n");
					if (rowCount % 100 == 0) {
						bufferedWriter.flush();
					}
					rowCount--;
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
