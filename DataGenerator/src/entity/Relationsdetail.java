package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the relationsdetails database table.
 * 
 */
@Entity
@Table(name="relationsdetails")
@NamedQuery(name="Relationsdetail.findAll", query="SELECT r FROM Relationsdetail r")
public class Relationsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idrelations;

	@Column(length=200)
	private String description;

	@Column(length=45)
	private String type;

	//bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name="columnId")
	private Columnsdetail columnsdetail;

	//bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name="tableid")
	private Tabledetail tabledetail;

	public Relationsdetail() {
	}

	public int getIdrelations() {
		return this.idrelations;
	}

	public void setIdrelations(int idrelations) {
		this.idrelations = idrelations;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Columnsdetail getColumnsdetail() {
		return this.columnsdetail;
	}

	public void setColumnsdetail(Columnsdetail columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

	public Tabledetail getTabledetail() {
		return this.tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

}