package jobs.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class QueryExecuteTask extends Task {
	Connection connection;
	ResultSet resultSet;
	String query;

	public QueryExecuteTask(Connection connection, String quString) {
		this.connection = connection;
		this.query = quString;
	}

	@Override
	public void execute() throws BuildException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BuildException();
		}
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

}
