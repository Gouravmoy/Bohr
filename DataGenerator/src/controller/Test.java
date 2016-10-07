package controller;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import enums.DBType;
import exceptions.PersistException;

public class Test {

	@SuppressWarnings("unchecked")
	public Test() {

		super();
		rfAppender.setContext(loggerContext);
		rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" + "rapid" + ".log");
		logbackLogger = loggerContext.getLogger("Sample Part");
		logbackLogger.addAppender(rfAppender);
	}

	static LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	static RollingFileAppender rfAppender = new RollingFileAppender();
	static Logger logbackLogger;

	public void saveEmployees() {

		Databasedetail databasedetail = new Databasedetail();
		databasedetail = new Databasedetail("1", "", "", "", "0", DBType.IBM_DB2, "", "");
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		try {
			databaseDao.saveDatabse(databasedetail);
		} catch (PersistException e) {
			e.printStackTrace();
		}

		logbackLogger.debug("successfully saved");
	}
}
