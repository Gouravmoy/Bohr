package jobs.tasks.create;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import jobs.tasks.ConnectonCreateTask;

public class CreateTableTask extends Task {
	Databasedetail databasedetail;
	Schemadetail schemadetail;
	List<Tabledetail> tabledetails;

	public CreateTableTask(Databasedetail databasedetail, Schemadetail schemadetail) {
		this.databasedetail = databasedetail;
		this.schemadetail = schemadetail;
	}

	@Override
	public void execute() throws BuildException {
		try {
			tabledetails = new ArrayList<>();
			ConnectonCreateTask connectonCreateTask = new ConnectonCreateTask(databasedetail);
			connectonCreateTask.execute();
			Connection connection = connectonCreateTask.getConnection();
			QueryFetchTask fetchTask = new QueryFetchTask(databasedetail, "TableNames");
			fetchTask.execute();
			String queryExecute = fetchTask.getQuery();
			queryExecute = queryExecute.replace("schemaName_replace", schemadetail.getName());
			QueryExecuteTask executeTask = new QueryExecuteTask(connection, queryExecute);
			executeTask.execute();
			ResultSet resultSet = executeTask.getResultSet();
			Tabledetail tabledetail;
			while (resultSet.next()) {
				tabledetail = new Tabledetail();
				tabledetail.setTableName(resultSet.getString("TABLE_NAME"));
				tabledetail.setSchemadetail(schemadetail);
				tabledetails.add(tabledetail);
			}
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new BuildException();
		}

	}

	public List<Tabledetail> getTabledetails() {
		return tabledetails;
	}

}
