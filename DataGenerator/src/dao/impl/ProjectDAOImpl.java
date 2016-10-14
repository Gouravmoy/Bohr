package dao.impl;

import java.util.List;

import dao.ProjectDao;
import entity.Projectdetails;
import entity.Tabledetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class ProjectDAOImpl extends GenericDAOImpl<Projectdetails, Integer> implements ProjectDao {

	@Override
	public Projectdetails saveProjectdetails(Projectdetails projectdetails) throws PersistException {
		try {
			save(projectdetails);
		} catch (DAOException err) {
			err.printStackTrace();
			throw new PersistException("Could not persist projectdetails Data - " + err.getMessage() + projectdetails);
		}
		return projectdetails;
	}

	@Override
	public Projectdetails getProjectdetailsByid(Integer id) throws ReadEntityException {
		try {
			return readById(Projectdetails.class, id);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get Projectdetails Data for ID - " + id);
		}
	}

	@Override
	public List<Projectdetails> getAllProjectdetailsinDB() throws ReadEntityException {
		List<Projectdetails> projectdetails;
		try {
			projectdetails = readAll("Projectdetails.findAll", Projectdetails.class);
		} catch (Exception err) {
			throw new ReadEntityException("Could not get All projectdetails Information");
		}
		return projectdetails;
	}

	@Override
	public void update(Projectdetails projectdetails) throws EntityNotPresent {
		try {
			update(Projectdetails.class, projectdetails.getIdproject(), projectdetails);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update Projectdetails Information");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
