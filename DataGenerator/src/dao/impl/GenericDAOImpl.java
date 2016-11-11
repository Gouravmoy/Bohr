package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import common.Master;
import dao.GenericDAO;
import enums.Environment;
import exceptions.DAOException;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	private Session session;
	Configuration configuration;

	public GenericDAOImpl() {
		super();
		if (configuration == null) {
			startOpereation();
		}
	}

	private Transaction tx;

	@Override
	public T save(T t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			session.save(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return t;
	}

	@Override
	public void batchSaveDAO(List<T> t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.save(tSingle);
				} catch (Exception err) {
					throw new DAOException();
				}
				if (i % 20 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> readAll(String namedQueryName, Class clazz) throws DAOException {
		List<T> listT = null;
		try {
			buildSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from " + clazz.getName());
			listT = query.list();
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return listT;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T readById(Class clazz, ID id) throws DAOException {
		T t = null;
		try {
			buildSession();
			tx = session.beginTransaction();
			t = (T) session.get(clazz, id);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.getSessionFactory().close();
			session.close();

		}
		return t;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public T update(Class clazz, ID id, T t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			// T newEntityRef = (T) session.merge(t);
			session.update(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return t;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isEntityExists(Class clazz, ID id) {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getFirstRecord(Class<?> clazz) throws DAOException {
		T t = null;
		try {
			buildSession();
			tx = session.beginTransaction();
			Criteria queryCriteria = session.createCriteria(clazz);
			queryCriteria.setFirstResult(0);
			queryCriteria.setMaxResults(1);
			t = (T) queryCriteria.list().get(0);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> getByQuery(String queryExecute, Object[] pars, Class clazz) throws DAOException {
		List<T> results = null;
		try {
			buildSession();
			Query query = session.createQuery(queryExecute);
			if (pars != null) {
				for (int i = 0; i < pars.length; i++) {
					query.setParameter("arg" + i, pars[i]);
				}
			}
			results = query.list();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return results;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public T saveOrUpdate(Class clazz, T t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return t;
	}

	@Override
	public void saveOrUpdateBatch(List<T> t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.update(tSingle);
				} catch (Exception err) {
					throw new DAOException();
				}
				if (i % 5 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory
					session.flush();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void delete(Class clazz, T t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			session.delete(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}

	}

	@Override
	public boolean deleteById(Class<?> clazz, Serializable id) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			Object persistentInstance = session.load(clazz, id);
			if (persistentInstance != null) {
				session.delete(persistentInstance);
				session.flush();
				tx.commit();
				return true;
			}
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}
		return false;
	}

	@Override
	public void deleteBatch(List<T> t) throws DAOException {
		try {
			buildSession();
			tx = session.beginTransaction();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.delete(tSingle);
				} catch (Exception err) {
					throw new DAOException();
				}
				if (i % 20 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory
					session.flush();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.getSessionFactory().close();
			session.close();
		}

	}

	protected void handleException(HibernateException e) throws DAOException {
		e.printStackTrace();
		tx.rollback();
		throw new DAOException(e);
	}

	private void startOpereation() {
		if (Master.INSTANCE.getEnvironment() == Environment.PROD) {
			configuration = new AnnotationConfiguration().configure("/environment/hibernate.cfg.staging.xml");
		} else if (Master.INSTANCE.getEnvironment() == Environment.TEST) {
			configuration = new AnnotationConfiguration().configure("/environment/hibernate.cfg.staging.xml");
		} else if (Master.INSTANCE.getEnvironment() == Environment.STAGING) {
			configuration = new AnnotationConfiguration().configure("/environment/hibernate.cfg.staging.xml");
		} else {
			configuration = new AnnotationConfiguration().configure("/environment/hibernate.cfg.staging.xml");
		}
		if (Master.INSTANCE.isClearAll())
			configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		else
			configuration.setProperty("hibernate.hbm2ddl.auto", "update");
	}

	private void buildSession() {
		if (session != null) {
			if (session.isOpen()) {
				session.clear();
				session.close();
			}
			session = session.getSessionFactory().openSession();
			return;
		}
		session = configuration.buildSessionFactory().openSession();
	}

}