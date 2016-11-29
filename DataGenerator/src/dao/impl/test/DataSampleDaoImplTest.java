package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.DatabaseDao;
import dao.SchemaDao;
import dao.TableDao;
import dao.impl.DataSampleDaoImpl;
import dao.impl.DatabaseDAOImpl;
import dao.impl.SchemaDaoImpl;
import dao.impl.TableDaoImpl;
import entity.Databasedetail;
import entity.Datasamplemodel;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.DAOException;
import exceptions.ReadEntityException;

/**
 * The class <code>DataSampleDaoImplTest</code> contains tests for the class
 * <code>{@link DataSampleDaoImpl}</code>.
 *
 * @generatedBy CodePro at 10/11/16 7:57 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class DataSampleDaoImplTest {
	/**
	 * Run the List<Datasamplemodel> getAllDatasamplemodelinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	@Test
	public void testGetAllDatasamplemodelinDB_1() {
		DataSampleDaoImpl fixture = new DataSampleDaoImpl();
		List<Datasamplemodel> result = null;
		try {
			result = fixture.getAllDatasamplemodelinDB();
			assertTrue(result.size() >= 0);
		} catch (ReadEntityException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Run the Datasamplemodel getDatasamplemodelByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	@Test
	public void testGetDatasamplemodelByid_1() throws Exception {
		DataSampleDaoImpl fixture = new DataSampleDaoImpl();
		Integer id = new Integer(1);

		List<Datasamplemodel> datasamplemodels = null;
		try {
			datasamplemodels = fixture.getAllDatasamplemodelinDB();
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}
		if (datasamplemodels.size() > 0) {
			Datasamplemodel result = fixture.getDatasamplemodelByid(id);
			assertNotNull(result);
		} else {
			saveNewDataSample(fixture);
			assertNotNull(fixture.getDatasamplemodelByid(id));
		}
	}

	/**
	 * Run the Datasamplemodel saveDatasamplemodel(Datasamplemodel) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	@Test
	public void testSaveDatasamplemodel_1() throws Exception {
		DataSampleDaoImpl fixture = new DataSampleDaoImpl();
		Datasamplemodel datasamplemodel = new Datasamplemodel();

		Datasamplemodel result = fixture.saveDatasamplemodel(datasamplemodel);
		assertNotNull(result);
	}

	/**
	 * Run the void update(Datasamplemodel) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		DataSampleDaoImpl fixture = new DataSampleDaoImpl();
		Datasamplemodel datasamplemodel = new Datasamplemodel();
	//	((Object) datasamplemodel).setIddatasamplemodel(1);

		fixture.update(datasamplemodel);
	}

	/**
	 * Run the void update(Datasamplemodel) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	@Test
	public void testUpdate_2() throws Exception {
		DataSampleDaoImpl fixture = new DataSampleDaoImpl();
		Datasamplemodel datasamplemodel = new Datasamplemodel();
		//datasamplemodel.setIddatasamplemodel(1);

		fixture.update(datasamplemodel);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 7:57 PM
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
	 * @generatedBy CodePro at 10/11/16 7:57 PM
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
	 * @generatedBy CodePro at 10/11/16 7:57 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(DataSampleDaoImplTest.class);
	}
	
	private Datasamplemodel saveNewDataSample(DataSampleDaoImpl fixture) throws DAOException{
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		SchemaDao schemaDao = new SchemaDaoImpl();
		TableDao tableDao = new TableDaoImpl();
		Tabledetail tabledetail = new Tabledetail();
		Schemadetail schemadetail = new Schemadetail();
		Databasedetail databsedetail = new Databasedetail();
		Datasamplemodel datasamplemodel = new Datasamplemodel();

		databsedetail.setConnectionName("TEST_" + ThreadLocalRandom.current().nextInt(0, 999 + 1));
		databaseDao.saveDatabse(databsedetail);
		schemadetail.setDatabasedetail(databsedetail);
		schemaDao.saveSchema(schemadetail);
		tabledetail.setSchemadetail(schemadetail);
		tableDao.saveTabledetail(tabledetail);
		return fixture.save(datasamplemodel);
	}
}