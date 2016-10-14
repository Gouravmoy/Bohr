package dao;

import java.util.List;

import entity.Projectdetails;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface ProjectDao {
	public Projectdetails saveProjectdetails(Projectdetails projectdetails) throws PersistException;

	public Projectdetails getProjectdetailsByid(Integer id) throws ReadEntityException;

	public List<Projectdetails> getAllProjectdetailsinDB() throws ReadEntityException;

	public void update(Projectdetails projectdetails) throws EntityNotPresent;

}
