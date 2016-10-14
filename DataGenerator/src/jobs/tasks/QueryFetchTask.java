package jobs.tasks;

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
		try {
			String path = "D:\\Bohr\\DataGenerator\\";
			inputStream = new FileInputStream(path + databasedetail.getType() + ".properties");
			properties.load(inputStream);
			query = properties.getProperty(propertyName);
			inputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getQuery() {
		return query;
	}
}
