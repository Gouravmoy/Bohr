package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "predefinedmodels")
@NamedQuery(name = "PreDefinedModels.findAll", query = "SELECT p FROM PreDefinedModels p")
public class PreDefinedModels implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idpredefinedDS;

	@Column(length = 200)
	private String expectedColumnName;

	@Column(length = 30000)
	private String sampelValues;

	@OneToMany(mappedBy = "predefinedModel", fetch = FetchType.EAGER)
	private Set<Columnsdetail> columnsdetails;

	public int getIdpredefinedDS() {
		return idpredefinedDS;
	}

	public void setIdpredefinedDS(int idpredefinedDS) {
		this.idpredefinedDS = idpredefinedDS;
	}

	public String getExpectedColumnName() {
		return expectedColumnName;
	}

	public void setExpectedColumnName(String expectedColumnName) {
		this.expectedColumnName = expectedColumnName;
	}

	public String getSampelValues() {
		return sampelValues;
	}

	public void setSampelValues(String sampelValues) {
		this.sampelValues = sampelValues;
	}

	@Override
	public String toString() {
		return expectedColumnName;
	}

	public Set<Columnsdetail> getColumnsdetails() {
		return columnsdetails;
	}

	public void setColumnsdetails(Set<Columnsdetail> columnsdetails) {
		this.columnsdetails = columnsdetails;
	}

}
