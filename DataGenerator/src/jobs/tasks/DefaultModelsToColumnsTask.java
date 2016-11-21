package jobs.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import dao.ColumnsDao;
import dao.PreDefinedModelDao;
import dao.impl.ColumnsDAOImpl;
import dao.impl.PreDefinedModelsDaoImpl;
import entity.Columnsdetail;
import entity.PreDefinedModels;
import entity.Projectdetails;
import entity.Tabledetail;
import enums.ColumnType;
import exceptions.ReadEntityException;

public class DefaultModelsToColumnsTask extends Task {

	Projectdetails project;

	@Override
	public void execute() throws BuildException {
		PreDefinedModelDao preDefinedModelDao = new PreDefinedModelsDaoImpl();
		List<Tabledetail> tabledetails = null;
		List<Columnsdetail> columnsdetails = null;
		List<PreDefinedModels> preDefinedModels = null;
		ColumnsDao columnsDao = new ColumnsDAOImpl();
		try {
			preDefinedModels = preDefinedModelDao.getAllPreDefinedModelsinDB();
			tabledetails = new ArrayList<>();
			tabledetails.addAll(project.getSchemadetail().getTabledetails());
			for (Tabledetail tabledetail : tabledetails) {
				columnsdetails = new ArrayList<>();
				columnsdetails = tabledetail.getColumnsdetails();
				for (Columnsdetail columnsdetail : columnsdetails) {
					if (columnsdetail.getType() != ColumnType.VARCHAR)
						continue;
					for (PreDefinedModels preDefinedModel : preDefinedModels) {
						String colName = columnsdetail.getName();
						colName = colName.replace("_", "");
						if(columnsdetail.getConstraintsdetails1().size()>0)
							break;
						for (String expectedColName : preDefinedModel.getExpectedColumnName().split(",")) {
							String tablePlusCol = tabledetail.getTableName() + "" + colName;
							if (colName.equalsIgnoreCase(expectedColName) || colName.endsWith(expectedColName)
									|| tablePlusCol.contains(expectedColName)) {
								columnsdetail.setPredefinedModel(preDefinedModel);
								try {
									columnsDao.update(columnsdetail);
								} catch (Exception err) {
									err.printStackTrace();
								}
								break;
							}
						}
					}
				}
			}
			preDefinedModelDao.getAllPreDefinedModelsinDB();
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

	}

	public void setProject(Projectdetails project) {
		this.project = project;
	}

}
