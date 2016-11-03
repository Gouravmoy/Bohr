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

public class RegenerateUKForFK {
	BufferedWriter[] tempBufferedWriter;
	List<GeneratedColumn> ukFkColumns = new ArrayList<>();
	BufferedWriter uniqueKeyWriter;
	String[][] ukArray;
	static String combinationFilePath;
	int numberOfRows;

	static String tempFolder = "C:\\Users\\M1026352\\Desktop\\DataGn\\Export\\";

	public void regenerate() {
		String[] tempFilePath = new String[ukFkColumns.size()];
		BufferedReader reader = null;
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
			int step = (int) (lineCount / numberOfRows) != 0 ? (int) (lineCount / numberOfRows) : 1;
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
				File targetFile = new File(ukFkColumns.get(i).filePath.replace(ukFkColumns.get(i).getColName(),
						ukFkColumns.get(i).getColName() + "_UK_FK"));
				ukFkColumns.get(i).setFilePath(targetFile.getPath());
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

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

}
