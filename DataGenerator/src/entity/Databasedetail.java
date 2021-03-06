package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enums.DBType;

/**
 * The persistent class for the databasedetails database table.
 * 
 */
@Entity
@Table(name = "databasedetails")
@NamedQuery(name = "Databasedetail.findAll", query = "SELECT d FROM Databasedetail d")
public class Databasedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int iddatabase;

	@Column(length = 200, unique = true)
	private String connectionName;

	@Column(length = 200)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTS;

	@Column(length = 50)
	private String name;

	@Column(length = 45)
	private String password;

	@Column(length = 45)
	private String port;

	@Enumerated(EnumType.STRING)
	private DBType type;

	@Column(length = 200)
	private String url;

	@Column(length = 45)
	private String username;

	// bi-directional many-to-one association to Changelog
	@OneToMany(mappedBy = "databasedetail", fetch = FetchType.EAGER)
	private Set<Changelog> changelogs;

	// bi-directional many-to-one association to Schemadetail
	@OneToMany(mappedBy = "databasedetail", fetch = FetchType.EAGER)
	private Set<Schemadetail> schemadetails;

	public Databasedetail(String connectionName, String description, String name, String password, String port,
			DBType type, String url, String username) {
		super();
		this.connectionName = connectionName;
		this.description = description;
		this.lastUpdateTS = new Date();
		this.name = name;
		this.password = password;
		this.port = port;
		this.type = type;
		this.url = url;
		this.username = username;
	}

	public Databasedetail() {
	}

	public int getIddatabase() {
		return this.iddatabase;
	}

	public void setIddatabase(int iddatabase) {
		this.iddatabase = iddatabase;
	}

	public String getConnectionName() {
		return this.connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public DBType getType() {
		return type;
	}

	public void setType(DBType type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public Set<Changelog> getChangelogs() {
		return changelogs;
	}

	public void setChangelogs(Set<Changelog> changelogs) {
		this.changelogs = changelogs;
	}

	public Changelog addChangelog(Changelog changelog) {
		getChangelogs().add(changelog);
		changelog.setDatabasedetail(this);

		return changelog;
	}

	public Changelog removeChangelog(Changelog changelog) {
		getChangelogs().remove(changelog);
		changelog.setDatabasedetail(null);

		return changelog;
	}

	

	public Set<Schemadetail> getSchemadetails() {
		return schemadetails;
	}

	public void setSchemadetails(Set<Schemadetail> schemadetails) {
		this.schemadetails = schemadetails;
	}

	public Schemadetail addSchemadetail(Schemadetail schemadetail) {
		getSchemadetails().add(schemadetail);
		schemadetail.setDatabasedetail(this);

		return schemadetail;
	}

	public Schemadetail removeSchemadetail(Schemadetail schemadetail) {
		getSchemadetails().remove(schemadetail);
		schemadetail.setDatabasedetail(null);

		return schemadetail;
	}

	@Override
	public String toString() {
		return connectionName;
	}

}