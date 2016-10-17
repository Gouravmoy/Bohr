package service;

import java.util.List;

import entity.Relationsdetail;

public interface RelationService {

	public List<Relationsdetail> getAllRelationsForProjectId(Integer id);

	public Relationsdetail getRelationForColumnId(Integer id, Integer projectId);

}
