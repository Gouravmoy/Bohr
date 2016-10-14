package controller;

import java.util.ArrayList;
import java.util.List;

import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Constraintsdetail;
import entity.Schemadetail;
import entity.Tabledetail;
import exceptions.ReadEntityException;
import jobs.tasks.SortTableTask;

public class DeleteMain {

	public static void main(String[] args) {

		SchemaDao dao = new SchemaDaoImpl();
		try {
			Schemadetail schemadetail = dao.getSchemaByid(7);
			List<Tabledetail> list = new ArrayList<>(schemadetail.getTabledetails());
			for (Tabledetail tabledetail : list) {
				for (Constraintsdetail constraintsdetail : tabledetail.getConstraintsdetails()) {
					System.out.println(constraintsdetail.getColumnsdetail1().getTabledetail().getTableName()
							+ " is dependetn on " + tabledetail.getTableName());
				}
			}

			SortTableTask sortTableTask = new SortTableTask(list);
			sortTableTask.execute();
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
