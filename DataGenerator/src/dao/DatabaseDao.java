package dao;

import java.util.List;

import entity.Databasedetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface DatabaseDao {
	public Databasedetail saveDatabse(Databasedetail databse) throws PersistException;

	public Databasedetail getDatabaseByid(Integer id) throws ReadEntityException;

	public List<Databasedetail> getAllDatabaseinDB() throws ReadEntityException;

	public List<String> getAllConnectionNames() throws ReadEntityException;

	public void update(Databasedetail database) throws EntityNotPresent;
}
