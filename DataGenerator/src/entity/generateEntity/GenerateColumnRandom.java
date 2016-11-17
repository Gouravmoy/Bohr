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

import enums.PatternType;

public class GenerateColumnRandom extends GeneratedColumn {
	boolean isNullable = false;
	boolean generateAllUnique = true;
	BufferedWriter writer;
	

	public void generateColumn() {
		try {
			writer = new BufferedWriter(new FileWriter(new File(filePath), true));
			BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
			int recordCount = this.numberOfRows;
			String randomValue = "";
			int currentLineNumber = 0;
			int newLineStartNumber = 0;
			currentLineNumber = getLastLineNo();
			newLineStartNumber = currentLineNumber + 1;
			if (isNullable) {
				writer.write(null + "\n");
			} else if (newLineStartNumber == 1) {
				writer.write(generateRandomValue(recordCount) + "\n");
			} else {
				if (!generateAllUnique)
					writer.write(generateRandomValue(recordCount) + "\n");
				else {
					generateRandomUnique(writer, reader, randomValue, recordCount);
				}
			}
			recordCount--;
			if (recordCount % 50 == 0) {
				writer.flush();
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void generateRandomUnique(BufferedWriter writer, BufferedReader reader, String randomValue, int recordCount)
			throws IOException {
		String line;
		boolean matched;
		boolean willRepeat = true;
		matched = false;
		int generateTry = 0;
		while (willRepeat) {
			matched = false;
			reader = new BufferedReader(new FileReader(new File(filePath)));
			if (generateTry++ > 500) {
				System.out.println("500 attempts exceeded");
				break;
			}
			randomValue = generateRandomValue(recordCount);
			while ((line = reader.readLine()) != null) {
				line = line.replace(",", "");
				if (randomValue.equals(line)) {
					matched = true;
					break;
				}
			}
			if (matched) {
				willRepeat = true;
			} else {
				willRepeat = false;
			}
		}
		reader.close();
		System.out.println(generateTry);
		if (!(generateTry > 500))
			writer.write(randomValue + "\n");
	}

	private int getLastLineNo() throws FileNotFoundException, IOException {
		int currentLineNumber;
		LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filePath)));
		lnr.skip(Long.MAX_VALUE);
		currentLineNumber = lnr.getLineNumber();
		lnr.close();
		return currentLineNumber;
	}

	private String generateRandomValue(int recordCount) {

		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int N = alphabet.length();
		Random r = new Random();
		StringBuilder builder = new StringBuilder();

		try {
			switch (columnType) {
			case CHAR:
				while (recordCount > 0) {
					builder = new StringBuilder();
					int size = (int) (colLength <= 10 ? colLength : 10);
					builder.append("\"");
					for (int i = 0; i < size; i++) {
						builder.append(alphabet.charAt(r.nextInt(N)));
					}
					builder.append("\"");
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case VARCHAR:
				int sizeVarchar = (int) (colLength <= 10 ? colLength : 10);
				while (recordCount > 0) {
					builder = new StringBuilder();
					builder.append("\"");
					if (pattern == null) {
						for (int i = 0; i < sizeVarchar; i++) {
							builder.append(alphabet.charAt(r.nextInt(N)));
						}
					} else {
						String patternString = pattern.getRegexpString();
						generateVarcharWithPattern(alphabet, N, r, builder, sizeVarchar, patternString);
					}
					builder.append("\"");
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case INTEGER:
				int minimum = 1;
				int maximum = 1000;
				while (recordCount > 0) {
					builder = new StringBuilder();
					builder.append("" + minimum + (int) (Math.random() * maximum));
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case FLOAT:
				float minX = 50.0f;
				float maxX = 100.0f;
				while (recordCount > 0) {
					builder = new StringBuilder();
					float finalX = r.nextFloat() * (maxX - minX) + minX;
					builder.append("" + finalX);
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case DECIMAL:
				int upperBound = (int) Math.pow(10, colLength - colDecLenght);
				while (recordCount > 0) {
					builder = new StringBuilder();
					String finalXDec = getRandomValue(r, 0, upperBound, colDecLenght);
					builder.append("" + finalXDec);
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;

			case TINYINT:
				while (recordCount > 0) {
					builder = new StringBuilder();
					builder.append(0);
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case DATE:
				String startDate = "2013-02-08 00:00:00";
				String endDate = "2016-02-08 00:58:00";
				String finalDateFormat = "yyyy-MM-dd hh:mm:ss";
				long rangebegin = Timestamp.valueOf(startDate).getTime();
				long rangeend = Timestamp.valueOf(endDate).getTime();
				long diff = rangeend - rangebegin + 1;
				while (recordCount > 0) {
					builder = new StringBuilder();
					Timestamp rand = new Timestamp(rangebegin + (long) (Math.random() * diff));
					Date date = new Date(rand.getTime());
					SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateFormat);
					builder.append("\"");
					builder.append("" + dateFormat.format(date));
					builder.append("\"");
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case YEAR:
				String startDate1 = "1900-02-08 00:00:00";
				String endDate1 = "2100-02-08 00:58:00";
				String finalDateFormat1 = "yyyy";
				long rangebegin1 = Timestamp.valueOf(startDate1).getTime();
				long rangeend1 = Timestamp.valueOf(endDate1).getTime();
				long diff1 = rangeend1 - rangebegin1 + 1;
				while (recordCount > 0) {
					builder = new StringBuilder();
					Timestamp rand1 = new Timestamp(rangebegin1 + (long) (Math.random() * diff1));
					Date date1 = new Date(rand1.getTime());
					SimpleDateFormat dateFormat1 = new SimpleDateFormat(finalDateFormat1);
					builder.append("\"");
					builder.append("" + dateFormat1.format(date1));
					builder.append("\"");
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
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

	public void generateVarcharWithPattern(final String alphabet, final int N, Random r, StringBuilder builder,
			int sizeVarchar, String patternString) {
		if (pattern.getPatternType() == PatternType.PREFIX) {
			builder.append(patternString);
			for (int i = 0; i < (sizeVarchar - patternString.length()); i++) {
				builder.append(alphabet.charAt(r.nextInt(N)));
			}
			if (sizeVarchar < builder.length()) {
				String truncatedValue = builder.substring(0, sizeVarchar);
				builder.setLength(0);
				builder.append(truncatedValue);
			}
		} else {
			for (int i = 0; i < (sizeVarchar - patternString.length()); i++) {
				builder.append(alphabet.charAt(r.nextInt(N)));
			}
			builder.append(patternString);
			if (sizeVarchar < builder.length()) {
				String truncatedValue = builder.substring(builder.length() - sizeVarchar, builder.length());
				builder.setLength(0);
				builder.append(truncatedValue);
			}
		}
	}

	public static String getRandomValue(final Random random, final int lowerBound, final int upperBound,
			final int decimalPlaces) {

		if (lowerBound < 0 || upperBound <= lowerBound || decimalPlaces < 0) {
			throw new IllegalArgumentException("Put error message here");
		}

		final double dbl = ((random == null ? new Random() : random).nextDouble() //
				* (upperBound - lowerBound)) + lowerBound;
		return String.format("%." + decimalPlaces + "f", dbl);

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