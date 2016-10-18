package service;

import java.util.List;

import entity.Patterndetail;

public interface PatternService {
	public List<Patterndetail> getAllPatternForProjectId(Integer id);

	public Patterndetail getPatternForColumnId(Integer id);

}
