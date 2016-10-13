package dao;

import java.util.List;

import entity.Constraintsdetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface ConstraintsDao {

	public Constraintsdetail saveConstraint(Constraintsdetail constraintsdetail) throws PersistException;

	public Constraintsdetail getConstraintsByid(Integer id) throws ReadEntityException;

	public List<Constraintsdetail> getAllConstraintsinDB() throws ReadEntityException;

	public List<String> getAllConstraintNames() throws ReadEntityException;

	public Constraintsdetail update(Constraintsdetail constraintsdetail) throws EntityNotPresent;

	public void saveListOfConstraint(List<Constraintsdetail> constraintsdetails) throws PersistException;

}
