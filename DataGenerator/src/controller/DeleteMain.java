package controller;

import java.util.ArrayList;
import java.util.List;

import common.Master;
import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Schemadetail;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import enums.Environment;
import exceptions.ReadEntityException;
import jobs.tasks.GenerateColumnDataTask;
import jobs.tasks.GenerateTableDataTask_1;
import jobs.tasks.SortTableTask;

public class DeleteMain {

	public static void main(String[] args) {
		SchemaDao dao = new SchemaDaoImpl();
		try {
			Master.INSTANCE.setEnvironment(Environment.STAGING);
			Master.INSTANCE.setClearAll(false);
			Schemadetail schemadetail = dao.getSchemaByid(1);
			List<Tabledetail> tableList = new ArrayList<>(schemadetail.getTabledetails());
			SortTableTask sortTableTask = new SortTableTask(tableList);
			sortTableTask.execute();
			GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
			dataTask_1.execute();
			for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
				if (generatedTable.getTableName().equals("country")) {
					for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
						column.generateColumn();
					}
					GenerateTableDataTask_1 dataTask_12 = new GenerateTableDataTask_1(generatedTable);
					dataTask_12.execute();
				}
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

	}

}
