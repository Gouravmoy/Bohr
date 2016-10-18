package jobs.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import entity.Columnsdetail;
import entity.Constraintsdetail;
import entity.Tabledetail;

public class SortTableTask extends Task {

	List<Tabledetail> tabledetailListSorted = new LinkedList<>();
	List<Tabledetail> tabledetailList;
	List<String> validatedTabledetails = new ArrayList<>();

	public SortTableTask(List<Tabledetail> tabledetailList) {
		super();
		this.tabledetailList = tabledetailList;
	}

	@Override
	public void execute() throws BuildException {
		try {
			getPriorityOneTables();
			callPriorityTwoSorting();
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(validatedTabledetails);
			System.out.println("-----------------------------------------------------------------------------");
		} catch (BuildException buildException) {
			throw new BuildException(buildException.getMessage());
		}
	}

	void getPriorityOneTables() {
		boolean priorityOne;
		Iterator<Tabledetail> TabledetailsIterator = tabledetailList.iterator();
		Tabledetail Tabledetails;
		while (TabledetailsIterator.hasNext()) {
			Tabledetails = TabledetailsIterator.next();
			priorityOne = true;
			for (Columnsdetail Columndetails : Tabledetails.getColumnsdetails()) {
				if (Columndetails.getConstraintsdetails1() != null && priorityOne) {
					for (Constraintsdetail constraint : Columndetails.getConstraintsdetails1()) {
						if (constraint.getReferenceTable() != null) {
							priorityOne = false;
							break;
						}
					}
				}
			}
			if (priorityOne) {
				tabledetailListSorted.add(Tabledetails);
				validatedTabledetails.add(Tabledetails.getTableName());
				TabledetailsIterator.remove();
			}
		}
	}

	void getPriorityTwotables() {
		boolean priorityTwo;
		Iterator<Tabledetail> tableIterator = tabledetailList.iterator();
		Tabledetail table;
		while (tableIterator.hasNext()) {
			table = tableIterator.next();
			priorityTwo = true;
			for (Columnsdetail column : table.getColumnsdetails()) {
				if (column.getConstraintsdetails1() != null && priorityTwo) {
					for (Constraintsdetail constraint : column.getConstraintsdetails1()) {
						if (constraint.getReferenceTable() != null) {
							if (!validatedTabledetails.contains(constraint.getReferenceTable().getTableName())) {
								priorityTwo = false;
								break;
							}
						}
					}
				}
			}
			if (priorityTwo) {
				tabledetailListSorted.add(table);
				validatedTabledetails.add(table.getTableName());
				tableIterator.remove();
			}
		}

	}

	void callPriorityTwoSorting() {
		int previousSize;
		int currentSize;
		while (tabledetailList.size() != 0) {
			previousSize = tabledetailList.size();
			getPriorityTwotables();
			currentSize = tabledetailList.size();
			if (previousSize == currentSize) {
				if (tabledetailList.size() > 0) {
					sortCyclicTables();
				}
				break;
			}
		}

	}

	void sortCyclicTables() {
		int previousSize;
		int currentSize;
		boolean priorityTwo;
		Iterator<Tabledetail> tableIterator = tabledetailList.iterator();
		previousSize = tabledetailList.size();
		Tabledetail table;
		while (tableIterator.hasNext()) {
			table = tableIterator.next();
			priorityTwo = true;
			for (Columnsdetail column : table.getColumnsdetails()) {
				if (column.getConstraintsdetails1() != null && priorityTwo) {
					if (!(column.getIsnullable() != 0)) {
						for (Constraintsdetail constraint : column.getConstraintsdetails1()) {
							if (constraint.getReferenceTable() != null) {
								if (!validatedTabledetails.contains(constraint.getReferenceTable().getTableName())) {
									priorityTwo = false;
								}
							}
						}
					}
				}
			}
			if (priorityTwo) {
				tabledetailListSorted.add(table);
				validatedTabledetails.add(table.getTableName());
				tableIterator.remove();
			}
		}
		currentSize = tabledetailList.size();
		if (currentSize < previousSize) {
			callPriorityTwoSorting();
		} else {
			System.out.println("Invalid schema structure");
			throw new BuildException("Invalid schema structure");
		}

	}

	public List<Tabledetail> getTabledetailListSorted() {
		return tabledetailListSorted;
	}

	public void setTabledetailListSorted(List<Tabledetail> tabledetailListSorted) {
		this.tabledetailListSorted = tabledetailListSorted;
	}

}
