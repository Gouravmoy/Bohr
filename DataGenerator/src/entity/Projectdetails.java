package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projectdetails")
@NamedQuery(name = "Projectdetails.findAll", query = "SELECT p FROM Projectdetails p")
public class Projectdetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idproject;

	@Column(length = 200, unique = true)
	private String projectName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idschema")
	private Schemadetail schemadetail;

	// bi-directional many-to-one association to Relationsdetail
	@OneToMany(mappedBy = "projectdetail", fetch = FetchType.EAGER)
	private Set<Relationsdetail> relationsdetails;

	@OneToMany(mappedBy = "projectdetail", fetch = FetchType.EAGER)
	private Set<Datasamplemodel> datasamplemodels;

	// bi-directional many-to-one association to Patterndetail
	@OneToMany(mappedBy = "projectdetail", fetch = FetchType.EAGER)
	private Set<Patterndetail> patterndetails;

	@OneToMany(mappedBy = "projectdetail", fetch = FetchType.EAGER)
	private Set<Conditions> conditions;

	public int getIdproject() {
		return idproject;
	}

	public void setIdproject(int idproject) {
		this.idproject = idproject;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Schemadetail getSchemadetail() {
		return schemadetail;
	}

	public void setSchemadetail(Schemadetail schemadetail) {
		this.schemadetail = schemadetail;
	}

	@Override
	public String toString() {
		return projectName;
	}

	public Set<Datasamplemodel> getDatasamplemodels() {
		return datasamplemodels;
	}

	public void setDatasamplemodels(Set<Datasamplemodel> datasamplemodels) {
		this.datasamplemodels = datasamplemodels;
	}

	public Set<Relationsdetail> getRelationsdetails() {
		return relationsdetails;
	}

	public void setRelationsdetails(Set<Relationsdetail> relationsdetails) {
		this.relationsdetails = relationsdetails;
	}

	public Set<Patterndetail> getPatterndetails() {
		return patterndetails;
	}

	public void setPatterndetails(Set<Patterndetail> patterndetails) {
		this.patterndetails = patterndetails;
	}

	public Set<Conditions> getConditions() {
		return conditions;
	}

	public void setConditions(Set<Conditions> conditions) {
		this.conditions = conditions;
	}

}
