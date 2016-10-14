package entity;

import java.util.List;

public class GeneratedTableData {
	Tabledetail table;
	int tableOrder;
	List<String> rows;
	List<KeysData> keysDatas;

	public Tabledetail getTable() {
		return table;
	}

	public void setTable(Tabledetail table) {
		this.table = table;
	}

	public int getTableOrder() {
		return tableOrder;
	}

	public void setTableOrder(int tableOrder) {
		this.tableOrder = tableOrder;
	}

	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}

	public List<KeysData> getKeysDatas() {
		return keysDatas;
	}

	public void setKeysDatas(List<KeysData> keysDatas) {
		this.keysDatas = keysDatas;
	}

	@Override
	public String toString() {
		return "GeneratedTableData [table=" + table + ", tableOrder=" + tableOrder + ", rows=" + rows + ", keysDatas="
				+ keysDatas + "]";
	}

}
