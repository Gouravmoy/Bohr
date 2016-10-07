package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the datasamplemodel database table.
 * 
 */
@Entity
@Table(name="datasamplemodel")
@NamedQuery(name="Datasamplemodel.findAll", query="SELECT d FROM Datasamplemodel d")
public class Datasamplemodel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int iddatasamplemodel;

	@Column(length=30000)
	private String datasamplemodelcol;

	@Column(length=45)
	private String sampletype;

	//bi-directional many-to-one association to Columnsdetail
	@OneToMany(mappedBy="datasamplemodel", fetch=FetchType.LAZY)
	private List<Columnsdetail> columnsdetails;

	//bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name="coulmnid")
	private Columnsdetail columnsdetail;

	//bi-directional many-to-one association to Schemadetail
	@ManyToOne
	@JoinColumn(name="schemaid")
	private Schemadetail schemadetail;

	//bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name="tableid")
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

	public String getSampletype() {
		return this.sampletype;
	}

	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}

	public List<Columnsdetail> getColumnsdetails() {
		return this.columnsdetails;
	}

	public void setColumnsdetails(List<Columnsdetail> columnsdetails) {
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

}