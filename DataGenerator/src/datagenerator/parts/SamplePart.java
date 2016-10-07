package datagenerator.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import controller.Test;

public class SamplePart {

	private Text txtInput;
	private TableViewer tableViewer;

	static LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	static RollingFileAppender rfAppender = new RollingFileAppender();
	static Logger logbackLogger;

	@Inject
	private MDirtyable dirty;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void createComposite(Composite parent) {

		rfAppender.setContext(loggerContext);
		rfAppender.setFile(System.getProperty("log_file_loc") + "/log/" + "rapid" + ".log");
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
		encoder.setPattern("%d{yyyy-MM-dd_HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
		encoder.start();

		rfAppender.setEncoder(encoder);
		logbackLogger = loggerContext.getLogger("Sample Part");
		logbackLogger.addAppender(rfAppender);

		logbackLogger.info("First Log from Data Generator");
		logbackLogger.error("First Log from Data Generator");
		
		Test test = new Test();
		test.saveEmployees();

		parent.setLayout(new GridLayout(1, false));

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Enter text to mark part as dirty");
		txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty.setDirty(true);
				logbackLogger.error("First Log from Data Generator");
			}
		});
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		tableViewer = new TableViewer(parent);

		tableViewer.add("Sample item 1");
		tableViewer.add("Sample item 2");
		tableViewer.add("Sample item 3");
		tableViewer.add("Sample item 4");
		tableViewer.add("Sample item 5");
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}
}