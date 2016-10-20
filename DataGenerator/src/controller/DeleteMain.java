package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DatabaseDao;
import dao.impl.DatabaseDAOImpl;
import entity.Constraintsdetail;
import entity.Databasedetail;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.ReadEntityException;
import jobs.tasks.SortTableTask;

public class DeleteMain {

	public static void main(String[] args) {
		DatabaseDao dao = new DatabaseDAOImpl();
		try {
			Databasedetail databasedetail = dao.getDatabaseByid(1);
			for (Schemadetail schemadetail : databasedetail.getSchemadetails()) {
				List<Tabledetail> list = new ArrayList<>(schemadetail.getTabledetails());
				for (Tabledetail tabledetail : list) {
					System.out.println(tabledetail.getTableName() + " --> Parent Table");
					for (Constraintsdetail constraintsdetail : tabledetail.getConstraintsdetails()) {
						System.out.println(constraintsdetail.getColumnsdetail1().getTabledetail().getTableName());
					}
				}
				SortTableTask sortTableTask = new SortTableTask(list);
				sortTableTask.execute();
			}
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
