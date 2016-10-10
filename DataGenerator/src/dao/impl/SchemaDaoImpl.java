/**
 * 
 */
package dao.impl;

import java.util.List;

import dao.SchemaDao;
import entity.Schemadetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

/**
 * @author M1026335
 *
 */
public class SchemaDaoImpl extends GenericDAOImpl<Schemadetail, Integer> implements SchemaDao {

	@Override
	public void saveSchema(Schemadetail schemadetail) throws PersistException {

		try {
			save(schemadetail);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException("Could not persist Schema Detail Data - " + err.getMessage() + schemadetail);
		}

	}

	@Override
	public Schemadetail getSchemaByid(Integer id) throws ReadEntityException {
		return null;
	}

	@Override
	public List<Schemadetail> getAllSchemainDB() throws ReadEntityException {
		return null;
	}

	@Override
	public void update(Schemadetail schemadetail) throws EntityNotPresent {

	}

}
