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

public class GeneratedColumnUniqueForeignKey extends GeneratedColumn {
	BufferedReader[] fkBufferedReaders;
	BufferedWriter[] tempBufferedWriter;
	List<GeneratedColumn> ukFkColumns = new ArrayList<>();
	BufferedWriter uniqueKeyWriter;
	String[][] ukArray;
	static String combinationFilePath;

	public void generateColumn() {
		List<String> ukFkList = new ArrayList<>();
		int count = 0;
		BufferedReader reader = null;

		for (GeneratedColumn ukFkColumn : ukFkColumns) {
			String line = "";
			int ukCount = 0;
			try {
				fkBufferedReaders[count] = new BufferedReader(new FileReader(new File(ukFkColumn.getFilePath())));
				tempBufferedWriter[count] = new BufferedWriter(new FileWriter(new File(ukFkColumn.getFilePath())));

				while ((line = reader.readLine()) != null) {
					reader = new BufferedReader(new FileReader(new File(ukFkColumn.getFilePath())));
					ukArray[count][ukCount] = line;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			count++;
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
			int step = (int) (lineCount / ukFkColumns.size());
			reader = new BufferedReader(new FileReader(new File(combinationFilePath)));
			lineCount = 0;

			BufferedWriter[] bufferedWriter;
			for (int i = 0; i < ukFkColumns.size(); i++) {

			}

			while ((line = reader.readLine()) != null) {
				lineCount++;
				if (lineCount % step != 0) {
					String[] splitValue = line.split(",");
					// for()

				} else {

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void combinations(String[][] twoDimStringArray) throws IOException {
		combinationFilePath = "C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\DataGeneration\\combinations.txt";
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
			bufferedWriter.write(sb.toString() + "\n");
			if (combinationSet.size() % 50 == 0) {
				bufferedWriter.flush();
			}
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
	}

	public BufferedReader[] getFkBufferedReaders() {
		return fkBufferedReaders;
	}

	public void setFkBufferedReaders(BufferedReader[] fkBufferedReaders) {
		this.fkBufferedReaders = fkBufferedReaders;
	}

	public List<GeneratedColumn> getUkFkColumns() {
		return ukFkColumns;
	}

	public void setUkFkColumns(List<GeneratedColumn> ukFkColumns) {
		this.ukFkColumns = ukFkColumns;
	}

}
