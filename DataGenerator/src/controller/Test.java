package controller;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import entity.Databasedetail;

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
		/*
		 * rfAppender.setContext(loggerContext);
		 * rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" +
		 * "rapid" + ".log"); logbackLogger = loggerContext.getLogger(
		 * "Sample Part"); logbackLogger.addAppender(rfAppender);
		 */

		Session session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();

		Transaction t = session.beginTransaction();

		Databasedetail databasedetail = new Databasedetail();
		databasedetail = new Databasedetail("1", "", "", "", "0", "", "", "");
		session.persist(databasedetail);

		t.commit();
		session.close();
		logbackLogger.debug("successfully saved");
	}
}
