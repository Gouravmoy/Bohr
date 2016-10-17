package dao;

import java.util.List;

import entity.Patterndetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface PatternDao {

	public Patterndetail savePattern(Patterndetail patterndetail) throws PersistException;

	public Patterndetail getPatternByid(Integer id) throws ReadEntityException;

	public List<Patterndetail> getAllPatterninDB() throws ReadEntityException;

	public List<String> getAllPatternNames() throws ReadEntityException;

	public Patterndetail update(Patterndetail patterndetail) throws EntityNotPresent;

	public List<Patterndetail> getSchemaByQyery(String query, Object[] pars);


}
