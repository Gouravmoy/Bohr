package jobs.tasks.generate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import enums.ExportType;

public class GenerateTableDataWithInsertQueryTask extends Task {
	GeneratedTable generatedTableData;
	BufferedWriter bufferedWriter;
	BufferedReader bufferedReaders;
	HashMap<Integer, String> columnFilePath = new HashMap<>();
	HashMap<Integer, Boolean> fileReopen = new HashMap<>();
	ExportType exportType;
	static String AUTOCOMMIT = "SET AUTOCOMMIT=0;";
	static String INSERT = "INSERT INTO ";
	static String NEWLINE = "\n";
	static String QUOTE = "`";
	static String SEMICOLON = ";";
	static String COMMIT = "COMMIT ;";
	static String COMMA = ",";
	static String DOT = ".";
	static String OPENBRACKET = "(";
	static String CLOSEBRACKET = ")";

	public GenerateTableDataWithInsertQueryTask(GeneratedTable generatedTableData, String folderPath,
			ExportType exportType) {
		this.generatedTableData = generatedTableData;
		this.exportType = exportType;
		File floder = new File(folderPath);
		if (!floder.exists()) {
			floder.mkdir();
		}
		this.generatedTableData.setTableOutPutPath(folderPath + "\\" + generatedTableData.getTableRank() + "_");
	}

	@Override
	public void execute() throws BuildException {
		String exportFileName = generatedTableData.getTableOutPutPath() + generatedTableData.getTableName();
		try {
			bufferedReaders = new BufferedReader(new FileReader(generatedTableData.getTablePath()));
			if (exportType == ExportType.EXCEL) {
				exportFileName += ".csv";
				generateCSVTables(exportFileName);
			} else if (exportType == ExportType.INSERT_SCRIPTS) {
				exportFileName += ".sql";
				generateInsertScripts(exportFileName);
			} else if (exportType == ExportType.JSON) {
				exportFileName += ".csv";
				generateCSVTables(exportFileName);
				// exportFileName += ".json";
			}
			bufferedReaders.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateInsertScripts(String exportFileName) {
		int rowCount = 1;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(exportFileName));
			bufferedWriter.write(AUTOCOMMIT + NEWLINE);
			bufferedWriter.write(INSERT + QUOTE + generatedTableData.getSchemaName() + QUOTE + DOT + QUOTE
					+ generatedTableData.getTableName() + QUOTE + " values" + NEWLINE);
			String rowString = null;
			String completeString = "";
			while ((rowString = bufferedReaders.readLine()) != null) {
				rowString = OPENBRACKET + rowString;
				rowString = rowString + CLOSEBRACKET + COMMA + NEWLINE;
				completeString += rowString;

				if (rowCount % 49 == 0) {
					completeString = completeString.substring(0, completeString.length() - 2);
					bufferedWriter.write(completeString);
					bufferedWriter.write(COMMIT + NEWLINE);
					bufferedWriter.write(AUTOCOMMIT + NEWLINE);
					bufferedWriter.write(INSERT + QUOTE + generatedTableData.getSchemaName() + QUOTE + DOT + QUOTE
							+ generatedTableData.getTableName() + QUOTE + " values" + NEWLINE);
					bufferedWriter.flush();
					completeString = "";
				}
				rowCount++;
			}
			if (completeString.length() > 2) {
				completeString = completeString.substring(0, completeString.length() - 2);
			}
			bufferedWriter.write(completeString + NEWLINE);
			bufferedWriter.write(SEMICOLON + COMMIT + NEWLINE);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

	}

	private void generateCSVTables(String exportFileName) {
		int rowCount = 1;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(exportFileName));
			String rowString = null;
			for (GeneratedColumn columnsdetail : generatedTableData.getGeneratedColumn()) {
				bufferedWriter.write(columnsdetail.getColName() + ",");
			}
			bufferedWriter.write("\n");
			while ((rowString = bufferedReaders.readLine()) != null) {
				rowString = rowString.replace("\"", "");
				rowString = rowString.replace("\'", "");
				bufferedWriter.write(rowString + "\n");

				if (rowCount % 50 == 0) {
					bufferedWriter.flush();
				}
				rowCount++;
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}