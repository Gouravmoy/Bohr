package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import enums.PatternType;

/**
 * The persistent class for the patterndetails database table.
 * 
 */
@Entity
@Table(name = "patterndetails")
@NamedQuery(name = "Patterndetail.findAll", query = "SELECT p FROM Patterndetail p")
public class Patterndetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "columnsdetail"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "idpatterndetails")
	private int idpatterndetails;

	@Column(length = 100)
	private String patternDescription;

	@Column(length = 45)
	private String patternName;

	@Column(length = 100)
	private String regexpString;

	/*
	 * @OneToMany(mappedBy = "patterndetail", fetch = FetchType.EAGER) private
	 * Set<Columnsdetail> columnsdetail;
	 */

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Columnsdetail columnsdetail;

	@Enumerated(EnumType.STRING)
	private PatternType patternType;

	@ManyToOne
	@JoinColumn(name = "idproject")
	private Projectdetails projectdetail;

	public Projectdetails getProjectdetail() {
		return projectdetail;
	}

	public void setProjectdetail(Projectdetails projectdetail) {
		this.projectdetail = projectdetail;
	}

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

	public PatternType getPatternType() {
		return patternType;
	}

	public void setPatternType(PatternType patternType) {
		this.patternType = patternType;
	}

	@Override
	public String toString() {
		return this.regexpString;
	}

}