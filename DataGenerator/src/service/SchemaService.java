package service;

import java.util.List;

import entity.Schemadetail;

public interface SchemaService {

	public List<Schemadetail> getAllSchemaForDBID(Integer id);

}
