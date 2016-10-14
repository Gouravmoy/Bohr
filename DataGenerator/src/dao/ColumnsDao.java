package dao;

import java.util.List;

import entity.Columnsdetail;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface ColumnsDao {

	public Columnsdetail saveColumn(Columnsdetail columnsdetail) throws PersistException;

	public Columnsdetail getColumnsdetailByid(Integer id) throws ReadEntityException;

	public List<Columnsdetail> getAllColumnsinDB() throws ReadEntityException;

	public List<String> getAllColumnsNames() throws ReadEntityException;

	public Columnsdetail update(Columnsdetail columnsdetail) throws EntityNotPresent;

	public void saveListOfColumns(List<Columnsdetail> columnsdetails) throws PersistException;

}
