package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.impl.PatternDAOImpl;
import entity.Patterndetail;

/**
 * The class <code>PatternDAOImplTest</code> contains tests for the class
 * <code>{@link PatternDAOImpl}</code>.
 *
 * @generatedBy CodePro at 10/11/16 1:49 PM
 * @author M1026352
 * @version $Revision: 1.0 $
 */
public class PatternDAOImplTest {

	/**
	 * Run the List<String> getAllPatternNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	PatternDAOImpl fixture = new PatternDAOImpl();

	@Test
	public void testGetAllPatternNames_1() throws Exception {
		PatternDAOImpl fixture = new PatternDAOImpl();
		Patterndetail patterndetail = new Patterndetail();
		fixture.save(patterndetail);
		List<String> result = fixture.getAllPatternNames();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the List<String> getAllPatternNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testGetAllPatternNames_2() throws Exception {

		List<String> result = fixture.getAllPatternNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at dao.impl.PatternDAOImpl.getAllPatterninDB(PatternDAOImpl.java:44)
		// at dao.impl.PatternDAOImpl.getAllPatternNames(PatternDAOImpl.java:56)
		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllPatternNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testGetAllPatternNames_3() throws Exception {

		List<String> result = fixture.getAllPatternNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at dao.impl.PatternDAOImpl.getAllPatterninDB(PatternDAOImpl.java:44)
		// at dao.impl.PatternDAOImpl.getAllPatternNames(PatternDAOImpl.java:56)
		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllPatternNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testGetAllPatternNames_4() throws Exception {

		List<String> result = fixture.getAllPatternNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at dao.impl.PatternDAOImpl.getAllPatterninDB(PatternDAOImpl.java:44)
		// at dao.impl.PatternDAOImpl.getAllPatternNames(PatternDAOImpl.java:56)
		assertNotNull(result);
	}

	/**
	 * Run the List<Patterndetail> getAllPatterninDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testGetAllPatterninDB_1() throws Exception {
		PatternDAOImpl fixture = new PatternDAOImpl();
		Patterndetail patterndetail = new Patterndetail();
		fixture.save(patterndetail);
		List<Patterndetail> result = fixture.getAllPatterninDB();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the Patterndetail getPatternByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testGetPatternByid_1() throws Exception {
		Integer id = new Integer(1);
		// Columnsdetail columnsdetail =
		// CreateColumnPreClass.addColumnToDatabase();
		Patterndetail patterndetail = new Patterndetail();
		fixture.save(patterndetail);
		Patterndetail result = fixture.getPatternByid(id);

		assertNotNull(result);
	}

	/**
	 * Run the Patterndetail savePattern(Patterndetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testSavePattern_1() throws Exception {
		Patterndetail patterndetail = new Patterndetail();
		Patterndetail result = fixture.savePattern(patterndetail);
		assertNotNull(result);
	}

	/**
	 * Run the Patterndetail update(Patterndetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		Patterndetail patterndetail = new Patterndetail();
		// Columnsdetail columnsdetail =
		// CreateColumnPreClass.addColumnToDatabase();
		fixture.savePattern(patterndetail);
		patterndetail.setPatternName("Updated_Name");

		Patterndetail result = fixture.update(patterndetail);

		assertNotNull(result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@Before
	public void setUp() throws Exception {
		//Master.INSTANCE.setEnvironment(Environment.TEST);
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args
	 *            the command line arguments
	 *
	 * @generatedBy CodePro at 10/11/16 1:49 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(PatternDAOImplTest.class);
	}
}