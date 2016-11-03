package jobs.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Databasedetail;

public class QueryFetchTask extends Task {
	Databasedetail databasedetail;
	String propertyName;
	String query;

	public QueryFetchTask(Databasedetail databasedetail, String propertyName) {
		super();
		this.databasedetail = databasedetail;
		this.propertyName = propertyName;
	}

	@Override
	public void execute() throws BuildException {
		InputStream inputStream;
		Properties properties = new Properties();
		String path = "D:\\Bohr\\DataGenerator\\resources\\files\\";
		File queryFile = new File(path + databasedetail.getType() + ".properties");
		// File queryFile = new File
		// ("D:\\Workspaces\\RDGGitStaging\\DataGenerator\\resources\\files\\MYSQL.properties");
		try {
			// String path = "platform:/plugin/DataGenerator/resources/files/"+
			// databasedetail.getType() + ".properties";
			// resultTemplateURL = new URL(path);
			// FileUtils.copyURLToFile(resultTemplateURL, queryFile);
			inputStream = new FileInputStream(queryFile);
			properties.load(inputStream);
			query = properties.getProperty(propertyName);
			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getQuery() {
		return query;
	}
}
