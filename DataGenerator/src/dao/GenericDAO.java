package dao;

import java.io.Serializable;
import java.util.List;

import exceptions.DAOException;
import exceptions.EntityNotPresent;

public interface GenericDAO<T, ID extends Serializable> {

	public T save(T t) throws DAOException;

	public void batchSaveDAO(List<T> t) throws DAOException;

	public List<T> readAll(String namedQueryName, @SuppressWarnings("rawtypes") Class clazz) throws DAOException;

	public T readById(@SuppressWarnings("rawtypes") Class clazz, ID id) throws DAOException;

	public T update(@SuppressWarnings("rawtypes") Class clazz, ID id, T t) throws EntityNotPresent, DAOException;

	public boolean isEntityExists(@SuppressWarnings("rawtypes") Class clazz, ID id);

	T getFirstRecord(Class<?> clazz) throws DAOException;

	List<T> getByQuery(String queryExecute, Object[] pars, @SuppressWarnings("rawtypes") Class clazz) throws DAOException;

	public T saveOrUpdate(@SuppressWarnings("rawtypes") Class clazz, T t) throws DAOException;

	public void saveOrUpdateBatch(List<T> t) throws DAOException;

	void delete(@SuppressWarnings("rawtypes") Class clazz, T t) throws EntityNotPresent, DAOException;

	boolean deleteById(Class<?> clazz, Serializable id) throws EntityNotPresent, DAOException;

	void deleteBatch(List<T> t) throws DAOException;

}
