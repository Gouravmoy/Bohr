package dao;

import java.util.List;

import entity.PreDefinedModels;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface PreDefinedModelDao {
	public PreDefinedModels savePreDefinedModels(PreDefinedModels preDefinedModels) throws PersistException;

	public PreDefinedModels getPreDefinedModelsByid(Integer id) throws ReadEntityException;

	public List<PreDefinedModels> getAllPreDefinedModelsinDB() throws ReadEntityException;

	public void update(PreDefinedModels preDefinedModels) throws EntityNotPresent;
	
	public List<PreDefinedModels> getPreDefinedModelsByQyery(String query, Object[] pars);
}
