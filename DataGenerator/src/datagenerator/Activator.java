package datagenerator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import common.Master;
import controller.MainController;
import enums.Environment;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Master.INSTANCE.setEnvironment(Environment.PROD);
		Master.INSTANCE.setClearAll(false);
		MainController.loadPredefData();
		MainController.getLogger(Activator.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
