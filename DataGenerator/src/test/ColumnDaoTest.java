package test;

import org.junit.Test;

import dao.ColumnsDao;
import dao.GenericDAO;
import dao.impl.ColumnsDAOImpl;
import dao.impl.GenericDAOImpl;
import entity.Columnsdetail;
import entity.Tabledetail;
import exceptions.PersistException;
import junit.framework.TestCase;

public class ColumnDaoTest extends TestCase {
	Columnsdetail columnsdetail;
	ColumnsDao columnsDao = new ColumnsDAOImpl();
	GenericDAO<Tabledetail, Long> dao = new GenericDAOImpl<Tabledetail, Long>() {
	};

	public void setUp() {
		try {
			super.setUp();
			columnsdetail = new Columnsdetail();
			Tabledetail tabledetail = new Tabledetail();
			tabledetail = dao.getFirstRecord(Tabledetail.class);
			columnsdetail.setTabledetail(tabledetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveColumns() {
		try {
			columnsDao.saveColumn(columnsdetail);
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
