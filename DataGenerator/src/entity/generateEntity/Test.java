package entity.generateEntity;

import java.util.ArrayList;
import java.util.List;

import enums.ColumnType;

public class Test {

	public static void main(String args[]) {
		GeneratedTable generatedTable = new GeneratedTable();
		List<GeneratedColumn> generatedColumns = new ArrayList<GeneratedColumn>();

		GeneratedColumnEnum generatedColumnEnum = new GeneratedColumnEnum();

		generatedColumnEnum.setColName("ABC");
		generatedColumnEnum.setColumnType(ColumnType.ENUM);
		generatedColumnEnum.setColLength(5);
		generatedColumnEnum.setFilePath("C:\\Users\\m1026335\\Desktop\\Test\\Rapid TDG\\ABC.txt");
		generatedColumnEnum.setEnumValues("a,b,c,d");
		generatedColumnEnum.generateColumn();

		generatedColumns.add(generatedColumnEnum);
		generatedTable.setGeneratedColumn(generatedColumns);
	}

}
