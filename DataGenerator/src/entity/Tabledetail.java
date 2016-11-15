package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	@Column(nullable = false)
	private int idtabledetails;

	@Column(length = 100)
	private String tableName;

	// bi-directional many-to-one association to Changelog
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.EAGER)
	private Set<Changelog> changelogs;

	// bi-directional many-to-one association to Columnsdetail
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Columnsdetail> columnsdetails;

	@OneToMany(mappedBy = "referenceTable", fetch = FetchType.EAGER)
	private Set<Constraintsdetail> constraintsdetails;

	// bi-directional many-to-one association to Relationsdetail
	@OneToMany(mappedBy = "tabledetail", fetch = FetchType.EAGER)
	private Set<Relationsdetail> relationsdetails;

	// bi-directional many-to-one association to Schemadetail
	@ManyToOne
	@JoinColumn(name = "schema_id", nullable = false)
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

	public Set<Changelog> getChangelogs() {
		return this.changelogs;
	}

	public void setChangelogs(Set<Changelog> changelogs) {
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

	public Set<Relationsdetail> getRelationsdetails() {
		return this.relationsdetails;
	}

	public void setRelationsdetails(Set<Relationsdetail> relationsdetails) {
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

	public Set<Constraintsdetail> getConstraintsdetails() {
		return constraintsdetails;
	}

	public void setConstraintsdetails(Set<Constraintsdetail> constraintsdetails) {
		this.constraintsdetails = constraintsdetails;
	}

	@Override
	public String toString() {
		return tableName;
	}

}