package dao;

import java.util.List;

import entity.Datasamplemodel;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public interface DataSampleDao {
	public Datasamplemodel saveDatasamplemodel(Datasamplemodel datasamplemodel) throws PersistException;

	public Datasamplemodel getDatasamplemodelByid(Integer id) throws ReadEntityException;

	public List<Datasamplemodel> getAllDatasamplemodelinDB() throws ReadEntityException;

	public void update(Datasamplemodel datasamplemodel) throws EntityNotPresent;
}
