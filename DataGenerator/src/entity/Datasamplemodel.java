package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import enums.SampleType;

/**
 * The persistent class for the datasamplemodel database table.
 * 
 */
@Entity
@Table(name = "datasamplemodel")
@NamedQuery(name = "Datasamplemodel.findAll", query = "SELECT d FROM Datasamplemodel d")
public class Datasamplemodel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	DataSampleModelKey conditionKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "columnId", insertable = false, updatable = false)
	Columnsdetail columnsdetail;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId", insertable = false, updatable = false)
	Projectdetails projectdetail;

	@Column(length = 30000)
	private String datasamplemodelcol;

	@Enumerated(EnumType.STRING)
	private SampleType sampletype;

	@Column
	boolean repeteableIndex;

	public Datasamplemodel() {
	}

	public String getDatasamplemodelcol() {
		return datasamplemodelcol;
	}

	public void setDatasamplemodelcol(String datasamplemodelcol) {
		this.datasamplemodelcol = datasamplemodelcol;
	}

	public SampleType getSampletype() {
		return sampletype;
	}

	public void setSampletype(SampleType sampletype) {
		this.sampletype = sampletype;
	}

	public Columnsdetail getColumnsdetail() {
		return columnsdetail;
	}

	public void setColumnsdetail(Columnsdetail columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

	@Override
	public String toString() {
		return datasamplemodelcol;
	}

	public Projectdetails getProjectdetail() {
		return projectdetail;
	}

	public void setProjectdetail(Projectdetails projectdetail) {
		this.projectdetail = projectdetail;
	}

	public boolean isRepeteableIndex() {
		return repeteableIndex;
	}

	public void setRepeteableIndex(boolean repeteableIndex) {
		this.repeteableIndex = repeteableIndex;
	}

	public DataSampleModelKey getConditionKey() {
		return conditionKey;
	}

	public void setConditionKey(DataSampleModelKey conditionKey) {
		this.conditionKey = conditionKey;
	}

}