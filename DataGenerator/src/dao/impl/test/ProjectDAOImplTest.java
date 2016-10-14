package dao.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.impl.ProjectDAOImpl;
import entity.Projectdetails;

/**
 * The class <code>ProjectDAOImplTest</code> contains tests for the class <code>{@link ProjectDAOImpl}</code>.
 *
 * @generatedBy CodePro at 10/13/16 6:54 PM
 * @author M1026335
 * @version $Revision: 1.0 $
 */
public class ProjectDAOImplTest {
	/**
	 * Run the List<Projectdetails> getAllProjectdetailsinDB() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	ProjectDAOImpl fixture = new ProjectDAOImpl();
	@Test
	public void testGetAllProjectdetailsinDB_1()
		throws Exception {
		List<Projectdetails> result = fixture.getAllProjectdetailsinDB();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the Projectdetails getProjectdetailsByid(Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@Test(expected = exceptions.ReadEntityException.class)
	public void testGetProjectdetailsByid_1()
		throws Exception {
		Integer id = new Integer(1);

		Projectdetails result = fixture.getProjectdetailsByid(id);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Projectdetails saveProjectdetails(Projectdetails) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@Test(expected = exceptions.PersistException.class)
	public void testSaveProjectdetails_1()
		throws Exception {
		Projectdetails projectdetails = new Projectdetails();

		Projectdetails result = fixture.saveProjectdetails(projectdetails);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the void update(Projectdetails) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@Test
	public void testUpdate_1()
		throws Exception {
		Projectdetails projectdetails = new Projectdetails();
		projectdetails.setIdproject(1);

		fixture.update(projectdetails);

		// add additional test code here
	}

	/**
	 * Run the void update(Projectdetails) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@Test
	public void testUpdate_2()
		throws Exception {
		Projectdetails projectdetails = new Projectdetails();
		projectdetails.setIdproject(1);

		fixture.update(projectdetails);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		//Master.INSTANCE.setEnvironment(Environment.TEST);
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 10/13/16 6:54 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ProjectDAOImplTest.class);
	}
}