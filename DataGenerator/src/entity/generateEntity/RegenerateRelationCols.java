package entity.generateEntity;

import java.util.List;

import entity.Columnsdetail;
import entity.Tabledetail;

public class RegenerateRelationCols {

	List<GeneratedColumn> generatedCol;
	List<GeneratedTable> generatedTables;

	public void regenerate() {

		for (GeneratedColumn generatedColumn : generatedCol) {
			if (generatedColumn.getRelationsdetail() != null) {
				GeneratedColumn relatedCol = getRelatedGenerateCol(
						generatedColumn.getRelationsdetail().getRelatedColumndetail(),
						generatedColumn.getRelationsdetail().getTabledetail());
				if (relatedCol != null)
					generatedColumn.setFilePath(relatedCol.getFilePath());
			}
		}
	}

	private GeneratedColumn getRelatedGenerateCol(Columnsdetail columnsdetail, Tabledetail tabledetail) {
		for (GeneratedTable generatedTable : generatedTables) {
			if (generatedTable.getTableName().equals(tabledetail.getTableName())) {
				for (GeneratedColumn columnsdetail2 : generatedTable.getGeneratedColumn()) {
					if (columnsdetail2.getColName().equals(columnsdetail.getName()))
						return columnsdetail2;
				}
			}
		}
		return null;
	}

	/*
	 * private boolean isTableChildOfRelatedTable(Tabledetail baseTable,
	 * Tabledetail relatedTable) { int tablePos = 0; int relatedTablePos = 0;
	 * for (int i = 0; i < Master.INSTANCE.getSortedTableInLoadOrder().size();
	 * i++) { Tabledetail sortedTable =
	 * Master.INSTANCE.getSortedTableInLoadOrder().get(i); if
	 * (sortedTable.getTableName().equals(baseTable.getTableName())) tablePos =
	 * i; if (sortedTable.getTableName().equals(relatedTable.getTableName()))
	 * relatedTablePos = i; } if (tablePos > relatedTablePos) return true; else
	 * return false; }
	 */

	public List<GeneratedColumn> getGeneratedCol() {
		return generatedCol;
	}

	public void setGeneratedCol(List<GeneratedColumn> generatedCol) {
		this.generatedCol = generatedCol;
	}

	public List<GeneratedTable> getGeneratedTables() {
		return generatedTables;
	}

	public void setGeneratedTables(List<GeneratedTable> generatedTables) {
		this.generatedTables = generatedTables;
	}

}
