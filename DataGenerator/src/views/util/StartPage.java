
package views.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class StartPage {
	LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	@SuppressWarnings("rawtypes")
	RollingFileAppender rfAppender = new RollingFileAppender();
	Logger logbackLogger;

	@Inject
	public StartPage() {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
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
}