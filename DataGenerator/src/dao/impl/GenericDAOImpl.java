package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import dao.GenericDAO;
import exceptions.DAOException;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	RollingFileAppender rfAppender = new RollingFileAppender();
	Logger logbackLogger;

	private Session session;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		super();
		rfAppender.setContext(loggerContext);
		rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" + "rapid" + ".log");
		logbackLogger = loggerContext.getLogger("MainController");
		logbackLogger.addAppender(rfAppender);
	}

	private Transaction tx;

	@Override
	public T save(T t) throws DAOException {
		try {
			startOpereation();
			session.save(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return t;
	}

	@Override
	public void batchSaveDAO(List<T> t) throws DAOException {
		try {
			startOpereation();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.save(tSingle);
				} catch (Exception err) {
					logbackLogger.error(err.getMessage(), err);
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
			session.close();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> readAll(String namedQueryName, Class clazz) throws DAOException {
		List<T> listT = null;
		try {
			startOpereation();
			Query query = session.createQuery("from " + clazz.getName());
			listT = query.list();
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return listT;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T readById(Class clazz, ID id) throws DAOException {
		T t = null;
		try {
			startOpereation();
			t = (T) session.get(clazz, id);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T update(Class clazz, ID id, T t) throws DAOException {
		try {
			startOpereation();
			T newEntityRef = (T) session.merge(t);
			session.update(newEntityRef);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
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
			startOpereation();
			Criteria queryCriteria = session.createCriteria(clazz);
			queryCriteria.setFirstResult(0);
			queryCriteria.setMaxResults(1);
			t = (T) queryCriteria.list().get(0);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> getByQuery(String queryExecute, Object[] pars, Class clazz) throws DAOException {
		List<T> results = null;
		try {
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
			session.close();
		}
		return results;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public T saveOrUpdate(Class clazz, T t) throws DAOException {
		try {
			startOpereation();
			session.saveOrUpdate(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return t;
	}

	@Override
	public void saveOrUpdateBatch(List<T> t) throws DAOException {
		try {
			startOpereation();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.saveOrUpdate(tSingle);
				} catch (Exception err) {
					logbackLogger.error(err.getMessage(), err);
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
			session.close();
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void delete(Class clazz, T t) throws DAOException {
		try {
			startOpereation();
			session.delete(t);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}

	}

	@Override
	public boolean deleteById(Class<?> clazz, Serializable id) throws DAOException {
		try {
			startOpereation();
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
			session.close();
		}
		return false;
	}

	@Override
	public void deleteBatch(List<T> t) throws DAOException {
		try {
			startOpereation();
			int i = 0;
			for (T tSingle : t) {
				i++;
				try {
					session.delete(tSingle);
				} catch (Exception err) {
					logbackLogger.error(err.getMessage(), err);
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
			session.close();
		}

	}

	protected void handleException(HibernateException e) throws DAOException {
		logbackLogger.error(e.getMessage(), e);
		tx.rollback();
		throw new DAOException(e);
	}

	private void startOpereation() {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		tx = session.beginTransaction();
	}

}
