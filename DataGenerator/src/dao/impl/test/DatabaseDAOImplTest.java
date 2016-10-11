package dao.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import enums.DBType;

/**
 * The class <code>DatabaseDAOImplTest</code> contains tests for the class
 * <code>{@link DatabaseDAOImpl}</code>.
 *
 * @generatedBy CodePro at 10/10/16 3:08 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class DatabaseDAOImplTest {
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
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		fixture.save(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
		List<String> result = fixture.getAllConnectionNames();

		// add additional test code here
		assertEquals("ABC", result.get(0));
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
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		fixture.saveDatabse(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
		List<Databasedetail> result = fixture.getAllDatabaseinDB();

		// add additional test code here
		
		assertNotNull(result);
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
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		
		fixture.saveDatabse(new Databasedetail("", "", "", "", "", DBType.IBM_DB2, "", ""));

		Databasedetail result = fixture.getDatabaseByid(1);

		// add additional test code here
		assertNotNull(result);
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
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		Databasedetail databse = new Databasedetail("", "", "", "", "", DBType.IBM_DB2, "", "");

		fixture.saveDatabse(databse);
		List<Databasedetail> result = fixture.getAllDatabaseinDB();
		assertTrue(result.size() == 1);

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
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		Databasedetail database = new Databasedetail();
		database.setIddatabase(1);

		fixture.update(database);

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
	public void testUpdate_2() throws Exception {
		DatabaseDAOImpl fixture = new DatabaseDAOImpl();
		Databasedetail database = new Databasedetail();
		database.setIddatabase(1);

		fixture.update(database);

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
		// add additional set up code here
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
}