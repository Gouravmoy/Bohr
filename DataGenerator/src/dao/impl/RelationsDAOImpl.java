package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.RealationsDao;
import entity.Relationsdetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class RelationsDAOImpl extends GenericDAOImpl<Relationsdetail, Integer> implements RealationsDao {

	@Override
	public void saveRelation(Relationsdetail relationsdetail) throws PersistException {
		try {
			save(relationsdetail);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistException();
		}
	}

	@Override
	public Relationsdetail getRelationByid(Integer id) throws ReadEntityException {
		Relationsdetail relationsdetail = null;
		try {
			relationsdetail = readById(Relationsdetail.class, id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return relationsdetail;
	}

	@Override
	public List<Relationsdetail> getAllRelationsinDB() throws ReadEntityException {
		List<Relationsdetail> relationsdetails = null;
		try {
			relationsdetails = readAll("Relationsdetail.findAll", Relationsdetail.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return relationsdetails;
	}

	@Override
	public List<String> getAllRelationNames() throws ReadEntityException {
		List<String> listRelationNames = new ArrayList<>();
		for (Relationsdetail relationsdetail : getAllRelationsinDB()) {
			listRelationNames.add(relationsdetail.getDescription());
		}
		return listRelationNames;
	}

	@Override
	public Relationsdetail update(Relationsdetail relationsdetail) throws EntityNotPresent {
		try {
			return update(Relationsdetail.class, relationsdetail.getIdrelations(), relationsdetail);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntityNotPresent();
		}
	}

}
