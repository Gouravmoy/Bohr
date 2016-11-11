package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.StatusPrinter;
import dao.PreDefinedModelDao;
import dao.impl.PreDefinedModelsDaoImpl;
import entity.PreDefinedModels;
import exceptions.PersistException;
import exceptions.ReadEntityException;

public class MainController {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Logger getLogger(Class clazz) {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		RollingFileAppender rfAppender = new RollingFileAppender();
		rfAppender.setContext(loggerContext);
		rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" + "rapid" + ".log");
		FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
		rollingPolicy.setContext(loggerContext);
		// rolling policies need to know their parent
		// it's one of the rare cases, where a sub-component knows about its
		// parent
		rollingPolicy.setParent(rfAppender);
		rollingPolicy.setFileNamePattern(System.getProperty("log_file_loc") + "/log/" + "rapidLog.%i.log.zip");
		rollingPolicy.start();

		SizeBasedTriggeringPolicy triggeringPolicy = new ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy();
		triggeringPolicy.setMaxFileSize("5MB");
		triggeringPolicy.start();

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
		encoder.setPattern("%d{yyyy-MM-dd_HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
		encoder.start();

		rfAppender.setEncoder(encoder);
		rfAppender.setRollingPolicy(rollingPolicy);
		rfAppender.setTriggeringPolicy(triggeringPolicy);

		rfAppender.start();

		Logger hibernateType = loggerContext.getLogger("org.hibernate.type");
		hibernateType.setLevel(Level.ERROR);
		hibernateType.addAppender(rfAppender);

		Logger hibernateAll = loggerContext.getLogger("org.hibernate");
		hibernateAll.setLevel(Level.ERROR);
		hibernateAll.addAppender(rfAppender);

		// attach the rolling file appender to the logger of your choice
		Logger logbackLogger = loggerContext.getLogger(clazz.getName());
		logbackLogger.addAppender(rfAppender);

		// OPTIONAL: print logback internal status messages
		StatusPrinter.print(loggerContext);

		// log something
		logbackLogger.debug("hello");
		return logbackLogger;
	}

	public static void loadPredefData() {
		Properties properties = new Properties();
		PreDefinedModels preDefinedModel = new PreDefinedModels();
		PreDefinedModelDao preDefinedModelDao = new PreDefinedModelsDaoImpl();
		List<PreDefinedModels> preDefinedModels = new ArrayList<>();
		try {
			preDefinedModels = preDefinedModelDao.getAllPreDefinedModelsinDB();
		} catch (ReadEntityException e1) {
			e1.printStackTrace();
		}
		if (preDefinedModels.isEmpty()) {
			try {
				// File propFile = new File(
				// "D:\\Workspaces\\RDGGitStaging\\DataGenerator\\resources\\files\\predefined.properties");
				File propFile = new File(
						"D:\\Workspaces\\RDGGitStaging\\DataGenerator\\resources\\files\\predefined.properties");
				properties.load(new FileInputStream(propFile));
				Enumeration<?> e = properties.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = properties.getProperty(key);
					preDefinedModel = new PreDefinedModels();
					preDefinedModel.setSampelValues(value);
					preDefinedModel.setExpectedColumnName(key.replace("@", ","));
					preDefinedModelDao.savePreDefinedModels(preDefinedModel);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (PersistException e1) {
				e1.printStackTrace();
			}
		}
	}
}
