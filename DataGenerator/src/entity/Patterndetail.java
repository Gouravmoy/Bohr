package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the patterndetails database table.
 * 
 */
@Entity
@Table(name = "patterndetails")
@NamedQuery(name = "Patterndetail.findAll", query = "SELECT p FROM Patterndetail p")
public class Patterndetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idpatterndetails;

	@Column(length = 100)
	private String patternDescription;

	@Column(length = 45)
	private String patternName;

	@Column(length = 100)
	private String regexpString;

	// bi-directional many-to-one association to Columnsdetail
	@OneToMany(mappedBy = "patterndetail", fetch = FetchType.LAZY)
	private List<Columnsdetail> columnsdetails;

	public Patterndetail() {
	}

	public int getIdpatterndetails() {
		return this.idpatterndetails;
	}

	public void setIdpatterndetails(int idpatterndetails) {
		this.idpatterndetails = idpatterndetails;
	}

	public String getPatternDescription() {
		return this.patternDescription;
	}

	public void setPatternDescription(String patternDescription) {
		this.patternDescription = patternDescription;
	}

	public String getPatternName() {
		return this.patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public String getRegexpString() {
		return regexpString;
	}

	public void setRegexpString(String regexpString) {
		this.regexpString = regexpString;
	}

	public List<Columnsdetail> getColumnsdetails() {
		return this.columnsdetails;
	}

	public void setColumnsdetails(List<Columnsdetail> columnsdetails) {
		this.columnsdetails = columnsdetails;
	}

	public Columnsdetail addColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().add(columnsdetail);
		columnsdetail.setPatterndetail(this);

		return columnsdetail;
	}

	public Columnsdetail removeColumnsdetail(Columnsdetail columnsdetail) {
		getColumnsdetails().remove(columnsdetail);
		columnsdetail.setPatterndetail(null);

		return columnsdetail;
	}

}