package dao.impl;

import java.util.List;

import dao.DataSampleDao;
import entity.Datasamplemodel;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class DataSampleDaoImpl extends GenericDAOImpl<Datasamplemodel, Integer> implements DataSampleDao {

	@Override
	public Datasamplemodel saveDatasamplemodel(Datasamplemodel datasamplemodel) throws PersistException {
		try {
			save(datasamplemodel);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException(
					"Could not persist datasamplemodel Detail Data - " + err.getMessage() + datasamplemodel);
		}
		return datasamplemodel;
	}

	@Override
	public Datasamplemodel getDatasamplemodelByid(Integer id) throws ReadEntityException {
		try {
			return readById(Datasamplemodel.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get Datasamplemodel Detail Data for ID - " + id);
		}
	}

	@Override
	public List<Datasamplemodel> getAllDatasamplemodelinDB() throws ReadEntityException {
		List<Datasamplemodel> datasamplemodels;
		try {
			datasamplemodels = readAll("Datasamplemodel.findAll", Datasamplemodel.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All Datasamplemodel Information");
		}
		return datasamplemodels;
	}

	@Override
	public void update(Datasamplemodel datasamplemodel) throws EntityNotPresent {
		try {
			update(Datasamplemodel.class, 1, datasamplemodel);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update Datasamplemodel Information");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Datasamplemodel> getDatasamplemodelByQyery(String query, Object[] pars) {
		try {
			return getByQuery(query, pars, Datasamplemodel.class);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
