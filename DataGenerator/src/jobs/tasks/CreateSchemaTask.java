package jobs.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Databasedetail;
import entity.Schemadetail;

public class CreateSchemaTask extends Task {
	Databasedetail databasedetail;
	List<Schemadetail> schemadetails;

	public CreateSchemaTask(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	@Override
	public void execute() throws BuildException {
		try {
			schemadetails = new ArrayList<>();
			ConnectonCreateTask connectonCreateTask = new ConnectonCreateTask(databasedetail);
			connectonCreateTask.execute();
			Connection connection = connectonCreateTask.getConnection();
			QueryFetchTask fetchTask = new QueryFetchTask(databasedetail, "SchemaNames");
			fetchTask.execute();
			QueryExecuteTask executeTask = new QueryExecuteTask(connection, fetchTask.getQuery());
			executeTask.execute();
			ResultSet resultSet = executeTask.getResultSet();
			while (resultSet.next()) {
				Schemadetail schemadetail = new Schemadetail();
				schemadetail.setDatabasedetail(databasedetail);
				schemadetail.setName(resultSet.getString("SCHEMA_NAME"));
				schemadetails.add(schemadetail);
			}
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new BuildException();
		}
	}

	public List<Schemadetail> getSchemadetails() {
		return schemadetails;
	}

}
