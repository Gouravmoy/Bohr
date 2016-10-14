package dao;

import java.util.List;

import entity.Tabledetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface TableDao {
	public Tabledetail saveTabledetail(Tabledetail tabledetail) throws PersistException;

	public Tabledetail getTabledetailByid(Integer id) throws ReadEntityException;

	public List<Tabledetail> getAllTabledetailinDB() throws ReadEntityException;

	public void update(Tabledetail tabledetail) throws EntityNotPresent;

	public void saveListOfTables(List<Tabledetail> tabledetails) throws PersistException;

	public Tabledetail getTableByNameAndSchema(String tableName, int schemaId) throws ReadEntityException;
}
