package dao;

import java.util.List;

import entity.Schemadetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface SchemaDao {
	public void saveSchema(Schemadetail schemadetail) throws PersistException;

	public Schemadetail getSchemaByid(Integer id) throws ReadEntityException;

	public List<Schemadetail> getAllSchemainDB() throws ReadEntityException;

	public void update(Schemadetail schemadetail) throws EntityNotPresent;
}
