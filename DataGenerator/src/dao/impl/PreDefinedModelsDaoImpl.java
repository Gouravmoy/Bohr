package dao.impl;

import java.util.List;

import dao.PreDefinedModelDao;
import entity.Patterndetail;
import entity.PreDefinedModels;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class PreDefinedModelsDaoImpl extends GenericDAOImpl<PreDefinedModels, Integer> implements PreDefinedModelDao {

	@Override
	public PreDefinedModels savePreDefinedModels(PreDefinedModels preDefinedModels) throws PersistException {
		try {
			save(preDefinedModels);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException(
					"Could not persist preDefinedModels Data - " + err.getMessage() + preDefinedModels);
		}
		return preDefinedModels;
	}

	@Override
	public PreDefinedModels getPreDefinedModelsByid(Integer id) throws ReadEntityException {
		try {
			return readById(PreDefinedModels.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get PreDefinedModels Data for ID - " + id);
		}
	}

	@Override
	public List<PreDefinedModels> getAllPreDefinedModelsinDB() throws ReadEntityException {
		List<PreDefinedModels> preDefinedModels;
		try {
			preDefinedModels = readAll("PreDefinedModels.findAll", PreDefinedModels.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All PreDefinedModels Information");
		}
		return preDefinedModels;
	}

	@Override
	public void update(PreDefinedModels preDefinedModels) throws EntityNotPresent {
		try {
			update(PreDefinedModels.class, preDefinedModels.getIdpredefinedDS(), preDefinedModels);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update PreDefinedModels Information");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PreDefinedModels> getPreDefinedModelsByQyery(String query, Object[] pars) {
		try {
			return getByQuery(query, pars, PreDefinedModels.class);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
