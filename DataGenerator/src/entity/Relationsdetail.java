package entity;

import java.io.Serializable;
import javax.persistence.*;

import enums.RelationType;

/**
 * The persistent class for the relationsdetails database table.
 * 
 */
@Entity
@Table(name = "relationsdetails")
@NamedQuery(name = "Relationsdetail.findAll", query = "SELECT r FROM Relationsdetail r")
public class Relationsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idrelations;

	@Column(length = 200)
	private String description;

	@Enumerated(EnumType.STRING)
	private RelationType type;

	// bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name = "columnId",nullable=false)
	private Columnsdetail columnsdetail;

	// bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name = "tableid")
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

	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
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