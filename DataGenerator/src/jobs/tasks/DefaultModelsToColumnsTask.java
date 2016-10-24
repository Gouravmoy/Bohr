package jobs.tasks;

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
import exceptions.EntityNotPresent;
import exceptions.ReadEntityException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultModelsToColumnsTask extends Task {

	Projectdetails project;

	@Override
	public void execute() throws BuildException {
		PreDefinedModelDao preDefinedModelDao = new PreDefinedModelsDaoImpl();
		List<Tabledetail> tabledetails = null;
		List<Columnsdetail> columnsdetails = null;
		List<PreDefinedModels> preDefinedModels = null;
		Set<PreDefinedModels> colPredefinedData = null;
		ColumnsDao columnsDao = new ColumnsDAOImpl();
		try {
			preDefinedModels = preDefinedModelDao.getAllPreDefinedModelsinDB();
			tabledetails = new ArrayList<>();
			tabledetails.addAll(project.getSchemadetail().getTabledetails());
			for (Tabledetail tabledetail : tabledetails) {
				columnsdetails = new ArrayList<>();
				columnsdetails = tabledetail.getColumnsdetails();
				for (Columnsdetail columnsdetail : columnsdetails) {
					colPredefinedData = new HashSet<>();
					for (PreDefinedModels preDefinedModel : preDefinedModels) {
						String colName = columnsdetail.getName();
						for (String expectedColName : preDefinedModel.getExpectedColumnName().split(",")) {
							if (colName.equals(expectedColName) || colName.endsWith(expectedColName)) {
								colPredefinedData.add(preDefinedModel);
								columnsdetail.setPredefinedModels(colPredefinedData);
								columnsDao.update(columnsdetail);
								break;
							}
						}
					}
				}
			}
			preDefinedModelDao.getAllPreDefinedModelsinDB();
		} catch (ReadEntityException | EntityNotPresent e) {
			e.printStackTrace();
		}

	}

	public void setProject(Projectdetails project) {
		this.project = project;
	}

}
