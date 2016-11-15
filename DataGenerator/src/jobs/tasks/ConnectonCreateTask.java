package jobs.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Databasedetail;

public class ConnectonCreateTask extends Task {
	static Logger log = Logger.getLogger(ConnectonCreateTask.class.getName());
	Databasedetail databasedetail;
	Connection connection;

	public ConnectonCreateTask(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	@Override
	public void execute() throws BuildException {
		switch (databasedetail.getType()) {
		case DB2:
			break;
		case MYSQL:
			try {
				System.out.println(databasedetail);
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(databasedetail.getDescription(), databasedetail.getUsername(),
						databasedetail.getPassword());
			} catch (ClassNotFoundException | SQLException | NullPointerException classNotFoundException) {
				classNotFoundException.printStackTrace();
				log.error(databasedetail, classNotFoundException);
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
