package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

	@ManyToOne
	@JoinColumn(name = "columnId", nullable = false)
	private Columnsdetail columnsdetail;

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

	public Columnsdetail getColumnsdetail() {
		return columnsdetail;
	}

	public void setColumnsdetail(Columnsdetail columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

}