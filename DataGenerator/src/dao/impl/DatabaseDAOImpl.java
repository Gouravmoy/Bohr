package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import dao.DatabaseDao;
import entity.Databasedetail;
import exceptions.EntityAlreadyExists;
import exceptions.EntityNotPresent;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class DatabaseDAOImpl extends GenericDAOImpl<Databasedetail, Long> implements DatabaseDao {

	LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	RollingFileAppender rfAppender = new RollingFileAppender();
	Logger logbackLogger;

	@SuppressWarnings("unchecked")
	public DatabaseDAOImpl() {
		super();
		rfAppender.setContext(loggerContext);
		rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" + "rapid" + ".log");
		logbackLogger = loggerContext.getLogger("MainController");
		logbackLogger.addAppender(rfAppender);
	}

	@Override
	public void saveDatabse(Databasedetail databse) throws PersistException {
		try {
			List<String> dbNames = getAllConnectionNames();
			if (dbNames.contains(databse.getConnectionName())) {
				throw new EntityAlreadyExists(
						"Database - " + databse.getConnectionName() + " already exists in the databse");
			}
			save(databse);
		} catch (Exception err) {
			err.printStackTrace();
			logbackLogger.error(err.getMessage(), err);
			throw new PersistException("Could not persist Database Data - " + err.getMessage() + databse);
		}

	}

	@Override
	public Databasedetail getDatabaseByid(Long id) throws ReadEntityException {
		try {
			return readById(Databasedetail.class, id);
		} catch (Exception err) {
			logbackLogger.error(err.getMessage(), err);
			throw new ReadEntityException("Could not get Database Data for ID - " + id);
		}
	}

	@Override
	public List<Databasedetail> getAllDatabaseinDB() throws ReadEntityException {
		List<Databasedetail> databases;
		try {
			databases = readAll("Databasedetail.findAll", Databasedetail.class);
		} catch (Exception err) {
			logbackLogger.error(err.getMessage(), err);
			throw new ReadEntityException("Could not get All Database Information");
		}
		return databases;
	}

	@Override
	public List<String> getAllConnectionNames() throws ReadEntityException {
		List<Databasedetail> databases;
		List<String> dbConnectionNames;
		dbConnectionNames = new ArrayList<>();
		try {
			databases = getAllDatabaseinDB();
			for (Databasedetail database : databases) {
				dbConnectionNames.add(database.getConnectionName());
			}
		} catch (Exception err) {
			logbackLogger.error(err.getMessage(), err);
			throw new ReadEntityException("Could not get All Database Information");
		}
		return dbConnectionNames;
	}

	@Override
	public void update(Databasedetail database) throws EntityNotPresent {
		try {
			update(Databasedetail.class, Long.valueOf(database.getIddatabase()), database);
		} catch (EntityNotPresent err) {
			throw new EntityNotPresent("Could not get Update Database Information");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
