package jobs.tasks.generate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;

public class GenerateTableDataTask_1 extends Task {
	GeneratedTable generatedTableData;
	BufferedWriter bufferedWriter;
	BufferedReader[] bufferedReaders;
	HashMap<Integer, String> columnFilePath = new HashMap<>();
	HashMap<Integer, Boolean> fileReopen = new HashMap<>();
	boolean wirteToText = true;

	public GenerateTableDataTask_1(GeneratedTable generatedTableData) {
		this.generatedTableData = generatedTableData;
	}

	@Override
	public void execute() throws BuildException {
		int columnCount = generatedTableData.getGeneratedColumn().size();
		bufferedReaders = new BufferedReader[columnCount];
		createFiles();
		// generation logic per row
		int rowCount = generatedTableData.getRowCount();
		try {
			String rowString = null;
			String colString;
			while (rowCount > 0 && wirteToText) {
				rowString = "";
				int colDataIntCount = 0;
				while (colDataIntCount < columnCount) {
					colString = "";
					colString = bufferedReaders[colDataIntCount].readLine();
					if (colString != null) {
						rowString += colString + ",";
					} else {
						if (fileReopen.get(colDataIntCount)) {
							bufferedReaders[colDataIntCount] = new BufferedReader(
									new FileReader(columnFilePath.get(colDataIntCount)));
							colString = bufferedReaders[colDataIntCount].readLine();
							if (colString.length() > 0) {
								rowString += colString + ",";
							} else {
								wirteToText = false;
								break;
							}
						} else {
							wirteToText = false;
							break;
						}
					}
					colDataIntCount++;
				}
				if (wirteToText) {
					rowString = rowString.substring(0, rowString.length() - 1) + "\n";
					bufferedWriter.write(rowString);
				}
				if (rowCount % 50 == 0 && wirteToText) {
					bufferedWriter.flush();
				}
				rowCount--;
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		closeAllFiles();
	}

	private void closeAllFiles() throws BuildException {
		for (BufferedReader bufferedReader : bufferedReaders) {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BuildException();
				}
			}
		}
	}

	private void createFiles() throws BuildException {
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(generatedTableData.getTablePath()));
			int colAppend = 0;
			for (GeneratedColumn generatedColumn : generatedTableData.getGeneratedColumn()) {
				bufferedReaders[colAppend] = new BufferedReader(new FileReader(generatedColumn.getFilePath()));
				columnFilePath.put(colAppend, generatedColumn.getFilePath());
				if (generatedColumn.isFileReopen()) {
					fileReopen.put(colAppend, true);
				} else {
					fileReopen.put(colAppend, false);
				}
				colAppend++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BuildException("Something went wrong for Table:" + generatedTableData.getTableName());
		}

	}
}
