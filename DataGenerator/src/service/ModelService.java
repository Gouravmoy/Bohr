package service;

import entity.Datasamplemodel;
import entity.PreDefinedModels;

public interface ModelService {
	
	public Datasamplemodel getDataSampleModelByColumnId(Integer id,Integer projectId);
	
	public PreDefinedModels getPreDefinedmodelsByColumnId(Integer id);
	

}
