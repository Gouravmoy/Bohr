package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the schemadetails database table.
 * 
 */
@Entity
@Table(name = "schemadetails")
@NamedQuery(name = "Schemadetail.findAll", query = "SELECT s FROM Schemadetail s")
public class Schemadetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idschema;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTS;

	@Column(length = 100)
	private String name;

	// bi-directional many-to-one association to Changelog
	@OneToMany(mappedBy = "schemadetail", fetch = FetchType.EAGER)
	private Set<Changelog> changelogs;

	// bi-directional many-to-one association to Datasamplemodel
	@OneToMany(mappedBy = "schemadetail", fetch = FetchType.EAGER)
	private Set<Datasamplemodel> datasamplemodels;

	// bi-directional many-to-one association to Databasedetail
	@ManyToOne
	@JoinColumn(name = "db_id", nullable = false)
	private Databasedetail databasedetail;

	// bi-directional many-to-one association to Tabledetail
	@OneToMany(mappedBy = "schemadetail", fetch = FetchType.EAGER)
	private Set<Tabledetail> tabledetails;

	public Schemadetail() {
	}

	public int getIdschema() {
		return this.idschema;
	}

	public void setIdschema(int idschema) {
		this.idschema = idschema;
	}

	public Date getLastUpdateTS() {
		return this.lastUpdateTS;
	}

	public void setLastUpdateTS(Date lastUpdateTS) {
		this.lastUpdateTS = lastUpdateTS;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Changelog> getChangelogs() {
		return changelogs;
	}

	public void setChangelogs(Set<Changelog> changelogs) {
		this.changelogs = changelogs;
	}

	public Changelog addChangelog(Changelog changelog) {
		getChangelogs().add(changelog);
		changelog.setSchemadetail(this);

		return changelog;
	}

	public Changelog removeChangelog(Changelog changelog) {
		getChangelogs().remove(changelog);
		changelog.setSchemadetail(null);

		return changelog;
	}

	public Set<Datasamplemodel> getDatasamplemodels() {
		return datasamplemodels;
	}

	public void setDatasamplemodels(Set<Datasamplemodel> datasamplemodels) {
		this.datasamplemodels = datasamplemodels;
	}

	public Datasamplemodel addDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().add(datasamplemodel);
		datasamplemodel.setSchemadetail(this);

		return datasamplemodel;
	}

	public Datasamplemodel removeDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().remove(datasamplemodel);
		datasamplemodel.setSchemadetail(null);

		return datasamplemodel;
	}

	public Databasedetail getDatabasedetail() {
		return this.databasedetail;
	}

	public void setDatabasedetail(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
	}

	public Set<Tabledetail> getTabledetails() {
		return tabledetails;
	}

	public void setTabledetails(Set<Tabledetail> tabledetails) {
		this.tabledetails = tabledetails;
	}

	public Tabledetail addTabledetail(Tabledetail tabledetail) {
		getTabledetails().add(tabledetail);
		tabledetail.setSchemadetail(this);

		return tabledetail;
	}

	public Tabledetail removeTabledetail(Tabledetail tabledetail) {
		getTabledetails().remove(tabledetail);
		tabledetail.setSchemadetail(null);

		return tabledetail;
	}

	@Override
	public String toString() {
		return name;
	}

}