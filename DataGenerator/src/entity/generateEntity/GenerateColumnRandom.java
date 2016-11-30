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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import entity.Conditions;

public class GenerateColumnRandom extends GeneratedColumn {
	boolean isNullable = false;
	boolean generateAllUnique = true;
	BufferedWriter writer;
	int nextSeqNo = 0;
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final int N = alphabet.length();

	public void generateColumn() {
		try {
			if(colName.equals("Province")){
				System.out.println("Here");
			}
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

		// final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// final int N = alphabet.length();
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		try {
			if (condition == null) {
				setDefaultValues();
			}
			switch (columnType) {
			case VARCHAR:
			case CHAR:
				while (recordCount > 0) {
					builder = new StringBuilder();
					builder.append("\"");
					builder.append(generateVarchar());
					builder.append("\"");
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				nextSeqNo = 0;
				break;
			case INTEGER:
				while (recordCount > 0) {
					builder = new StringBuilder();
					int minimum = (int) condition.getLowerLimit();
					int maximum = (int) condition.getUpperLimit();
					builder.append((r.nextInt(maximum - minimum) + minimum));
					builder.append("\n");
					writer.write(builder.toString());
					recordCount--;
					if (recordCount % 50 == 0) {
						writer.flush();
					}
				}
				break;
			case FLOAT:
				while (recordCount > 0) {
					builder = new StringBuilder();
					float minX = (float) condition.getLowerLimit();
					float maxX = (float) condition.getUpperLimit();
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
				upperBound = (int) (condition.getUpperLimit() <= upperBound ? condition.getUpperLimit() : upperBound);
				while (recordCount > 0) {
					builder = new StringBuilder();
					String finalXDec = getRandomValue(r, (int) condition.getLowerLimit(), upperBound, colDecLenght);
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
				String finalDateFormat = "yyyy-MM-dd hh:mm:ss";
				SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateFormat);
				String startDate = dateFormat.format(condition.getDateLowerLimit());
				String endDate = dateFormat.format(condition.getDateUpperLimit());
				long rangebegin = Timestamp.valueOf(startDate).getTime();
				long rangeend = Timestamp.valueOf(endDate).getTime();
				long diff = rangeend - rangebegin + 1;
				while (recordCount > 0) {
					builder = new StringBuilder();
					Timestamp rand = new Timestamp(rangebegin + (long) (Math.random() * diff));
					Date date = new Date(rand.getTime());
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
				String finalDateFormat1 = "yyyy";
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(finalDateFormat1);
				String startDate1 = dateFormat1.format(condition.getDateLowerLimit());
				String endDate1 = dateFormat1.format(condition.getDateUpperLimit());
				long rangebegin1 = Long.parseLong(startDate1);
				long rangeend1 = Long.parseLong(endDate1);
				long diff1 = rangeend1 - rangebegin1 + 1;
				while (recordCount > 0) {
					builder = new StringBuilder();
					Timestamp rand1 = new Timestamp(rangebegin1 + (long) (Math.random() * diff1));
					Date date1 = new Date(rand1.getTime());
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
		} catch (IllegalArgumentException err) {
			err.printStackTrace();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return builder.toString();

	}

	private void setDefaultValues() throws ParseException {
		condition = new Conditions();
		condition.setConditionDesc("DEFAULT CONDITIONS");
		condition.setStartWith(null);
		condition.setEndsWith(null);
		condition.setGenerateRandom(true);
		condition.setSequenceNo(0);
		condition.setSizeLimit((int) (colLength <= 10 ? colLength : 10));
		condition.setUpperLimit(100);
		condition.setLowerLimit(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date parsed = format.parse("20000101");
		parsed = format.parse("20000101");
		condition.setDateLowerLimit(new java.sql.Date(parsed.getTime()));
		parsed = format.parse("21000101");
		condition.setDateUpperLimit(new java.sql.Date(parsed.getTime()));
	}

	private String generateVarchar() {
		int preFixLen = 0;
		int postFixLen = 0;
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		if (colName.equals("City")) {
			System.out.println("Debug");
		}

		if (condition.getSizeLimit() != 0) {
			int size = (int) (condition.getSizeLimit() <= colLength ? condition.getSizeLimit() : colLength);
			if (condition.getSizeLimit() > 0) {
				if (condition.getStartWith() != null)
					preFixLen = condition.getStartWith().length();
				if (condition.getEndsWith() != null)
					postFixLen = condition.getEndsWith().length();
				if (preFixLen > 0 && postFixLen > 0) {
					if (preFixLen + postFixLen >= colLength) {
						String returnValue = condition.getStartWith() + condition.getEndsWith();
						return returnValue.substring(0, (int) colLength);
					} else {
						int noOfRandom = (int) (colLength - (preFixLen + postFixLen));
						builder = new StringBuilder();
						for (int i = 0; i < noOfRandom; i++) {
							builder.append(alphabet.charAt(r.nextInt(N)));
						}
						return condition.getStartWith() + builder.toString() + condition.getEndsWith();
					}
				} else if (preFixLen > 0) {
					builder.append(generatePreFix(size, (int) colLength, true));
				} else if (postFixLen > 0) {
					builder.append(generatePreFix(size, (int) colLength, false));
				} else {
					if (condition.getSequenceNo() > 0) {
						if (nextSeqNo == 0) {
							nextSeqNo = condition.getSequenceNo();
						}
						int randomToGenerate = (int) (size - (int) (Math.log10(nextSeqNo) + 1));
						for (int i = 0; i < randomToGenerate; i++) {
							builder.append(alphabet.charAt(r.nextInt(N)));
						}
						if (!condition.isSequencePreFix()) {
							return builder.toString() + (nextSeqNo++) + "";
						} else {
							return (nextSeqNo++) + builder.toString() + "";
						}
					}else{
						for (int i = 0; i < size; i++) {
							builder.append(alphabet.charAt(r.nextInt(N)));
						}
					}
				}
			} else {
				return "";
			}
		} else {
			return "";
		}
		return builder.toString();
	}

	public String generatePreFix(int length, int max, boolean isPreFix) {
		StringBuilder builder = new StringBuilder();
		Random r = new Random();
		String returnValue = "";
		if (condition.getSequenceNo() < 0) {
			builder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				builder.append(alphabet.charAt(r.nextInt(N)));
			}
			if (isPreFix) {
				int randomToGenerate = (int) (length - (condition.getStartWith().length()));
				returnValue = condition.getStartWith() + builder.toString().substring(0, randomToGenerate);
				if (returnValue.length() > max)
					return returnValue.substring(0, max);
				else
					return returnValue;
			} else {
				int randomToGenerate = (int) (length - (condition.getEndsWith().length()));
				returnValue = builder.toString().substring(0, randomToGenerate) + condition.getEndsWith();
				if (returnValue.length() > max)
					return returnValue.substring(returnValue.length() - max, returnValue.length());
				else
					return returnValue;
			}
		} else {
			builder = new StringBuilder();
			int size = (int) (condition.getSizeLimit() <= colLength ? condition.getSizeLimit() : colLength);
			if (nextSeqNo == 0) {
				nextSeqNo = condition.getSequenceNo();
			}
			if (isPreFix) {
				int randomToGenerate = (int) (size
						- (condition.getStartWith().length() + (int) (Math.log10(nextSeqNo) + 1)));
				for (int i = 0; i < randomToGenerate; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				returnValue = condition.getStartWith() + builder.toString() + (nextSeqNo++) + "";
			} else {
				int randomToGenerate = (int) (size
						- (condition.getEndsWith().length() + (int) (Math.log10(nextSeqNo) + 1)));
				for (int i = 0; i < randomToGenerate; i++) {
					builder.append(alphabet.charAt(r.nextInt(N)));
				}
				returnValue = (nextSeqNo++) + builder.toString() + condition.getEndsWith() + "";
			}
			if (returnValue.length() > size) {
				int startValue = returnValue.length() - size;
				return returnValue.substring(startValue, returnValue.length());
			} else {
				return returnValue;
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