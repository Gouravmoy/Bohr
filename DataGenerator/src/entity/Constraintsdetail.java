package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the constraintsdetails database table.
 * 
 */
@Entity
@Table(name="constraintsdetails")
@NamedQuery(name="Constraintsdetail.findAll", query="SELECT c FROM Constraintsdetail c")
public class Constraintsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idconstraintsdetails;

	@Column(length=200)
	private String constraintname;

	private byte isunique;

	//bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name="columnId")
	private Columnsdetail columnsdetail1;

	//bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name="referencetableid")
	private Tabledetail tabledetail;

	//bi-directional many-to-one association to Columnsdetail
	@ManyToOne
	@JoinColumn(name="referencecolumnid")
	private Columnsdetail columnsdetail2;

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

	public Tabledetail getTabledetail() {
		return this.tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

	public Columnsdetail getColumnsdetail2() {
		return this.columnsdetail2;
	}

	public void setColumnsdetail2(Columnsdetail columnsdetail2) {
		this.columnsdetail2 = columnsdetail2;
	}

}