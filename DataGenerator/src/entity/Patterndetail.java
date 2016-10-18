package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@Column(nullable = false)
	private int idpatterndetails;

	@Column(length = 100)
	private String patternDescription;

	@Column(length = 45)
	private String patternName;

	@Column(length = 100)
	private String regexpString;

	@OneToMany(mappedBy = "patterndetail", fetch = FetchType.EAGER)
	private Set<Columnsdetail> columnsdetail;

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

	public Set<Columnsdetail> getColumnsdetail() {
		return columnsdetail;
	}

	public void setColumnsdetail(Set<Columnsdetail> columnsdetail) {
		this.columnsdetail = columnsdetail;
	}

	@Override
	public String toString() {
		List<Columnsdetail> columndetails = new ArrayList<>();
		columndetails.addAll(this.getColumnsdetail());
		for (Columnsdetail columnsdetail : columndetails) {
			return columnsdetail.getTabledetail().getTableName().toUpperCase() + "."
					+ columnsdetail.getName().toUpperCase();
		}
		return null;
	}

}