package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.RealationsDao;
import dao.impl.RelationsDAOImpl;
import entity.Relationsdetail;
import service.RelationService;

public class RelationServiceImpl implements RelationService {

	RealationsDao relationDao = new RelationsDAOImpl();

	@Override
	public List<Relationsdetail> getAllRelationsForProjectId(Integer id) {
		List<Relationsdetail> relationsdetails = new ArrayList<>();
		String query = "from Relationsdetail r where r.projectdetail.idproject=:arg0";
		Object[] pars = { id };
		relationsdetails = relationDao.getSchemaByQyery(query, pars);
		return relationsdetails;
	}

	@Override
	public Relationsdetail getRelationForColumnId(Integer id, Integer tableId) {
		List<Relationsdetail> relationsdetail = new ArrayList<>();
		String query = "from Relationsdetail r where r.columnsdetail.idcolumnsdetails=:arg0 and r.projectdetail.idproject=:arg1";
		Object[] pars = { id, tableId };
		relationsdetail = relationDao.getSchemaByQyery(query, pars);
		if (relationsdetail != null) {
			if (relationsdetail.size() > 0)
				return relationsdetail.get(0);
		}
		return null;
	}

}
