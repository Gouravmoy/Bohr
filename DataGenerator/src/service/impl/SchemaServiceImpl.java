package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Schemadetail;
import service.SchemaService;

public class SchemaServiceImpl implements SchemaService {

	SchemaDao schemaDao = new SchemaDaoImpl();

	@Override
	public List<Schemadetail> getAllSchemaForDBID(Integer Id) {
		List<Schemadetail> schemadetails = new ArrayList<>();
		String query = "from Schemadetail s where s.databasedetail.iddatabase=:arg0";
		Object[] pars = { Id };
		schemadetails = schemaDao.getSchemaByQyery(query, pars);
		return schemadetails;
	}

}
