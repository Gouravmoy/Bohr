package jobs.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Columnsdetail;
import entity.Databasedetail;
import entity.Tabledetail;
import enums.ColumnType;
import jobs.tasks.create.QueryExecuteTask;

public class CreateColumnTask extends Task {
	Databasedetail databasedetail;
	Tabledetail tabledetail;
	List<Columnsdetail> columnsdetails;

	public CreateColumnTask(Databasedetail databasedetail, Tabledetail tabledetail) {
		super();
		this.databasedetail = databasedetail;
		this.tabledetail = tabledetail;
	}

	@Override
	public void execute() throws BuildException {

		try {
			columnsdetails = new ArrayList<>();
			ConnectonCreateTask connectonCreateTask = new ConnectonCreateTask(databasedetail);
			connectonCreateTask.execute();
			Connection connection = connectonCreateTask.getConnection();
			QueryFetchTask fetchTask = new QueryFetchTask(databasedetail, "ColumnNames");
			fetchTask.execute();
			String queryExecute = fetchTask.getQuery();
			queryExecute = queryExecute.replace("schemaName_replace", tabledetail.getSchemadetail().getName());
			queryExecute = queryExecute.replace("tableName_replace", tabledetail.getTableName());
			QueryExecuteTask executeTask = new QueryExecuteTask(connection, queryExecute);
			executeTask.execute();
			ResultSet resultSet = executeTask.getResultSet();
			Columnsdetail columnsdetail;
			System.out.println(tabledetail.getTableName());
			if (tabledetail.getTableName().equals("busrv_businfo")) {
				System.out.println("delete");
			}
			while (resultSet.next()) {
				try {
					columnsdetail = new Columnsdetail();
					columnsdetail.setTabledetail(tabledetail);
					columnsdetail.setName(resultSet.getString("COLUMN_NAME"));
					if (resultSet.getString("IS_NULLABLE").equals("NO")) {
						columnsdetail.setIsnullable((byte) 0);
					} else {
						columnsdetail.setIsnullable((byte) 1);
					}
					if (resultSet.getString("MAX_LENGTH").equals("0")) {
						String str = resultSet.getString("COLUMN_TYPE") + "";
						if (str.contains("(")) {
							String answer = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
							if (answer.contains(",")) {
								int length = Integer.valueOf(answer.split(",")[0]);
								int decimalLength = Integer.valueOf(answer.split(",")[1]);
								columnsdetail.setLength(length);
								columnsdetail.setDecimalLength(decimalLength);
							} else {
								int length = Integer.valueOf(answer);
								columnsdetail.setLength(length);
								columnsdetail.setDecimalLength(0);
							}
						} else {
							columnsdetail.setLength(0);
							columnsdetail.setDecimalLength(0);
						}
					} else {
						columnsdetail.setLength(Integer.valueOf(resultSet.getString("MAX_LENGTH")));
					}
					columnsdetail.setType(getColType(resultSet.getString("DATA_TYPE")));
					if(columnsdetail.getType()==ColumnType.ENUM){
						String[] colTypeSplit = resultSet.getString("COLUMN_TYPE").split("\\(");
						colTypeSplit[1] = colTypeSplit[1].replace(")", "");
						columnsdetail.setEnumvalues(colTypeSplit[1]);
					}
					columnsdetails.add(columnsdetail);
				} catch (NumberFormatException exception) {
					System.out.println("Leave this");
				} catch (Exception exception) {
					System.out.println("Exception");
				}
			}
			resultSet.close();
			connection.close();
		
		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new BuildException();
		} catch (NumberFormatException exception) {
			exception.printStackTrace();
			throw new BuildException("invalid number");
		} catch (StringIndexOutOfBoundsException boundsException) {
			boundsException.printStackTrace();
			throw new BuildException("invalid number");
		}

	}

	private static ColumnType getColType(String colTypeString) {
		switch (colTypeString) {
		case "char":
		case "CHAR":
			return ColumnType.CHAR;
		case "varchar":
		case "VARCHAR":
			return ColumnType.VARCHAR;
		case "int":
		case "INT":
			return ColumnType.INTEGER;
		case "float":
		case "FLOAT":
			return ColumnType.FLOAT;
		case "date":
		case "DATE":
		case "datetime":
		case "DATETIME":
		case "timestamp":
		case "TIMESTAMP":
			return ColumnType.DATE;
		case "year":
		case "YEAR":
			return ColumnType.YEAR;
		case "enum":
		case "ENUM":
		case "set":
		case "SET":
			return ColumnType.ENUM;
		case "decimal":
		case "DECIMAL":
		case "double":
		case "DOUBLE":
			return ColumnType.INTEGER;
		case "mediumint":
		case "MEDIUMINT":
		case "smallint":
		case "SMALLINT":
			return ColumnType.INTEGER;
		case "tinyint":
		case "TINYINT":
			return ColumnType.TINYINT;
		case "blob":
		case "BLOB":
			return ColumnType.VARCHAR;
		case "text":
		case "TEXT":
			return ColumnType.VARCHAR;
		default:
			return ColumnType.VARCHAR;
		}
	}

	public List<Columnsdetail> getColumnsdetails() {
		return columnsdetails;
	}

}
