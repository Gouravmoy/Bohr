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
import jobs.tasks.GenerateColumnDataTask;
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
			GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
			dataTask_1.execute();
			for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
				System.out.println("Done + === " + generatedTable.getTableName());
				for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
					column.generateColumn();
				}
				GenerateTableDataTask_1 dataTask_12 = new GenerateTableDataTask_1(generatedTable);
				dataTask_12.execute();
			}
		} catch (ReadEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
