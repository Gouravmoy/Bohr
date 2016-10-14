package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projectdetails")
@NamedQuery(name = "Projectdetails.findAll", query = "SELECT p FROM Projectdetails p")
public class Projectdetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idproject;

	@Column(length = 200, unique = true)
	private String projectName;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "idschema")
	private Schemadetail schemadetail;

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
	
	
}
