package dao.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import dao.impl.SchemaDaoImpl;
import entity.Databasedetail;
import entity.Schemadetail;
import enums.DBType;
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
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		List<Schemadetail> result = null;
		try {
			Databasedetail databasedetail = databaseDao
					.saveDatabse(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
			Schemadetail schemadetail = new Schemadetail();
			schemadetail.setDatabasedetail(databasedetail);
			fixture.saveSchema(schemadetail);
			result = fixture.getAllSchemainDB();
			assertEquals(result.size(), 1);
		} catch (ReadEntityException | PersistException e) {
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
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		SchemaDaoImpl fixture = new SchemaDaoImpl();

		try{
		Databasedetail databasedetail = databaseDao
				.saveDatabse(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
		Schemadetail schemadetail = new Schemadetail();
		schemadetail.setDatabasedetail(databasedetail);

		fixture.saveSchema(schemadetail);

		Integer id = new Integer(1);

		Schemadetail result = fixture.getSchemaByid(id);
		assertEquals(result.getIdschema(), 1);
		}catch(DAOException e){
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
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		SchemaDaoImpl fixture = new SchemaDaoImpl();

		try {
			Databasedetail databasedetail = databaseDao
					.saveDatabse(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
			Schemadetail schemadetail = new Schemadetail();
			schemadetail.setDatabasedetail(databasedetail);

			fixture.saveSchema(schemadetail);
			assertEquals(schemadetail.getIdschema(), 1);
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
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		SchemaDaoImpl fixture = new SchemaDaoImpl();

		try {
			Databasedetail databasedetail = databaseDao
					.saveDatabse(new Databasedetail("ABC", "", "", "", "", DBType.IBM_DB2, "", ""));
			Schemadetail schemadetail = new Schemadetail();
			schemadetail.setDatabasedetail(databasedetail);

			fixture.saveSchema(schemadetail);
			schemadetail.setName("ABC");
			fixture.update(schemadetail);
			assertEquals(fixture.getAllSchemainDB().get(0).getName(), "ABC");
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
		// add additional set up code here
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
}