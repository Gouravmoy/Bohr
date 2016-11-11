package views.util;

import java.util.ArrayList;
import java.util.List;

import entity.Columnsdetail;
import entity.Tabledetail;

public class PropertiesUtil {
	List<String> returnList;

	public List<String> getTableProps(Tabledetail tabledetail) {
		returnList = new ArrayList<>();
		for (Columnsdetail columnsdetail : tabledetail.getColumnsdetails()) {
			returnList.add(
					columnsdetail.getName() + "," + columnsdetail.getType() + "(" + columnsdetail.getLength() + ")");
		}
		return returnList;
	}
}
