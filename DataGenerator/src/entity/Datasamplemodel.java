package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int iddatasamplemodel;

	@Column(length = 30000)
	private String datasamplemodelcol;

	@Enumerated(EnumType.STRING)
	private SampleType sampletype;

	// bi-directional many-to-one association to Columnsdetail
	@OneToMany(mappedBy = "datasamplemodel", fetch = FetchType.EAGER)
	private Set<Columnsdetail> columnsdetails;

	// bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name = "coulmnid",nullable=false)
	private Columnsdetail columnsdetail;

	// bi-directional many-to-one association to Schemadetail
	@ManyToOne
	@JoinColumn(name = "schemaid",nullable=false)
	private Schemadetail schemadetail;

	// bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name = "tableid",nullable=false)
	private Tabledetail tabledetail;

	public Datasamplemodel() {
	}

	public int getIddatasamplemodel() {
		return this.iddatasamplemodel;
	}

	public void setIddatasamplemodel(int iddatasamplemodel) {
		this.iddatasamplemodel = iddatasamplemodel;
	}

	public String getDatasamplemodelcol() {
		return this.datasamplemodelcol;
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

	public Set<Columnsdetail> getColumnsdetails() {
		return this.columnsdetails;
	}

	public void setColumnsdetails(Set<Columnsdetail> columnsdetails) {
		this.columnsdetails = columnsdetails;
	}

	public Columnsdetail addColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().add(columnsdetail);
		columnsdetail.setDatasamplemodel(this);

		return columnsdetail;
	}

	public Columnsdetail removeColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().remove(columnsdetail);
		columnsdetail.setDatasamplemodel(null);

		return columnsdetail;
	}

	public Columnsdetail getColumnsdetail() {
		return this.columnsdetail;
	}

	public void setColumnsdetail(Columnsdetail columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

	public Schemadetail getSchemadetail() {
		return this.schemadetail;
	}

	public void setSchemadetail(Schemadetail schemadetail) {
		this.schemadetail = schemadetail;
	}

	public Tabledetail getTabledetail() {
		return this.tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

	@Override
	public String toString() {
		return "Datasamplemodel [iddatasamplemodel=" + iddatasamplemodel + ", datasamplemodelcol=" + datasamplemodelcol
				+ ", sampletype=" + sampletype + ", columnsdetails=" + columnsdetails + ", columnsdetail="
				+ columnsdetail + ", schemadetail=" + schemadetail + ", tabledetail=" + tabledetail + "]";
	}

}