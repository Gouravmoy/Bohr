package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the constraintsdetails database table.
 * 
 */
@Entity
@Table(name = "constraintsdetails")
@NamedQuery(name = "Constraintsdetail.findAll", query = "SELECT c FROM Constraintsdetail c")
public class Constraintsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idconstraintsdetails;

	@Column(length = 200)
	private String constraintname;

	private byte isunique;

	// bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name = "columnId", nullable = false)
	private Columnsdetail columnsdetail1;

	@Column
	private String referenceTable;

	@Column
	private String referenceColumnName;

	public Constraintsdetail() {
	}

	public int getIdconstraintsdetails() {
		return this.idconstraintsdetails;
	}

	public void setIdconstraintsdetails(int idconstraintsdetails) {
		this.idconstraintsdetails = idconstraintsdetails;
	}

	public String getConstraintname() {
		return this.constraintname;
	}

	public void setConstraintname(String constraintname) {
		this.constraintname = constraintname;
	}

	public byte getIsunique() {
		return this.isunique;
	}

	public void setIsunique(byte isunique) {
		this.isunique = isunique;
	}

	public Columnsdetail getColumnsdetail1() {
		return this.columnsdetail1;
	}

	public void setColumnsdetail1(Columnsdetail columnsdetail1) {
		this.columnsdetail1 = columnsdetail1;
	}

	public String getReferenceTable() {
		return referenceTable;
	}

	public void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}

	public String getReferenceColumnName() {
		return referenceColumnName;
	}

	public void setReferenceColumnName(String referenceColumnName) {
		this.referenceColumnName = referenceColumnName;
	}

	@Override
	public String toString() {
		return constraintname;
	}

}