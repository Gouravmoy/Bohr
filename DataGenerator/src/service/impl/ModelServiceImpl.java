package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.DataSampleDao;
import dao.PreDefinedModelDao;
import dao.impl.DataSampleDaoImpl;
import dao.impl.PreDefinedModelsDaoImpl;
import entity.Datasamplemodel;
import entity.PreDefinedModels;
import service.ModelService;

public class ModelServiceImpl implements ModelService {

	DataSampleDao dataSampleDao = new DataSampleDaoImpl();
	PreDefinedModelDao predefinedDao = new PreDefinedModelsDaoImpl();

	@Override
	public Datasamplemodel getDataSampleModelByColumnId(Integer id,Integer projectId) {
		List<Datasamplemodel> datasamplemodels = new ArrayList<>();
		String query = "from Datasamplemodel p where p.columnsdetail.idcolumnsdetails=:arg0 and p.projectdetail.idproject=:arg1";
		Object[] pars = { id,projectId };
		datasamplemodels = dataSampleDao.getDatasamplemodelByQyery(query, pars);
		if (!datasamplemodels.isEmpty())
			return datasamplemodels.get(0);
		return null;
	}

	@Override
	public PreDefinedModels getPreDefinedmodelsByColumnId(Integer id) {
		List<PreDefinedModels> preDefinedModels = new ArrayList<>();
		String query = "select distinct p from PreDefinedModels p join p.columnsdetails c where c.idcolumnsdetails=:arg0";
		Object[] pars = { id };
		preDefinedModels = predefinedDao.getPreDefinedModelsByQyery(query, pars);
		if (!preDefinedModels.isEmpty())
			return preDefinedModels.get(0);
		/*
		 * try { preDefinedModels = predefinedDao.getAllPreDefinedModelsinDB();
		 * for (PreDefinedModels definedModels : preDefinedModels) { for
		 * (Columnsdetail columnsdetail : definedModels.getColumnsdetails()) {
		 * if (columnsdetail.getIdcolumnsdetails() == id) return definedModels;
		 * } } } catch (ReadEntityException e) { e.printStackTrace(); }
		 */
		return null;
	}

}
