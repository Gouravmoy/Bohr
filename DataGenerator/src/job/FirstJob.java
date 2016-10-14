package job;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import dao.ColumnsDao;
import dao.ConstraintsDao;
import dao.SchemaDao;
import dao.TableDao;
import dao.impl.ColumnsDAOImpl;
import dao.impl.ConstraintsDAOImpl;
import dao.impl.SchemaDaoImpl;
import dao.impl.TableDaoImpl;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.PersistException;
import jobs.tasks.CreateColumnTask;
import jobs.tasks.CreateConstraintTask;
import jobs.tasks.CreateSchemaTask;
import jobs.tasks.CreateTableTask;

public class FirstJob extends Job {
	Databasedetail databasedetail;

	public FirstJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor arg0) {
		SchemaDao schemaDao = new SchemaDaoImpl();
		TableDao tableDao = new TableDaoImpl();
		ColumnsDao columnsDao = new ColumnsDAOImpl();
		ConstraintsDao constraintsDao = new ConstraintsDAOImpl();
		CreateSchemaTask createSchemaTask = new CreateSchemaTask(databasedetail);
		CreateTableTask createTableTask;
		CreateColumnTask createColumnTask;
		CreateConstraintTask constraintTask;
		createSchemaTask.execute();
		List<Schemadetail> schemadetailList = createSchemaTask.getSchemadetails();
		for (Schemadetail schemadetail : schemadetailList) {
			try {
				schemaDao.saveSchema(schemadetail);
				createTableTask = new CreateTableTask(databasedetail, schemadetail);
				createTableTask.execute();
				List<Tabledetail> tabledetailList = createTableTask.getTabledetails();
				for (Tabledetail tabledetail : tabledetailList) {
					tableDao.saveTabledetail(tabledetail);
					createColumnTask = new CreateColumnTask(databasedetail, tabledetail);
					createColumnTask.execute();
					List<Columnsdetail> columnsdetailList = createColumnTask.getColumnsdetails();
					columnsDao.saveListOfColumns(columnsdetailList);
					constraintTask = new CreateConstraintTask(databasedetail, columnsdetailList);
					constraintTask.execute();
					List<Constraintsdetail> constraintsdetailList = constraintTask.getConstraintsdetails();
					constraintsDao.saveListOfConstraint(constraintsdetailList);
				}
			} catch (PersistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return Status.OK_STATUS;
	}

	public void setDatabasedetail(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

}
