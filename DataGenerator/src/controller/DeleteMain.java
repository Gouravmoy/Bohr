package controller;

import java.util.ArrayList;
import java.util.List;

import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Schemadetail;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import exceptions.ReadEntityException;
import jobs.tasks.GenerateTableDataTask_1;
import jobs.tasks.SortTableTask;

public class DeleteMain {

	public static void main(String[] args) {
		SchemaDao dao = new SchemaDaoImpl();
		try {
			Schemadetail schemadetail = dao.getSchemaByid(1);
			List<Tabledetail> tableList = new ArrayList<>(schemadetail.getTabledetails());
			SortTableTask sortTableTask = new SortTableTask(tableList);
			sortTableTask.execute();
			GenerateTableDataTask_1 dataTask_1 = new GenerateTableDataTask_1(sortTableTask.getTabledetailListSorted());
			dataTask_1.execute();
			for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
				System.out.println(generatedTable.getTableName() + " : " + generatedTable.getTablePath());
				if (generatedTable.getTableName().equalsIgnoreCase("store")
						|| generatedTable.getTableName().equalsIgnoreCase("store")) {
					System.out.println(generatedTable.getTableName() + " : " + generatedTable.getTablePath());
				}
				for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
					System.out.println(column);
					if (column.getFilePath() == null) {
						System.out.println(column);
					}
				}
			}
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
