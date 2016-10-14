package test;

import org.junit.Test;

import dao.ColumnsDao;
import dao.ConstraintsDao;
import dao.GenericDAO;
import dao.impl.ColumnsDAOImpl;
import dao.impl.ConstraintsDAOImpl;
import dao.impl.GenericDAOImpl;
import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Tabledetail;
import exceptions.PersistException;
import junit.framework.TestCase;

public class ConstraintDaoTest extends TestCase {

	Constraintsdetail constraintsdetail;
	Columnsdetail columnsdetail;
	ColumnsDao columnsDao = new ColumnsDAOImpl();
	ConstraintsDao constraintsDao = new ConstraintsDAOImpl();
	GenericDAO<Tabledetail, Long> dao = new GenericDAOImpl<Tabledetail, Long>() {
	};

	public void setUp() {
		try {
			super.setUp();
			constraintsdetail = new Constraintsdetail();
			Tabledetail tabledetail = new Tabledetail();
			columnsdetail = columnsDao.getColumnsdetailByid(1);
			tabledetail = dao.getFirstRecord(Tabledetail.class);
			constraintsdetail.setColumnsdetail1(columnsdetail);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConstaintSave() {
		try {
			constraintsDao.saveConstraint(constraintsdetail);
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
