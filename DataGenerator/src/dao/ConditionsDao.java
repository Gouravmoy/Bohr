package dao;

import java.util.List;

import entity.Conditions;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface ConditionsDao {

	public Conditions saveConditions(Conditions conditions) throws PersistException;

	public Conditions getConditionsByid(Integer id) throws ReadEntityException;

	public List<Conditions> getAllConditionsinDB() throws ReadEntityException;

	public void update(Conditions conditions) throws EntityNotPresent;

}
