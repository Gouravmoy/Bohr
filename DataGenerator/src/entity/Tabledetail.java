package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tabledetails database table.
 * 
 */
@Entity
@Table(name = "tabledetails")
@NamedQuery(name = "Tabledetail.findAll", query = "SELECT t FROM Tabledetail t")
public class Tabledetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idtabledetails;

	@Column(length = 100)
	private String tableName;

	// bi-directional many-to-one association to Changelog
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.LAZY)
	private List<Changelog> changelogs;

	// bi-directional many-to-one association to Columnsdetail
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.LAZY)
	private List<Columnsdetail> columnsdetails;

	// bi-directional many-to-one association to Constraintsdetail
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.LAZY)
	private List<Constraintsdetail> constraintsdetails;

	// bi-directional many-to-one association to Datasamplemodel
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.LAZY)
	private List<Datasamplemodel> datasamplemodels;

	// bi-directional many-to-one association to Relationsdetail
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.LAZY)
	private List<Relationsdetail> relationsdetails;

	// bi-directional many-to-one association to Schemadetail
	@ManyToOne
	@JoinColumn(name="schema_id",nullable=false)
	private Schemadetail schemadetail;

	public Tabledetail() {
	}

	public int getIdtabledetails() {
		return this.idtabledetails;
	}

	public void setIdtabledetails(int idtabledetails) {
		this.idtabledetails = idtabledetails;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Changelog> getChangelogs() {
		return this.changelogs;
	}

	public void setChangelogs(List<Changelog> changelogs) {
		this.changelogs = changelogs;
	}

	public Changelog addChangelog(Changelog changelog) {
		getChangelogs().add(changelog);
		changelog.setTabledetail(this);

		return changelog;
	}

	public Changelog removeChangelog(Changelog changelog) {
		getChangelogs().remove(changelog);
		changelog.setTabledetail(null);

		return changelog;
	}

	public List<Columnsdetail> getColumnsdetails() {
		return this.columnsdetails;
	}

	public void setColumnsdetails(List<Columnsdetail> columnsdetails) {
		this.columnsdetails = columnsdetails;
	}

	public Columnsdetail addColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().add(columnsdetail);
		columnsdetail.setTabledetail(this);

		return columnsdetail;
	}

	public Columnsdetail removeColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().remove(columnsdetail);
		columnsdetail.setTabledetail(null);

		return columnsdetail;
	}

	public List<Constraintsdetail> getConstraintsdetails() {
		return this.constraintsdetails;
	}

	public void setConstraintsdetails(List<Constraintsdetail> constraintsdetails) {
		this.constraintsdetails = constraintsdetails;
	}

	public Constraintsdetail addConstraintsdetail(Constraintsdetail constraintsdetail) {
		getConstraintsdetails().add(constraintsdetail);
		constraintsdetail.setTabledetail(this);

		return constraintsdetail;
	}

	public Constraintsdetail removeConstraintsdetail(Constraintsdetail constraintsdetail) {
		getConstraintsdetails().remove(constraintsdetail);
		constraintsdetail.setTabledetail(null);

		return constraintsdetail;
	}

	public List<Datasamplemodel> getDatasamplemodels() {
		return this.datasamplemodels;
	}

	public void setDatasamplemodels(List<Datasamplemodel> datasamplemodels) {
		this.datasamplemodels = datasamplemodels;
	}

	public Datasamplemodel addDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().add(datasamplemodel);
		datasamplemodel.setTabledetail(this);

		return datasamplemodel;
	}

	public Datasamplemodel removeDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().remove(datasamplemodel);
		datasamplemodel.setTabledetail(null);

		return datasamplemodel;
	}

	public List<Relationsdetail> getRelationsdetails() {
		return this.relationsdetails;
	}

	public void setRelationsdetails(List<Relationsdetail> relationsdetails) {
		this.relationsdetails = relationsdetails;
	}

	public Relationsdetail addRelationsdetail(Relationsdetail relationsdetail) {
		getRelationsdetails().add(relationsdetail);
		relationsdetail.setTabledetail(this);

		return relationsdetail;
	}

	public Relationsdetail removeRelationsdetail(Relationsdetail relationsdetail) {
		getRelationsdetails().remove(relationsdetail);
		relationsdetail.setTabledetail(null);

		return relationsdetail;
	}

	public Schemadetail getSchemadetail() {
		return this.schemadetail;
	}

	public void setSchemadetail(Schemadetail schemadetail) {
		this.schemadetail = schemadetail;
	}

	@Override
	public String toString() {
		return "Tabledetail [idtabledetails=" + idtabledetails + ", tableName=" + tableName + ", changelogs="
				+ changelogs + ", columnsdetails=" + columnsdetails + ", constraintsdetails=" + constraintsdetails
				+ ", datasamplemodels=" + datasamplemodels + ", relationsdetails=" + relationsdetails
				+ ", schemadetail=" + schemadetail + "]";
	}

}