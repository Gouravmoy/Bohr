package controller;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Databasedetail;
import exceptions.PersistException;
import job.FirstJob;

public class DeleteMain {

	public static void main(String[] args) {
		FirstJob firstJob = new FirstJob("My Job");
		DatabaseDao databaseDao = new DatabaseDAOImpl();
		Databasedetail databasedetail = new Databasedetail();
		databasedetail.setDescription("jdbc:mysql://localhost:3306/sakila");
		databasedetail.setUsername("root");
		databasedetail.setPassword("root");
		try {
			databaseDao.saveDatabse(databasedetail);
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		firstJob.setDatabasedetail(databasedetail);
		System.out.println(firstJob.getResult());
		firstJob.schedule();
	}

}
