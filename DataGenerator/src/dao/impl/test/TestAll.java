package dao.impl.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import common.Master;
import enums.Environment;

/**
 * The class <code>TestAll</code> builds a suite that can be used to run all of
 * the tests within its package as well as within any subpackages of its
 * package.
 *
 * @generatedBy CodePro at 10/10/16 5:02 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ DatabaseDAOImplTest.class, SchemaDaoImplTest.class, TableDaoImplTest.class,
		ColumnsDAOImplTest.class, ConstraintsDAOImplTest.class, PatternDAOImplTest.class, RelationsDAOImplTest.class })
public class TestAll {

	/**
	 * Launch the test.
	 *
	 * @param args
	 *            the command line arguments
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */

	public static void main(String[] args) {
		Master.INSTANCE.setEnvironment(Environment.TEST);
		JUnitCore.runClasses(new Class[] { TestAll.class });
	}
}