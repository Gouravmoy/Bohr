/**
 * 
 */
package dao.impl;

import java.util.List;

import dao.TableDao;
import entity.Tabledetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class TableDaoImpl extends GenericDAOImpl<Tabledetail, Integer> implements TableDao {

	@Override
	public Tabledetail saveTabledetail(Tabledetail tabledetail) throws PersistException {
		try {
			save(tabledetail);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException("Could not persist Tabledetail Data - " + err.getMessage() + tabledetail);
		}
		return tabledetail;
	}

	@Override
	public Tabledetail getTabledetailByid(Integer id) throws ReadEntityException {
		try {
			return readById(Tabledetail.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get Tabledetail Data for ID - " + id);
		}
	}

	@Override
	public List<Tabledetail> getAllTabledetailinDB() throws ReadEntityException {
		List<Tabledetail> tabledetails;
		try {
			tabledetails = readAll("Schemadetail.findAll", Tabledetail.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All Tabledetail Information");
		}
		return tabledetails;
	}

	@Override
	public void update(Tabledetail tabledetail) throws EntityNotPresent {
		try {
			update(Tabledetail.class, tabledetail.getIdtabledetails(), tabledetail);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update Tabledetail Information");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveListOfTables(List<Tabledetail> tabledetails) throws PersistException {
		try {
			batchSaveDAO(tabledetails);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new PersistException("Unable to save batch");
		}

	}

	@Override
	public Tabledetail getTableByNameAndSchema(String tableName, int schemaId) throws ReadEntityException {
		String query = "FROM Tabledetail T where T.tableName=:arg0 and  T.schemadetail.idschema=:arg1";
		Object[] pars = { tableName, schemaId };
		List<Tabledetail> qList;
		try {
			qList = getByQuery(query, pars, Tabledetail.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ReadEntityException("Unable to read Table with name" + tableName);
		}
		return qList.get(0);

	}

}
