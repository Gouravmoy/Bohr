package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Master;
import dao.impl.ColumnsDAOImpl;
import entity.Columnsdetail;
import entity.Tabledetail;
import enums.Environment;

public class ColumnsDAOImplTest {
	ColumnsDAOImpl fixture = new ColumnsDAOImpl();

	@Test
	public void testGetAllColumnsNames_2() throws Exception {
		ColumnsDAOImpl fixture = new ColumnsDAOImpl();
		Tabledetail tabledetail = CreateColumnPreClass.addTableToDataBase();
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setTabledetail(tabledetail);
		fixture.save(columnsdetail);
		List<String> result = fixture.getAllColumnsNames();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the List<String> getAllColumnsNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testGetAllColumnsNames_3() throws Exception {

		List<String> result = fixture.getAllColumnsNames();

		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllColumnsNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testGetAllColumnsNames_4() throws Exception {

		List<String> result = fixture.getAllColumnsNames();

		assertNotNull(result);
	}

	/**
	 * Run the List<String> getAllColumnsNames() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testGetAllColumnsNames_5() throws Exception {
		ColumnsDAOImpl fixture = new ColumnsDAOImpl();

		List<String> result = fixture.getAllColumnsNames();

		assertNotNull(result);
	}

	/**
	 * Run the List<Columnsdetail> getAllColumnsinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testGetAllColumnsinDB_1() throws Exception {
		Tabledetail tabledetail = CreateColumnPreClass.addTableToDataBase();
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setTabledetail(tabledetail);
		fixture.save(columnsdetail);
		List<Columnsdetail> result = fixture.getAllColumnsinDB();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	/**
	 * Run the Columnsdetail getColumnsdetailByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testGetColumnsdetailByid_2() throws Exception {
		ColumnsDAOImpl fixture = new ColumnsDAOImpl();
		Integer id = new Integer(1);
		Tabledetail tabledetail = CreateColumnPreClass.addTableToDataBase();
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setTabledetail(tabledetail);
		fixture.save(columnsdetail);
		Columnsdetail result = fixture.getColumnsdetailByid(id);
		assertNotNull(result);
	}

	/**
	 * Run the Columnsdetail saveColumn(Columnsdetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testSaveColumn_2() throws Exception {
		Tabledetail tabledetail = CreateColumnPreClass.addTableToDataBase();
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setTabledetail(tabledetail);
		Columnsdetail result = fixture.saveColumn(columnsdetail);

		assertNotNull(result);
	}

	/**
	 * Run the Columnsdetail update(Columnsdetail) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	@Test
	public void testUpdate_1() throws Exception {
		Columnsdetail columnsdetail = new Columnsdetail();
		columnsdetail.setIdcolumnsdetails(1);
		columnsdetail.setName("Updated Name");
		Tabledetail tabledetail = CreateColumnPreClass.addTableToDataBase();
		columnsdetail.setTabledetail(tabledetail);
		fixture.save(columnsdetail);
		Columnsdetail result = fixture.update(columnsdetail);
		assertNotNull(result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/11/16 11:01 AM
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
	 * @generatedBy CodePro at 10/11/16 11:01 AM
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
	 * @generatedBy CodePro at 10/11/16 11:01 AM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ColumnsDAOImplTest.class);
	}
}