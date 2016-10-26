package entity.generateEntity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public class GeneratedColumnUniqueForeignKey extends GeneratedColumn {
	BufferedWriter[] tempBufferedWriter;
	List<GeneratedColumn> ukFkColumns = new ArrayList<>();
	BufferedWriter uniqueKeyWriter;
	String[][] ukArray;
	static String combinationFilePath;

	static String tempFolder = "C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\DataGeneration\\";

	public void generateColumn() {
		String[] tempFilePath = new String[ukFkColumns.size()];
		BufferedReader reader = null;
		numberOfRows = 5;// Remove this
		ukArray = new String[ukFkColumns.size()][numberOfRows];

		int rowCount = -1;
		int colCount = -1;
		for (GeneratedColumn ukFkColumn : ukFkColumns) {
			String line = "";
			try {
				rowCount++;
				colCount = -1;
				reader = new BufferedReader(new FileReader(new File(ukFkColumn.getFilePath())));
				while ((line = reader.readLine()) != null) {
					colCount++;
					ukArray[rowCount][colCount] = new String(line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			combinations(ukArray);
			reader = new BufferedReader(new FileReader(new File(combinationFilePath)));
			String line = "";
			int lineCount = 0;
			while ((line = reader.readLine()) != null) {
				lineCount++;
			}
			reader.close();
			int step = (int) (lineCount / ukFkColumns.size()) != 0 ? (int) (lineCount / ukFkColumns.size()) : 1;
			lineCount = 0;

			BufferedWriter[] bufferedWriter = new BufferedWriter[ukFkColumns.size()];
			for (int i = 0; i < ukFkColumns.size(); i++) {
				String finalPath = tempFolder + ukFkColumns.get(i).getColName() + "_temp.txt";
				bufferedWriter[i] = new BufferedWriter(new FileWriter(new File(finalPath)));
				tempFilePath[i] = finalPath;
			}
			reader = new BufferedReader(new FileReader(new File(combinationFilePath)));
			while ((line = reader.readLine()) != null) {
				lineCount++;
				if (lineCount % step == 0) {
					String[] splitValue = line.split(",");
					for (int i = 0; i < splitValue.length; i++) {
						bufferedWriter[i].write(splitValue[i] + "\n");
						if (i % 100 == 0)
							flushAllBufferedWriters(bufferedWriter[i]);
					}
				}
			}
			reader.close();
			closeAllBufferedWriters(bufferedWriter);
			for (int i = 0; i < ukFkColumns.size(); i++) {
				File sourceTempFile = new File(tempFilePath[i]);
				File targetFile = new File(ukFkColumns.get(i).filePath);
				FileUtils.copyFile(sourceTempFile, targetFile);
				sourceTempFile.delete();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeAllBufferedWriters(BufferedWriter[] bufferedWriter) {
		for (BufferedWriter writer : bufferedWriter) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void flushAllBufferedWriters(BufferedWriter bufferedWriter) {
		try {
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		String[][] ukArray = new String[2][5];
		ukArray[0][0] = "1";
		ukArray[0][1] = "2";
		ukArray[1][0] = "3";
		ukArray[1][1] = "4";
		List<String> allCombinations = new ArrayList<>();
		allCombinations.addAll(combinations(ukArray));
		for (String combination : allCombinations)
			System.out.println("Combination - " + combination);
	}

	public static Set<String> combinations(String[][] twoDimStringArray) throws IOException {
		combinationFilePath = tempFolder + "combinations.txt";
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(combinationFilePath)));
		int sizeArray[] = new int[twoDimStringArray.length];

		int counterArray[] = new int[twoDimStringArray.length];

		int totalCombinationCount = 1;
		for (int i = 0; i < twoDimStringArray.length; ++i) {
			sizeArray[i] = twoDimStringArray[i].length;
			totalCombinationCount *= twoDimStringArray[i].length;
		}

		Set<String> combinationSet = new TreeSet<>();

		StringBuilder sb;

		for (int countdown = totalCombinationCount; countdown > 0; --countdown) {
			sb = new StringBuilder();
			for (int i = 0; i < twoDimStringArray.length; ++i) {
				sb.append(twoDimStringArray[i][counterArray[i]] + ",");
			}
			if (!sb.toString().contains("null")) {
				combinationSet.add(sb.toString());
				bufferedWriter.write(sb.toString() + "\n");
			}
			// System.out.println(sb.toString() + "\n");
			for (int incIndex = twoDimStringArray.length - 1; incIndex >= 0; --incIndex) {
				if (counterArray[incIndex] + 1 < sizeArray[incIndex]) {
					++counterArray[incIndex];
					break;
				}
				counterArray[incIndex] = 0;
			}
		}
		bufferedWriter.flush();
		bufferedWriter.close();
		return combinationSet;
	}

	public List<GeneratedColumn> getUkFkColumns() {
		return ukFkColumns;
	}

	public void setUkFkColumns(List<GeneratedColumn> ukFkColumns) {
		this.ukFkColumns = ukFkColumns;
	}

}
