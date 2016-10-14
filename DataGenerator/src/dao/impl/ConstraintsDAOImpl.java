package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ConstraintsDao;
import entity.Constraintsdetail;
import entity.Databasedetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class ConstraintsDAOImpl extends GenericDAOImpl<Constraintsdetail, Integer> implements ConstraintsDao {

	@Override
	public Constraintsdetail saveConstraint(Constraintsdetail constraintsdetail) throws PersistException {
		try {
			return save(constraintsdetail);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new PersistException();
		}

	}

	@Override
	public Constraintsdetail getConstraintsByid(Integer id) throws ReadEntityException {
		Constraintsdetail constraintsdetail = null;
		try {
			constraintsdetail = readById(Constraintsdetail.class, id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return constraintsdetail;
	}

	@Override
	public List<Constraintsdetail> getAllConstraintsinDB() throws ReadEntityException {
		List<Constraintsdetail> constraintsdetails = null;
		try {
			constraintsdetails = readAll("Constraintsdetail.findAll", Constraintsdetail.class);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return constraintsdetails;
	}

	@Override
	public List<String> getAllConstraintNames() throws ReadEntityException {
		List<String> listConstraintNames = new ArrayList<>();
		for (Constraintsdetail constraintsdetail : getAllConstraintsinDB()) {
			listConstraintNames.add(constraintsdetail.getConstraintname());
		}
		return listConstraintNames;
	}

	@Override
	public Constraintsdetail update(Constraintsdetail constraintsdetail) throws EntityNotPresent {

		try {
			return update(Databasedetail.class, constraintsdetail.getIdconstraintsdetails(), constraintsdetail);
		} catch (Exception err) {
			throw new EntityNotPresent("Could not get Update Database Information");
		}

	}

	@Override
	public void saveListOfConstraint(List<Constraintsdetail> constraintsdetails) throws PersistException {

		try {
			batchSaveDAO(constraintsdetails);
		} catch (DAOException e) {

			e.printStackTrace();
			throw new PersistException("Unable to save List");
		}

	}

}
