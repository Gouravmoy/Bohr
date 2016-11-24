package dao.impl;

import java.util.List;

import dao.ConditionsDao;
import entity.Conditions;
import entity.Databasedetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class ConditionsDaoImpl extends GenericDAOImpl<Conditions, Integer> implements ConditionsDao {

	public ConditionsDaoImpl() {
		super();
	}

	@Override
	public Conditions saveConditions(Conditions conditions) throws PersistException {
		try {
			save(conditions);
		} catch (Exception err) {
			err.printStackTrace();
			throw new PersistException("Could not persist Conditions Data - " + err.getMessage() + conditions);
		}
		return conditions;
	}

	@Override
	public Conditions getConditionsByid(Integer id) throws ReadEntityException {
		try {
			return readById(Conditions.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get Conditions Data for ID - " + id);
		}
	}

	@Override
	public List<Conditions> getAllConditionsinDB() throws ReadEntityException {
		List<Conditions> conditions;
		try {
			conditions = readAll("Conditions.findAll", Databasedetail.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All Conditions Information");
		}
		return conditions;
	}

	@Override
	public void update(Conditions conditions) throws EntityNotPresent {
		try {
			update(Conditions.class, 1, conditions);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new EntityNotPresent();

		}
	}

}
