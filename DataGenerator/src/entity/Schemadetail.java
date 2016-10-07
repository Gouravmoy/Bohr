package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the schemadetails database table.
 * 
 */
@Entity
@Table(name="schemadetails")
@NamedQuery(name="Schemadetail.findAll", query="SELECT s FROM Schemadetail s")
public class Schemadetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idschema;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTS;

	@Column(length=100)
	private String name;

	//bi-directional many-to-one association to Changelog
	@OneToMany(mappedBy="schemadetail", fetch=FetchType.LAZY)
	private List<Changelog> changelogs;

	//bi-directional many-to-one association to Datasamplemodel
	@OneToMany(mappedBy="schemadetail", fetch=FetchType.LAZY)
	private List<Datasamplemodel> datasamplemodels;

	//bi-directional many-to-one association to Databasedetail
	@ManyToOne
	@JoinColumn(name="db_id")
	private Databasedetail databasedetail;

	//bi-directional many-to-one association to Tabledetail
	@OneToMany(mappedBy="schemadetail", fetch=FetchType.LAZY)
	private List<Tabledetail> tabledetails;

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

	public List<Changelog> getChangelogs() {
		return this.changelogs;
	}

	public void setChangelogs(List<Changelog> changelogs) {
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

	public List<Datasamplemodel> getDatasamplemodels() {
		return this.datasamplemodels;
	}

	public void setDatasamplemodels(List<Datasamplemodel> datasamplemodels) {
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

	public List<Tabledetail> getTabledetails() {
		return this.tabledetails;
	}

	public void setTabledetails(List<Tabledetail> tabledetails) {
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

}