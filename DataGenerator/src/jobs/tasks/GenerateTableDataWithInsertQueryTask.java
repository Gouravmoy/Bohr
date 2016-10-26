package jobs.tasks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.generateEntity.GeneratedTable;

public class GenerateTableDataWithInsertQueryTask extends Task {
	GeneratedTable generatedTableData;
	BufferedWriter bufferedWriter;
	BufferedReader bufferedReaders;
	HashMap<Integer, String> columnFilePath = new HashMap<>();
	HashMap<Integer, Boolean> fileReopen = new HashMap<>();
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

	public GenerateTableDataWithInsertQueryTask(GeneratedTable generatedTableData, String folderPath, int tableCount) {
		this.generatedTableData = generatedTableData;
		this.generatedTableData.setTableOutPutPath(folderPath + "\\" + tableCount + "_");
	}

	@Override
	public void execute() throws BuildException {
		int rowCount = 1;
		try {
			bufferedReaders = new BufferedReader(new FileReader(generatedTableData.getTablePath()));
			bufferedWriter = new BufferedWriter(new FileWriter(
					generatedTableData.getTableOutPutPath() + generatedTableData.getTableName() + ".txt"));
			bufferedWriter.write(AUTOCOMMIT + NEWLINE);
			bufferedWriter.write(INSERT + QUOTE + generatedTableData.getSchemaName() + QUOTE + DOT + QUOTE
					+ generatedTableData.getTableName() + QUOTE + " values");
			String rowString = null;
			String completeString = "";
			while ((rowString = bufferedReaders.readLine()) != null) {
				rowString = OPENBRACKET + rowString;
				rowString = rowString + CLOSEBRACKET + COMMA + NEWLINE;
				completeString += rowString;

				if (rowCount % 50 == 0) {
					completeString = completeString.substring(0, completeString.length() - 2);
					bufferedWriter.write(completeString);
					bufferedWriter.write(COMMIT + NEWLINE);
					bufferedWriter.write(AUTOCOMMIT + NEWLINE);
					bufferedWriter.write(INSERT + QUOTE + generatedTableData.getSchemaName() + QUOTE + DOT + QUOTE
							+ generatedTableData.getTableName() + QUOTE + " values");
					bufferedWriter.flush();
				}
				rowCount++;
			}
			completeString = completeString.substring(0, completeString.length() - 2);
			bufferedWriter.write(completeString + NEWLINE);
			bufferedWriter.write(SEMICOLON + COMMIT + NEWLINE);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}