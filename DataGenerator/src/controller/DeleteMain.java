package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Master;
import dao.SchemaDao;
import dao.impl.SchemaDaoImpl;
import entity.Schemadetail;
import entity.Tabledetail;
import entity.generateEntity.GeneratedColumn;
import entity.generateEntity.GeneratedTable;
import entity.generateEntity.RegenerateUKForFK;
import enums.Environment;
import enums.KeyType;
import exceptions.ReadEntityException;
import jobs.tasks.SortTableTask;
import jobs.tasks.generate.GenerateColumnDataTask;
import jobs.tasks.generate.GenerateTableDataTask_1;
import jobs.tasks.generate.GenerateTableDataWithInsertQueryTask;

public class DeleteMain {

	public static void main(String[] args) {
		SchemaDao dao = new SchemaDaoImpl();
		try {
			Master.INSTANCE.setEnvironment(Environment.TEST);
			Master.INSTANCE.setClearAll(false);
			Schemadetail schemadetail = dao.getSchemaByid(1);
			List<Tabledetail> tableList = new ArrayList<>(schemadetail.getTabledetails());
			List<GeneratedColumn> ukFkColumns = new ArrayList<>();
			SortTableTask sortTableTask = new SortTableTask(tableList);
			sortTableTask.execute();
			GenerateColumnDataTask dataTask_1 = new GenerateColumnDataTask(sortTableTask.getTabledetailListSorted());
			dataTask_1.execute();
			for (GeneratedTable generatedTable : dataTask_1.getGeneratedTableData()) {
				ukFkColumns = new ArrayList<>();
				generatedTable.setRowCount(10);
				System.out.println("Generating data for table " + generatedTable.getTableName());
				for (GeneratedColumn column : generatedTable.getGeneratedColumn()) {
					column.setNumberOfRows(10);
					column.generateColumn();
					if (column.getKeyType() == KeyType.UK_FK)
						ukFkColumns.add(column);
				}
				if (!ukFkColumns.isEmpty())
					regenerateUKFKColumns(ukFkColumns);
				GenerateTableDataTask_1 dataTask_12 = new GenerateTableDataTask_1(generatedTable);
				dataTask_12.execute();
				GenerateTableDataWithInsertQueryTask dataWithInsertQueryTask = new GenerateTableDataWithInsertQueryTask(
						generatedTable, "C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\Export\\"
								+ new SimpleDateFormat("yyyyMMddhhmm").format(new Date()) + "\\");
				dataWithInsertQueryTask.execute();
			}
		} catch (ReadEntityException e) {
			e.printStackTrace();
		}

	}

	public static void regenerateUKFKColumns(List<GeneratedColumn> ukFkColumns) {
		RegenerateUKForFK regenerateUKForFK = new RegenerateUKForFK();
		regenerateUKForFK.setUkFkColumns(ukFkColumns);
		regenerateUKForFK.setNumberOfRows(10);
		regenerateUKForFK.regenerate();
	}

}