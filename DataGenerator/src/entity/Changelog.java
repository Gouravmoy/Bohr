package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the changelog database table.
 * 
 */
@Entity
@Table(name="changelog")
@NamedQuery(name="Changelog.findAll", query="SELECT c FROM Changelog c")
public class Changelog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idchangelog;

	@Column(length=500)
	private String revisioncomments;

	private int revisionid;

	@Column(name="sys_comments", length=200)
	private String sysComments;

	//bi-directional many-to-one association to Databasedetail
	@ManyToOne
	@JoinColumn(name="databaseid")
	private Databasedetail databasedetail;

	//bi-directional many-to-one association to Schemadetail
	@ManyToOne
	@JoinColumn(name="schemaid")
	private Schemadetail schemadetail;

	//bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name="tableid")
	private Tabledetail tabledetail;

	public Changelog() {
	}

	public int getIdchangelog() {
		return this.idchangelog;
	}

	public void setIdchangelog(int idchangelog) {
		this.idchangelog = idchangelog;
	}

	public String getRevisioncomments() {
		return this.revisioncomments;
	}

	public void setRevisioncomments(String revisioncomments) {
		this.revisioncomments = revisioncomments;
	}

	public int getRevisionid() {
		return this.revisionid;
	}

	public void setRevisionid(int revisionid) {
		this.revisionid = revisionid;
	}

	public String getSysComments() {
		return this.sysComments;
	}

	public void setSysComments(String sysComments) {
		this.sysComments = sysComments;
	}

	public Databasedetail getDatabasedetail() {
		return this.databasedetail;
	}

	public void setDatabasedetail(Databasedetail databasedetail) {
		this.databasedetail = databasedetail;
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