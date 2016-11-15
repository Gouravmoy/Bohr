package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.PatternDao;
import dao.impl.PatternDAOImpl;
import entity.Patterndetail;
import service.PatternService;

public class PatternServiceImpl implements PatternService {

	PatternDao patternDao = new PatternDAOImpl();

	@Override
	public List<Patterndetail> getAllPatternForProjectId(Integer id) {
		List<Patterndetail> patterndetails = new ArrayList<>();
		String query = "from Patterndetail p where p.projectdetail.idproject=:arg0";
		Object[] pars = { id };
		patterndetails = patternDao.getSchemaByQyery(query, pars);
		return patterndetails;
	}

	@Override
	public Patterndetail getPatternForColumnId(Integer id,Integer columnId) {
		List<Patterndetail> patterndetails = new ArrayList<>();
		String query = "from Patterndetail p where p.columnsdetail.idcolumnsdetails=:arg0 and p.projectdetail.idproject=:arg1";
		Object[] pars = { id ,columnId};
		patterndetails = patternDao.getSchemaByQyery(query, pars);
		if (patterndetails != null)
			return patterndetails.get(0);
		return null;
	}

}
