package jobs.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import dao.TableDao;
import dao.impl.TableDaoImpl;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Tabledetail;
import exceptions.ReadEntityException;

public class CreateConstraintTask extends Task {
	Databasedetail databasedetail;
	Tabledetail tabledetail;
	List<Columnsdetail> columnsdetailList;
	List<Constraintsdetail> constraintsdetails;

	public CreateConstraintTask(Databasedetail databasedetail, List<Columnsdetail> columnsdetail) {
		super();
		this.databasedetail = databasedetail;
		this.columnsdetailList = columnsdetail;
	}

	@Override
	public void execute() throws BuildException {

		try {
			TableDao tableDao = new TableDaoImpl();
			constraintsdetails = new ArrayList<>();
			ConnectonCreateTask connectonCreateTask = new ConnectonCreateTask(databasedetail);
			connectonCreateTask.execute();
			Connection connection = connectonCreateTask.getConnection();
			QueryFetchTask fetchTask = new QueryFetchTask(databasedetail, "Constraint");
			fetchTask.execute();
			String queryExecute = fetchTask.getQuery();
			queryExecute = queryExecute.replace("schemaName_replace",
					columnsdetailList.get(0).getTabledetail().getSchemadetail().getName());
			queryExecute = queryExecute.replace("tableName_replace",
					columnsdetailList.get(0).getTabledetail().getTableName());
			QueryExecuteTask executeTask = new QueryExecuteTask(connection, queryExecute);
			executeTask.execute();
			ResultSet resultSet = executeTask.getResultSet();
			Constraintsdetail constraintsdetail;
			int schemaId = columnsdetailList.get(0).getTabledetail().getSchemadetail().getIdschema();
			while (resultSet.next()) {
				constraintsdetail = new Constraintsdetail();
				String columnName = resultSet.getString("COLUMN_NAME");
				constraintsdetail.setConstraintname(resultSet.getString("CONSTRAINT_NAME"));
				constraintsdetail.setReferenceColumnName(resultSet.getString("REFERENCED_COLUMN_NAME"));
				if (resultSet.getString("REFERENCED_TABLE_NAME") != null) {
					try {
						Tabledetail tabledetail = tableDao
								.getTableByNameAndSchema(resultSet.getString("REFERENCED_TABLE_NAME"), schemaId);
						constraintsdetail.setReferenceTable(tabledetail);
 					} catch (ReadEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (Columnsdetail columnsdetail : columnsdetailList) {
					if (columnsdetail.getName().equalsIgnoreCase(columnName)) {
						constraintsdetail.setColumnsdetail1(columnsdetail);
						break;
					}
				}
				constraintsdetails.add(constraintsdetail);
			}
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BuildException();
		} catch (NumberFormatException exception) {
			exception.printStackTrace();
			throw new BuildException("invalid number");
		}

	}

	public List<Constraintsdetail> getConstraintsdetails() {
		return constraintsdetails;
	}
}
