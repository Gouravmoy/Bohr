package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "columnsdetail"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "iddatasamplemodel", unique = true, nullable = false)
	private int iddatasamplemodel;

	@Column(length = 30000)
	private String datasamplemodelcol;

	@Enumerated(EnumType.STRING)
	private SampleType sampletype;

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Columnsdetail columnsdetail;
	
	@ManyToOne
	@JoinColumn(name = "idproject")
	private Projectdetails projectdetail;

	public Datasamplemodel() {
	}

	public int getIddatasamplemodel() {
		return iddatasamplemodel;
	}

	public void setIddatasamplemodel(int iddatasamplemodel) {
		this.iddatasamplemodel = iddatasamplemodel;
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
	

}