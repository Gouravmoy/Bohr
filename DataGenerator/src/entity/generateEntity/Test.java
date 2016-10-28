package entity.generateEntity;

import java.util.ArrayList;
import java.util.List;

import enums.ColumnType;

public class Test {

	public static void main(String args[]) {
		GeneratedTable generatedTable = new GeneratedTable();
		List<GeneratedColumn> generatedColumns = new ArrayList<GeneratedColumn>();

		for(int i=0;i<10000;i++){
			GenerateColumnRandom generateColumnRandom = new GenerateColumnRandom();

			generateColumnRandom.setColName("ABC");
			generateColumnRandom.setColumnType(ColumnType.INTEGER);
			generateColumnRandom.setColLength(3);
			generateColumnRandom.setFilePath("C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\ABC.txt");
			generateColumnRandom.generateColumn();
			generateColumnRandom.setNullable(false);
			generateColumnRandom.setGenerateAllUnique(true);

			generatedColumns.add(generateColumnRandom);
		}
		generatedTable.setGeneratedColumn(generatedColumns);
	}

}
