package controller;

import java.util.ArrayList;
import java.util.List;

import common.Master;
import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Schemadetail;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.RegenerateUKForFK;
import entity.generateEntity.GeneratedTable;
import enums.Environment;
import enums.KeyType;
import exceptions.ReadEntityException;
import jobs.tasks.GenerateColumnDataTask;
import jobs.tasks.SortTableTask;

public class DeleteMain {

	public static void main(String[] args) {
		SchemaDao dao = new SchemaDaoImpl();
		try {
			Master.INSTANCE.setEnvironment(Environment.STAGING);
			Master.INSTANCE.setClearAll(false);
			Schemadetail schemadetail = dao.getSchemaByid(1);
			List<Tabledetail> tableList = new ArrayList<>(schemadetail.getTabledetails());
			List<GeneratedColumn> ukFkColumns = new ArrayList<>();
			SortTableTask sortTableTask = new SortTableTask(tableList);
			sortTableTask.execute();
			GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
			dataTask_1.execute();
			for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
				if (generatedTable.getTableName().equals("rental") || generatedTable.getTableName().equals("film")
						|| generatedTable.getTableName().equals("store")
						|| generatedTable.getTableName().equals("inventory")
						|| generatedTable.getTableName().equals("customer")) {
					System.out.println("Generating data for table " + generatedTable.getTableName());
					for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
						column.generateColumn();
						if (column.getKeyType() == KeyType.UK_FK)
							ukFkColumns.add(column);
					}
					regenerateUKFKColumns(ukFkColumns);
				}
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

	}

	public static void regenerateUKFKColumns(List<GeneratedColumn> ukFkColumns) {
		RegenerateUKForFK regenerateUKForFK = new RegenerateUKForFK();
		regenerateUKForFK.setUkFkColumns(ukFkColumns);
		regenerateUKForFK.regenerate();
	}

}
