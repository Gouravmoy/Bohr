package views.util;

import java.util.ArrayList;
import java.util.List;

import entity.Columnsdetail;

public class MainUtil {

	List<String> returnList;

	public List<String> getColumnsdetail(Columnsdetail col) {
		returnList = new ArrayList<>();
		returnList.add("Name:" + col.getName());
		returnList.add("Type:" + col.getType());
		returnList.add("Length:" + col.getLength());
		returnList.add("Decimal Length:" + col.getDecimalLength());
		returnList.add("Is Nullable:" + (col.getIsnullable() == 1 ? "True" : "False"));
		returnList.add("Key Type:" + col.getKeytype());
		if (col.getEnumvalues() != null)
			returnList.add("Column Enum Values:" + col.getEnumvalues());
		return returnList;
	}
}
