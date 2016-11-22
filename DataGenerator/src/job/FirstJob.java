package job;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

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
import enums.ImportType;
import exceptions.PersistException;
import jobs.tasks.ImportRepoExcelTask;
import jobs.tasks.RefrehTreeTask;
import jobs.tasks.create.CreateColumnTask;
import jobs.tasks.create.CreateConstraintTask;
import jobs.tasks.create.CreateSchemaTask;
import jobs.tasks.create.CreateTableTask;
import views.dialog.ImportDialog;

public class FirstJob extends Job {
	Databasedetail databasedetail;
	ImportType importType;
	String importFileLocation;
	ImportDialog importDialog;

	public FirstJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		RefrehTreeTask refrehTreeTask;
		if (importType == ImportType.EXCEL) {
			ImportRepoExcelTask importRepoExcelTask = new ImportRepoExcelTask();
			importRepoExcelTask.setDatabasedetail(databasedetail);
			importRepoExcelTask.setImportFilePath(importFileLocation);
			importRepoExcelTask.execute();
		} else {
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
					tableDao.saveListOfTables(tabledetailList);
					for (Tabledetail tabledetail : tabledetailList) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								importDialog.getShell().setText("Importing Table " + tabledetail.getTableName());
							}
						});
						createColumnTask = new CreateColumnTask(databasedetail, tabledetail);
						createColumnTask.execute();
						List<Columnsdetail> columnsdetailList = createColumnTask.getColumnsdetails();
						columnsDao.saveListOfColumns(columnsdetailList);
						constraintTask = new CreateConstraintTask(databasedetail, columnsdetailList);
						constraintTask.execute();
						columnsDao.updateBatch(columnsdetailList);
						List<Constraintsdetail> constraintsdetailList = constraintTask.getConstraintsdetails();
						constraintsDao.saveListOfConstraint(constraintsdetailList);
					}
					refrehTreeTask = new RefrehTreeTask();
					refrehTreeTask.execute();
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							importDialog.close();
						}
					});
				} catch (PersistException e) {
					e.printStackTrace();
				}

			}
		}
		refrehTreeTask = new RefrehTreeTask();
		refrehTreeTask.execute();
		return Status.OK_STATUS;
	}

	public void setDatabasedetail(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	public void setImportType(ImportType importType) {
		this.importType = importType;
	}

	public String getImportFileLocation() {
		return importFileLocation;
	}

	public void setImportFileLocation(String importFileLocation) {
		this.importFileLocation = importFileLocation;
	}

	public void setImportDialog(ImportDialog importDialog) {
		this.importDialog = importDialog;
	}

}
