package datagenerator.parts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class SamplePart {

	static LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	static RollingFileAppender rfAppender = new RollingFileAppender();
	static Logger logbackLogger;

	@Inject
	private MDirtyable dirty;

	@Inject
	public static EPartService partService = null;
	@Inject
	public static EModelService modelService = null;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(null);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				URL url = null;
				try {
					url = new URL("platform:/plugin/DataGenerator/resources/images/tdg.png");
				} catch (MalformedURLException e) {
					logbackLogger.error(e.getMessage(), e);
				}
				Label lblNewLabel = new Label(parent, SWT.CENTER);
				lblNewLabel.setBounds(10, 10, 787, 418);
				lblNewLabel.setText("New Label");
				try {
					lblNewLabel.setImage(new Image(Display.getDefault(), url.openConnection().getInputStream()));
				} catch (IOException e) {
					logbackLogger.error(e.getMessage(), e);
				}
			}
		});
	}

	@Focus
	public void setFocus() {
	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}
}