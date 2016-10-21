package entity.generateEntity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import entity.Interface.GenerateColumnInterface;

public class GenerateColumnRandom extends GeneratedColumn implements GenerateColumnInterface {
	boolean isNullable = false;
	boolean generateAllUnique = true;

	public void generateColumn() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
			BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
			String randomValue = "";
			int currentLineNumber = 0;
			int newLineStartNumber = 0;
			currentLineNumber = getLastLineNo();
			newLineStartNumber = currentLineNumber + 1;
			if (newLineStartNumber % 5 == 0) {
				writer.write("");
			} else {
				if (!generateAllUnique)
					writer.write(generateRandomValue());
				else {
					generateRandomUnique(writer, reader, randomValue);
				}
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void generateRandomUnique(BufferedWriter writer, BufferedReader reader, String randomValue)
			throws IOException {
		String line;
		boolean matched;
		matched = false;
		while (!matched) {
			randomValue = generateRandomValue();
			while ((line = reader.readLine()) != null) {
				line = line.replace(",", "");
				if (randomValue.equals(line))
					matched = true;
			}
		}
		writer.write(randomValue);
	}

	private int getLastLineNo() throws FileNotFoundException, IOException {
		int currentLineNumber;
		LineNumberReader lnr = new LineNumberReader(new FileReader(new File("File1")));
		lnr.skip(Long.MAX_VALUE);
		currentLineNumber = lnr.getLineNumber() + 1;
		lnr.close();
		return currentLineNumber;
	}

	private String generateRandomValue() {

		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int N = alphabet.length();
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		boolean usePreviousKey = false;

		try {
			switch (columnType) {
			case CHAR:
				int size = (int) (colLength <= 10 ? colLength : 10);
				builder.append("\"");
				for (int i = 0; i < size; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				builder.append("\"");
				break;
			case VARCHAR:
				int sizeVarchar = (int) (colLength <= 10 ? colLength : 10);
				builder.append("\"");
				for (int i = 0; i < sizeVarchar; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				builder.append("\"");
				break;
			case INTEGER:
				if (!usePreviousKey) {
					builder.append("" + (r.nextInt(((2 ^ 31) - 0) + 1) + 0) + 1);
				} else {
					// String previousKey =
					// keysData.getValues().get(keysData.getValues().size() -
					// 1);
					// builder.append("" + (Integer.parseInt(previousKey) + 1));
				}
				break;
			case FLOAT:
				float minX = 50.0f;
				float maxX = 100.0f;
				float finalX = r.nextFloat() * (maxX - minX) + minX;
				if (!usePreviousKey) {
					builder.append("" + finalX);
				}
				break;
			case TINYINT:
				if (!usePreviousKey) {
					builder.append("" + r.nextInt(128));
				} else {
					// String previousKey =
					// keysData.getValues().get(keysData.getValues().size() -
					// 1);
					// builder.append("" + (Integer.parseInt(previousKey) + 1));
				}
				break;
			case DATE:
				String startDate = "2013-02-08 00:00:00";
				String endDate = "2016-02-08 00:58:00";
				String finalDateFormat = "yyyy-MM-dd hh:mm:ss";
				long rangebegin = Timestamp.valueOf(startDate).getTime();
				long rangeend = Timestamp.valueOf(endDate).getTime();
				long diff = rangeend - rangebegin + 1;
				Timestamp rand = new Timestamp(rangebegin + (long) (Math.random() * diff));
				Date date = new Date(rand.getTime());
				SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateFormat);
				builder.append("\"");
				builder.append("" + dateFormat.format(date));
				builder.append("\"");
				break;
			case YEAR:
				String startDate1 = "2013-02-08 00:00:00";
				String endDate1 = "2016-02-08 00:58:00";
				String finalDateFormat1 = "yyyy";
				long rangebegin1 = Timestamp.valueOf(startDate1).getTime();
				long rangeend1 = Timestamp.valueOf(endDate1).getTime();
				long diff1 = rangeend1 - rangebegin1 + 1;
				Timestamp rand1 = new Timestamp(rangebegin1 + (long) (Math.random() * diff1));
				Date date1 = new Date(rand1.getTime());
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(finalDateFormat1);
				builder.append("\"");
				builder.append("" + dateFormat1.format(date1));
				builder.append("\"");
				break;
			default:
				System.out.println("Incorrect Data Type");
				break;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return builder.toString();

	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isGenerateAllUnique() {
		return generateAllUnique;
	}

	public void setGenerateAllUnique(boolean generateAllUnique) {
		this.generateAllUnique = generateAllUnique;
	}

}
