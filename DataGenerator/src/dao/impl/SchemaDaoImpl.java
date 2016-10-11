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
	public Schemadetail saveSchema(Schemadetail schemadetail) throws PersistException {

		try {
			save(schemadetail);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException("Could not persist Schema Detail Data - " + err.getMessage() + schemadetail);
		}
		return schemadetail;
	}

	@Override
	public Schemadetail getSchemaByid(Integer id) throws ReadEntityException {
		try {
			return readById(Schemadetail.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get Schema Detail Data for ID - " + id);
		}
	}

	@Override
	public List<Schemadetail> getAllSchemainDB() throws ReadEntityException {
		List<Schemadetail> schemadetails;
		try {
			schemadetails = readAll("Schemadetail.findAll", Schemadetail.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All Schemadetail Information");
		}
		return schemadetails;
	}

	@Override
	public void update(Schemadetail schemadetail) throws EntityNotPresent {
		try {
			update(Schemadetail.class, schemadetail.getIdschema(), schemadetail);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update Schemadetail Information");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
