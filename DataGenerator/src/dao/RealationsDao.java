package dao;

import java.util.List;

import entity.Relationsdetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface RealationsDao {

	public void saveRelation(Relationsdetail relationsdetail) throws PersistException;

	public Relationsdetail getRelationByid(Integer id) throws ReadEntityException;

	public List<Relationsdetail> getAllRelationsinDB() throws ReadEntityException;

	public List<String> getAllRelationNames() throws ReadEntityException;

	public Relationsdetail update(Relationsdetail relationsdetail) throws EntityNotPresent;

	public List<Relationsdetail> getSchemaByQyery(String query, Object[] pars);

}
