package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ColumnsDao;
import entity.Columnsdetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class ColumnsDAOImpl extends GenericDAOImpl<Columnsdetail, Integer> implements ColumnsDao {

	@Override
	public Columnsdetail saveColumn(Columnsdetail columnsdetail) throws PersistException {
		try {
			return save(columnsdetail);
		} catch (DAOException e) {
			throw new PersistException();
		}
	}

	@Override
	public Columnsdetail getColumnsdetailByid(Integer id) throws ReadEntityException {
		Columnsdetail columnsdetail = null;
		try {
			columnsdetail = readById(Columnsdetail.class, id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return columnsdetail;
	}

	@Override
	public List<Columnsdetail> getAllColumnsinDB() throws ReadEntityException {
		List<Columnsdetail> columnsdetails = null;
		try {
			columnsdetails = readAll("Columnsdetail.findAll", Columnsdetail.class);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return columnsdetails;
	}

	@Override
	public List<String> getAllColumnsNames() throws ReadEntityException {
		List<String> columnNameList = new ArrayList<String>();
		for (Columnsdetail columnsdetail : getAllColumnsinDB()) {
			columnNameList.add(columnsdetail.getName());
		}
		return columnNameList;
	}

	@Override
	public Columnsdetail update(Columnsdetail columnsdetail) throws EntityNotPresent {
		try {
			
			return update(Columnsdetail.class, columnsdetail.getIdcolumnsdetails(), columnsdetail);
		} catch (Exception err) {
			throw new EntityNotPresent("Could not get Update Columnsdetail Information");
		}

	}

	@Override
	public void saveListOfColumns(List<Columnsdetail> columnsdetails) throws PersistException {
		try {
			batchSaveDAO(columnsdetails);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistException("Unable to save All Columns");
		}

	}

	@Override
	public void updateBatch(List<Columnsdetail> columnsdetails) {
		try {
			saveOrUpdateBatch(columnsdetails);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
