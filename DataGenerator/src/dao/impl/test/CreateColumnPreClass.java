package dao.impl.test;

import java.util.List;

import dao.ColumnsDao;
import dao.DatabaseDao;
import dao.SchemaDao;
import dao.TableDao;
import dao.impl.ColumnsDAOImpl;
import dao.impl.DatabaseDAOImpl;
import dao.impl.SchemaDaoImpl;
import dao.impl.TableDaoImpl;
import entity.Columnsdetail;
import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.DAOException;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class CreateColumnPreClass {

	public static Tabledetail addTableToDataBase() {
		DatabaseDao daoDataBase = new DatabaseDAOImpl();
		SchemaDao daoSchema = new SchemaDaoImpl();
		TableDao daoTable = new TableDaoImpl();
		Databasedetail databasedetail = new Databasedetail();
		Schemadetail schemadetail = new Schemadetail();
		Tabledetail tabledetail = new Tabledetail();
		databasedetail.setName("DataBase_1");
		schemadetail.setName("Schema_1");
		tabledetail.setTableName("Table_1");
		try {
			daoDataBase.saveDatabse(databasedetail);
			schemadetail.setDatabasedetail(databasedetail);
			daoSchema.saveSchema(schemadetail);
			tabledetail.setSchemadetail(schemadetail);
			daoTable.saveTabledetail(tabledetail);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tabledetail;
	}

	public static Columnsdetail addColumnToDatabase() {
		ColumnsDao columnsDao = new ColumnsDAOImpl();
		Tabledetail tabledetail = addTableToDataBase();
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setTabledetail(tabledetail);
		try {
			List<Columnsdetail> columnsdetails = columnsDao.getAllColumnsinDB();
			System.out.println(columnsdetails.size());
			columnsDao.saveColumn(columnsdetail);
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnsdetail;

	}
}
