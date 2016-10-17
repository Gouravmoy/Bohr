package jobs.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import common.Master;
import entity.GeneratedTableData;

public class GenerateScriptsTask extends Task {

	String exportPath = "";
	List<GeneratedTableData> tableDatas;

	public GenerateScriptsTask(String exportPath, List<GeneratedTableData> tableDatas) {
		this.exportPath = exportPath;
		this.tableDatas = tableDatas;
		Master.INSTANCE.setGeneratedTableData(tableDatas);
	}

	@Override
	public void execute() throws BuildException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String exportFolder = exportPath + "\\Export" + timeStamp + "\\";
		String fileName = "";
		String mainFileName = "";
		String newLine = "\n";
		BufferedWriter writer = null;
		BufferedWriter mainWriter = null;
		StringBuilder builder = new StringBuilder();
		File exportFolderLoc = new File(exportFolder);
		if (!exportFolderLoc.exists()) {
			exportFolderLoc.mkdirs();
		}
		mainFileName = exportFolder + "master.sql";
		try {
			mainWriter = new BufferedWriter(new FileWriter(new File(mainFileName)));

			for (GeneratedTableData data : tableDatas) {
				fileName = exportFolder + data.getTableOrder() + "_" + data.getTable().getTableName() + ".sql";
				builder = new StringBuilder();
				writer = new BufferedWriter(new FileWriter(new File(fileName)));
				builder.append("SET AUTOCOMMIT=0;" + newLine);
				builder.append("INSERT INTO `nagios`.`" + data.getTable().getTableName() + "`" + newLine);
				builder.append("VALUES" + newLine);
				for (String row : data.getRows()) {
					builder.append("(" + row + ")" + "," + newLine);
				}
				builder.deleteCharAt(builder.length() - 2);
				builder.append(";");
				builder.append("COMMIT;" + newLine);
				mainWriter.write(builder.toString() + newLine);
				writer.write(builder.toString());
				writer.flush();
				writer.close();
			}
			mainWriter.flush();
			mainWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
