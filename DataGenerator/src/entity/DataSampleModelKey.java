package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DataSampleModelKey implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "columnId")
	int columnId;
	@Column(name = "projectId")
	int projectId;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public DataSampleModelKey() {
		super();
	}

	public DataSampleModelKey(int columnId, int projectId) {
		super();
		this.columnId = columnId;
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "ConditionKey [columnId=" + columnId + ", projectId=" + projectId + "]";
	}

}
