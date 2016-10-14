package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.impl.RelationsDAOImpl;
import entity.Columnsdetail;
import entity.Relationsdetail;

/**
 * The class <code>RelationsDAOImplTest</code> contains tests for the class
 * <code>{@link RelationsDAOImpl}</code>.
 *
 * @generatedBy CodePro at 10/11/16 5:18 PM
 * @author M1026352
 * @version $Revision: 1.0 $
 */
public class RelationsDAOImplTest {

	/**
	 * Run the List<String> getAllRelationNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	RelationsDAOImpl fixture = new RelationsDAOImpl();

	@Test
	public void testGetAllRelationNames_1() throws Exception {
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Relationsdetail relationsdetail = new Relationsdetail();
		relationsdetail.setColumnsdetail(columnsdetail);
		fixture.save(relationsdetail);
		List<String> result = fixture.getAllRelationNames();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the List<String> getAllRelationNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testGetAllRelationNames_2() throws Exception {

		List<String> result = fixture.getAllRelationNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationsinDB(RelationsDAOImpl.java:43)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationNames(RelationsDAOImpl.java:55)
		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllRelationNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testGetAllRelationNames_3() throws Exception {

		List<String> result = fixture.getAllRelationNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationsinDB(RelationsDAOImpl.java:43)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationNames(RelationsDAOImpl.java:55)
		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllRelationNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testGetAllRelationNames_4() throws Exception {

		List<String> result = fixture.getAllRelationNames();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.readAll(GenericDAOImpl.java:84)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationsinDB(RelationsDAOImpl.java:43)
		// at
		// dao.impl.RelationsDAOImpl.getAllRelationNames(RelationsDAOImpl.java:55)
		assertNotNull(result);
	}

	/**
	 * Run the List<Relationsdetail> getAllRelationsinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testGetAllRelationsinDB_1() throws Exception {
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Relationsdetail relationsdetail = new Relationsdetail();
		relationsdetail.setColumnsdetail(columnsdetail);
		fixture.save(relationsdetail);
		List<Relationsdetail> result = fixture.getAllRelationsinDB();

		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the Relationsdetail getRelationByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testGetRelationByid_1() throws Exception {
		Integer id = new Integer(1);
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Relationsdetail relationsdetail = new Relationsdetail();
		relationsdetail.setColumnsdetail(columnsdetail);
		fixture.save(relationsdetail);
		Relationsdetail result = fixture.getRelationByid(id);
		assertNotNull(result);
	}

	/**
	 * Run the void saveRelation(Relationsdetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testSaveRelation_1() throws Exception {
		Relationsdetail relationsdetail = new Relationsdetail();
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		relationsdetail.setColumnsdetail(columnsdetail);
		fixture.saveRelation(relationsdetail);
		assertNotNull(columnsdetail);
	}

	/**
	 * Run the Relationsdetail update(Relationsdetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		Relationsdetail relationsdetail = new Relationsdetail();
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		relationsdetail.setColumnsdetail(columnsdetail);
		fixture.saveRelation(relationsdetail);
		relationsdetail.setDescription("Name_Uodates");

		Relationsdetail result = fixture.update(relationsdetail);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at dao.impl.GenericDAOImpl.handleException(GenericDAOImpl.java:280)
		// at dao.impl.GenericDAOImpl.update(GenericDAOImpl.java:118)
		// at dao.impl.RelationsDAOImpl.update(RelationsDAOImpl.java:64)
		assertNotNull(result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 5:18 PM
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
	 * @generatedBy CodePro at 10/11/16 5:18 PM
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
	 * @generatedBy CodePro at 10/11/16 5:18 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(RelationsDAOImplTest.class);
	}
}