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
import dao.impl.DatabaseDAOImpl;
import dao.impl.SchemaDaoImpl;
import entity.Databasedetail;
import entity.Schemadetail;
import enums.Environment;
import exceptions.DAOException;
import exceptions.PersistException;
import exceptions.ReadEntityException;

/**
 * The class <code>SchemaDaoImplTest</code> contains tests for the class
 * <code>{@link SchemaDaoImpl}</code>.
 *
 * @generatedBy CodePro at 10/10/16 5:02 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class SchemaDaoImplTest {
	/**
	 * Run the List<Schemadetail> getAllSchemainDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */
	@Test
	public void testGetAllSchemainDB_1() {
		SchemaDaoImpl fixture = new SchemaDaoImpl();
		List<Schemadetail> result = null;
		try {
			result = fixture.getAllSchemainDB();
			assertTrue(result.size() >= 0);
		} catch (ReadEntityException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Run the Schemadetail getSchemaByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */
	@Test
	public void testGetSchemaByid_1() {
		SchemaDaoImpl fixture = new SchemaDaoImpl();
		Integer id = new Integer(1);
		try {
			List<Schemadetail> schemadetails = fixture.getAllSchemainDB();
			if (schemadetails.size() > 0) {
				Schemadetail result = fixture.getSchemaByid(id);
				assertNotNull(result);
			} else {
				saveNewSchema(fixture);
				Schemadetail result = fixture.getSchemaByid(id);
				assertNotNull(result);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Run the void saveSchema(Schemadetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */
	@Test
	public void testSaveSchema_1() {
		SchemaDaoImpl fixture = new SchemaDaoImpl();
		try {
			assertNotNull(saveNewSchema(fixture));
		} catch (DAOException err) {
			assertTrue(false);
		}
	}

	/**
	 * Run the void update(Schemadetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */
	@Test
	public void testUpdate_1() {
		SchemaDaoImpl fixture = new SchemaDaoImpl();
Schemadetail schemadetail;
		try {
			List<Schemadetail> schemadetails;
			schemadetails = fixture.getAllSchemainDB();
			if (schemadetails.size() == 0) {
				schemadetail = saveNewSchema(fixture);
			} else {
				schemadetail = fixture.getSchemaByid(1);
			}
			Integer id = new Integer(schemadetail.getIdschema());
			schemadetail.setName("UpdatedSchemaName");
			fixture.update(schemadetail);
			assertTrue(fixture.getSchemaByid(id).getName().equals("UpdatedSchemaName"));
		} catch (DAOException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/10/16 5:02 PM
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
	 * @generatedBy CodePro at 10/10/16 5:02 PM
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
	 * @generatedBy CodePro at 10/10/16 5:02 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(SchemaDaoImplTest.class);
	}

	private Schemadetail saveNewSchema(SchemaDaoImpl fixture) throws PersistException {
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		Schemadetail schemadetail = new Schemadetail();
		Databasedetail databsedetail = new Databasedetail();

		databsedetail.setConnectionName("TEST_" + ThreadLocalRandom.current().nextInt(0, 999 + 1));
		databaseDao.saveDatabse(databsedetail);
		schemadetail.setDatabasedetail(databsedetail);
		return fixture.saveSchema(schemadetail);
	}
}