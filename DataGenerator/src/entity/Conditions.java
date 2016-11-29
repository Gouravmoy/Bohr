package entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import enums.ColumnType;

@Entity
@Table(name = "conditions")
@NamedQuery(name = "Conditions.findAll", query = "SELECT c FROM Conditions c")
public class Conditions implements Serializable {

	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "columnsdetail"))
	@Id
	@GeneratedValue(generator = "generator")
	private int idConditions;

	@Column(length = 50)
	private String conditionDesc;

	@Column(length = 50)
	private String startWith;

	@Column(columnDefinition = "boolean default false")
	private boolean isGenerateRandom;

	@Column
	private int sequenceNo;

	@Column(length = 50)
	private String endsWith;

	@Column(length = 50)
	private int sizeLimit;

	@Column
	private double lowerLimit;

	@Column
	private double upperLimit;

	@Column
	private Date dateLowerLimit;

	@Column
	private Date dateUpperLimit;

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Columnsdetail columnsdetail;

	@ManyToOne
	@JoinColumn(name = "idproject")
	private Projectdetails projectdetail;
	
	public Conditions() {
		super();
		this.sequenceNo=0;
		this.sizeLimit=0;
	}

	public int getIdConditions() {
		return idConditions;
	}

	public void setIdConditions(int idConditions) {
		this.idConditions = idConditions;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public String getStartWith() {
		return startWith;
	}

	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}

	public String getEndsWith() {
		return endsWith;
	}

	public void setEndsWith(String endsWith) {
		this.endsWith = endsWith;
	}

	public int getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Date getDateLowerLimit() {
		return dateLowerLimit;
	}

	public void setDateLowerLimit(Date dateLowerLimit) {
		this.dateLowerLimit = dateLowerLimit;
	}

	public Date getDateUpperLimit() {
		return dateUpperLimit;
	}

	public void setDateUpperLimit(Date dateUpperLimit) {
		this.dateUpperLimit = dateUpperLimit;
	}

	public Columnsdetail getColumnsdetail() {
		return columnsdetail;
	}

	public void setColumnsdetail(Columnsdetail columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

	public Projectdetails getProjectdetail() {
		return projectdetail;
	}

	public void setProjectdetail(Projectdetails projectdetail) {
		this.projectdetail = projectdetail;
	}

	public boolean isGenerateRandom() {
		return isGenerateRandom;
	}

	public void setGenerateRandom(boolean isGenerateRandom) {
		this.isGenerateRandom = isGenerateRandom;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	@Override
	public String toString() {
		if (this.columnsdetail.getType() == ColumnType.VARCHAR) {
			return "startWith=" + startWith + "," + "endsWith=" + endsWith;
		} else if (this.columnsdetail.getType() == ColumnType.INTEGER) {
			return "sizeLimit=" + sizeLimit + ", lowerLimit=" + lowerLimit + ", upperLimit=" + upperLimit;
		} else {
			return "dateLowerLimit=" + dateLowerLimit + ", dateUpperLimit=" + dateUpperLimit;
		}
	}
}
