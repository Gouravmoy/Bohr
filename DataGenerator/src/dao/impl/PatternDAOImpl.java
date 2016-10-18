package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.PatternDao;
import entity.Patterndetail;
import exceptions.DAOException;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class PatternDAOImpl extends GenericDAOImpl<Patterndetail, Integer> implements PatternDao {

	@Override
	public Patterndetail savePattern(Patterndetail patterndetail) throws PersistException {

		try {
			return save(patterndetail);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new PersistException();
		}

	}

	@Override
	public Patterndetail getPatternByid(Integer id) throws ReadEntityException {
		Patterndetail patterndetail = null;
		try {
			patterndetail=readById(Patterndetail.class, id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return patterndetail;
	}

	@Override
	public List<Patterndetail> getAllPatterninDB() throws ReadEntityException {
		List<Patterndetail> patterndetails = null;
		try {
			patterndetails = readAll("Patterndetail.findAll", Patterndetail.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ReadEntityException();
		}
		return patterndetails;
	}

	@Override
	public List<String> getAllPatternNames() throws ReadEntityException {
		List<String> listPatternNames = new ArrayList<>();
		for (Patterndetail patterndetail : getAllPatterninDB()) {
			listPatternNames.add(patterndetail.getPatternName());
		}
		return listPatternNames;
	}

	@Override
	public Patterndetail update(Patterndetail patterndetail) throws EntityNotPresent {
		try {
			return update(Patterndetail.class, patterndetail.getIdpatterndetails(), patterndetail);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntityNotPresent();
		}

	}

	@Override
	public List<Patterndetail> getSchemaByQyery(String query, Object[] pars) {
		try {
			return getByQuery(query, pars, Patterndetail.class);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
