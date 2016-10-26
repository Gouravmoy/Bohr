package controller;

import dao.DatabaseDao;
import dao.TableDao;
import dao.impl.DatabaseDAOImpl;
import dao.impl.TableDaoImpl;
import entity.Databasedetail;
import entity.Tabledetail;
import exceptions.ReadEntityException;
import jobs.tasks.CreateColumnTask;

public class MainTest_1 {

	public static void main(String[] args) {
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		TableDao tableDao = new TableDaoImpl();
		try {
			Databasedetail databasedetail = databaseDao.getDatabaseByid(7);
			Tabledetail tabledetail = tableDao.getTabledetailByid(7);
			CreateColumnTask columnTask = new CreateColumnTask(databasedetail, tabledetail);
			columnTask.execute();
			System.out.println(columnTask.getColumnsdetails());
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
