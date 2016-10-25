package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.DataSampleDao;
import dao.PreDefinedModelDao;
import dao.impl.DataSampleDaoImpl;
import dao.impl.PreDefinedModelsDaoImpl;
import entity.Columnsdetail;
import entity.Datasamplemodel;
import entity.PreDefinedModels;
import exceptions.ReadEntityException;
import service.ModelService;

public class ModelServiceImpl implements ModelService {

	DataSampleDao dataSampleDao = new DataSampleDaoImpl();
	PreDefinedModelDao predefinedDao = new PreDefinedModelsDaoImpl();

	@Override
	public Datasamplemodel getDataSampleModelByColumnId(Integer id) {
		List<Datasamplemodel> datasamplemodels = new ArrayList<>();
		String query = "from Datasamplemodel p where p.columnsdetail.idcolumnsdetails=:arg0";
		Object[] pars = { id };
		datasamplemodels = dataSampleDao.getDatasamplemodelByQyery(query, pars);
		if (!datasamplemodels.isEmpty())
			return datasamplemodels.get(0);
		return null;
	}

	@Override
	public PreDefinedModels getPreDefinedmodelsByColumnId(Integer id) {
		List<PreDefinedModels> preDefinedModels = new ArrayList<>();
		try {
			preDefinedModels = predefinedDao.getAllPreDefinedModelsinDB();
			for (PreDefinedModels definedModels : preDefinedModels) {
				for (Columnsdetail columnsdetail : definedModels.getColumnsdetails()) {
					if (columnsdetail.getIdcolumnsdetails() == id)
						return definedModels;
				}
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
