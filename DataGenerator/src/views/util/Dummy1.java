package views.util;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

public class Dummy1 {
	@Inject
	public Dummy1() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(null);
	}
}
