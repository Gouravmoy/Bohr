package dao.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.impl.ConstraintsDAOImpl;
import entity.Columnsdetail;
import entity.Constraintsdetail;

public class ConstraintsDAOImplTest {
	ConstraintsDAOImpl fixture = new ConstraintsDAOImpl();
	@Test
	public void testGetAllConstraintNames_1() throws Exception {
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Constraintsdetail constraintsdetail = new Constraintsdetail();
		constraintsdetail.setColumnsdetail1(columnsdetail);
		fixture.save(constraintsdetail);
		List<String> result = fixture.getAllConstraintNames();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAllConstraintNames_2() throws Exception {

		List<String> result = fixture.getAllConstraintNames();
		assertNotNull(result);
	}

	@Test
	public void testGetAllConstraintNames_3() throws Exception {

		List<String> result = fixture.getAllConstraintNames();

		assertNotNull(result);
	}

	@Test
	public void testGetAllConstraintNames_4() throws Exception {

		List<String> result = fixture.getAllConstraintNames();

		assertNotNull(result);
	}

	@Test
	public void testGetAllConstraintsinDB_1() throws Exception {
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Constraintsdetail constraintsdetail = new Constraintsdetail();
		constraintsdetail.setColumnsdetail1(columnsdetail);
		fixture.save(constraintsdetail);
		List<Constraintsdetail> result = fixture.getAllConstraintsinDB();
		if (result.isEmpty()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	@Test
	public void testGetConstraintsByid_1() throws Exception {
		Integer id = new Integer(1);
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		Constraintsdetail constraintsdetail = new Constraintsdetail();
		constraintsdetail.setColumnsdetail1(columnsdetail);
		fixture.save(constraintsdetail);
		Constraintsdetail result = fixture.getConstraintsByid(id);
		assertNotNull(result);
	}

	@Test
	public void testSaveConstraint_1() throws Exception {
		Constraintsdetail constraintsdetail = new Constraintsdetail();
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		constraintsdetail.setColumnsdetail1(columnsdetail);
		Constraintsdetail result = fixture.saveConstraint(constraintsdetail);

		assertNotNull(result);
	}

	@Test
	public void testUpdate_1() throws Exception {
		Constraintsdetail constraintsdetail = new Constraintsdetail();
		Columnsdetail columnsdetail = CreateColumnPreClass.addColumnToDatabase();
		constraintsdetail.setColumnsdetail1(columnsdetail);
		constraintsdetail.setIdconstraintsdetails(4);

		Constraintsdetail result = fixture.update(constraintsdetail);

		assertNotNull(result);
	}

	@Before
	public void setUp() throws Exception {
		//Master.INSTANCE.setEnvironment(Environment.TEST);
	}

	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ConstraintsDAOImplTest.class);
	}
}