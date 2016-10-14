package jobs.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Databasedetail;

public class ConnectonCreateTask extends Task {
	Databasedetail databasedetail;
	Connection connection;

	public ConnectonCreateTask(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	@Override
	public void execute() throws BuildException {
		switch (databasedetail.getType()) {
		case IBM_DB2:
			break;
		case MYSQL:
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(databasedetail.getDescription(), databasedetail.getUsername(),
						databasedetail.getPassword());
			} catch (ClassNotFoundException | SQLException classNotFoundException) {
				classNotFoundException.printStackTrace();
				throw new BuildException("Invalid Connection Parameters");
			}
			break;
		case ORACLE:
			break;
		default:
			break;

		}

	}

	public Connection getConnection() {
		return connection;
	}

}
