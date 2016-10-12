package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Master;
import dao.DatabaseDao;
import dao.SchemaDao;
import dao.impl.DatabaseDAOImpl;
import dao.impl.SchemaDaoImpl;
import dao.impl.TableDaoImpl;
import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import enums.Environment;
import exceptions.PersistException;
import exceptions.ReadEntityException;

/**
 * The class <code>TableDaoImplTest</code> contains tests for the class
 * <code>{@link TableDaoImpl}</code>.
 *
 * @generatedBy CodePro at 10/11/16 12:07 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class TableDaoImplTest {
	/**
	 * Run the List<Tabledetail> getAllTabledetailinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 12:07 PM
	 */
	@Test
	public void testGetAllTabledetailinDB_1() {
		TableDaoImpl fixture = new TableDaoImpl();
		List<Tabledetail> result = null;
		try {
			result = fixture.getAllTabledetailinDB();
			assertTrue(result.size() >= 0);
		} catch (ReadEntityException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Run the Tabledetail getTabledetailByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 12:07 PM
	 */
	@Test
	public void testGetTabledetailByid_1() throws Exception {
		TableDaoImpl fixture = new TableDaoImpl();
		Integer id = new Integer(1);

		List<Tabledetail> listOfTables = null;
		try {
			listOfTables = fixture.getAllTabledetailinDB();
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}
		if (listOfTables.size() > 0) {
			Tabledetail result = fixture.getTabledetailByid(id);
			assertNotNull(result);
		} else {
			saveNewTable(fixture);
			assertNotNull(fixture.getTabledetailByid(id));
		}

	}

	/**
	 * Run the Tabledetail saveTabledetail(Tabledetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 12:07 PM
	 */
	@Test
	public void testSaveTabledetail_1() {
		TableDaoImpl fixture = new TableDaoImpl();
		try {
			assertNotNull(saveNewTable(fixture));
		} catch (PersistException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Run the void update(Tabledetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 12:07 PM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		TableDaoImpl fixture = new TableDaoImpl();
		Tabledetail tabledetail;
		List<Tabledetail> tabledetails;
		tabledetails = fixture.getAllTabledetailinDB();
		if (tabledetails.size() == 0) {
			tabledetail = saveNewTable(fixture);
		} else {
			tabledetail = fixture.getTabledetailByid(1);
		}
		Integer id = new Integer(tabledetail.getIdtabledetails());
		tabledetail.setTableName("UpdatedTableName");
		fixture.update(tabledetail);
		assertTrue(fixture.getTabledetailByid(id).getTableName().equals("UpdatedTableName"));

	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 12:07 PM
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
	 * @generatedBy CodePro at 10/11/16 12:07 PM
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
	 * @generatedBy CodePro at 10/11/16 12:07 PM
	 */
	public static void main(String[] args) {

		new org.junit.runner.JUnitCore().run(TableDaoImplTest.class);
	}

	private Tabledetail saveNewTable(TableDaoImpl fixture) throws PersistException {
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		SchemaDao schemaDao = new SchemaDaoImpl();
		Tabledetail tabledetail = new Tabledetail();
		Schemadetail schemadetail = new Schemadetail();
		Databasedetail databsedetail = new Databasedetail();

		databsedetail.setConnectionName("TEST_" + ThreadLocalRandom.current().nextInt(0, 999 + 1));
		databaseDao.saveDatabse(databsedetail);
		schemadetail.setDatabasedetail(databsedetail);
		schemaDao.saveSchema(schemadetail);
		tabledetail.setSchemadetail(schemadetail);
		return fixture.saveTabledetail(tabledetail);
	}
}