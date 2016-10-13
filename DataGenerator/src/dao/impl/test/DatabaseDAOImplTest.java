package dao.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Master;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import enums.Environment;
import exceptions.PersistException;
import exceptions.ReadEntityException;

/**
 * The class <code>DatabaseDAOImplTest</code> contains tests for the class
 * <code>{@link DatabaseDAOImpl}</code>.
 *
 * @generatedBy CodePro at 10/10/16 3:08 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class DatabaseDAOImplTest {
	DatabaseDAOImpl fixture = new DatabaseDAOImpl();

	/**
	 * Run the DatabaseDAOImpl() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testDatabaseDAOImpl_1() throws Exception {

		DatabaseDAOImpl result = new DatabaseDAOImpl();

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllConnectionNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testGetAllConnectionNames_1() throws Exception {
		// DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		List<Databasedetail> databasedetails = fixture.getAllDatabaseinDB();
		List<String> result = fixture.getAllConnectionNames();

		// add additional test code here
		assertEquals(result.size(), databasedetails.size());
	}

	/**
	 * Run the List<Databasedetail> getAllDatabaseinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testGetAllDatabaseinDB_1() throws Exception {
		// DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		List<Databasedetail> result = fixture.getAllDatabaseinDB();

		// add additional test code here

		assertTrue(result.size() >= 0);
	}

	/**
	 * Run the Databasedetail getDatabaseByid(Long) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testGetDatabaseByid_1() throws Exception {
		// DatabaseDAOImpl fixture = new DatabaseDAOImpl();

		List<Databasedetail> listOfTables = null;
		Integer id = new Integer(1);
		try {
			listOfTables = fixture.getAllDatabaseinDB();
		} catch (ReadEntityException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		if (listOfTables.size() > 0) {
			Databasedetail result = fixture.getDatabaseByid(id);
			assertNotNull(result);
		} else {
			saveNewDatabase(fixture);
			assertNotNull(fixture.getDatabaseByid(id));
		}
	}

	/**
	 * Run the void saveDatabse(Databasedetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testSaveDatabse_1() throws Exception {
		// DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		assertNotNull(saveNewDatabase(fixture));

		// add additional test code here
	}

	/**
	 * Run the void update(Databasedetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		// DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		Databasedetail databasedetail;
		List<Databasedetail> databasedetails;
		databasedetails = fixture.getAllDatabaseinDB();
		if (databasedetails.size() == 0) {
			databasedetail = saveNewDatabase(fixture);
		} else {
			databasedetail = fixture.getDatabaseByid(1);
		}
		Integer id = new Integer(databasedetail.getIddatabase());
		databasedetail.setConnectionName("UpdatedDatabaseConnectionName");
		fixture.update(databasedetail);
		assertTrue(fixture.getDatabaseByid(id).getConnectionName().equals("UpdatedDatabaseConnectionName"));

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	@Before
	public void setUp() throws Exception {
		Master.INSTANCE.setEnvironment(Environment.TEST);
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 10/10/16 3:08 PM
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
	 * @generatedBy CodePro at 10/10/16 3:08 PM
	 */
	public static void main(String[] args) {

		new org.junit.runner.JUnitCore().run(DatabaseDAOImplTest.class);
	}

	private Databasedetail saveNewDatabase(DatabaseDAOImpl fixture) throws PersistException {
		Databasedetail databsedetail = new Databasedetail();

		//databsedetail.setConnectionName("TEST_" + ThreadLocalRandom.current().nextInt(0, 999 + 1));
		return fixture.saveDatabse(databsedetail);
	}
}