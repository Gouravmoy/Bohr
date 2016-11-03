package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import enums.ColumnType;
import enums.KeyType;

/**
 * The persistent class for the columnsdetails database table.
 * 
 */
@Entity
@Table(name = "columnsdetails")
@NamedQuery(name = "Columnsdetail.findAll", query = "SELECT c FROM Columnsdetail c")
public class Columnsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private int idcolumnsdetails;

	@Column(length = 45)
	private String columnsdetailscol;

	@Column(length = 200)
	private String enumvalues;

	private byte isnullable;

	@Enumerated(EnumType.STRING)
	private KeyType keytype;

	private long length;

	private int decimalLength;

	@Column(length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	private ColumnType type;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "columnsdetail", cascade = CascadeType.ALL)
	private Datasamplemodel datasamplemodel;

	@ManyToOne
	@JoinColumn(name = "patternId", nullable = true)
	private Patterndetail patterndetail;

	@ManyToOne
	@JoinColumn(name = "tableId", nullable = false)
	private Tabledetail tabledetail;

	@OneToMany(mappedBy = "columnsdetail1", fetch = FetchType.EAGER)
	private Set<Constraintsdetail> constraintsdetails1;

	@ManyToOne
	@JoinColumn(name = "idpredefinedDS")
	private PreDefinedModels predefinedModel;

	// bi-directional many-to-one association to Relationsdetail
	@OneToMany(mappedBy = "columnsdetail", fetch = FetchType.EAGER)
	private Set<Relationsdetail> relationsdetails;

	@OneToMany(mappedBy = "relatedColumndetail", fetch = FetchType.EAGER)
	private Set<Relationsdetail> relatedColumndetail;

	public Columnsdetail() {
	}

	public Set<Relationsdetail> getRelatedColumndetail() {
		return relatedColumndetail;
	}

	public void setRelatedColumndetail(Set<Relationsdetail> relatedColumndetail) {
		this.relatedColumndetail = relatedColumndetail;
	}

	public int getIdcolumnsdetails() {
		return this.idcolumnsdetails;
	}

	public void setIdcolumnsdetails(int idcolumnsdetails) {
		this.idcolumnsdetails = idcolumnsdetails;
	}

	public String getColumnsdetailscol() {
		return this.columnsdetailscol;
	}

	public void setColumnsdetailscol(String columnsdetailscol) {
		this.columnsdetailscol = columnsdetailscol;
	}

	public String getEnumvalues() {
		return this.enumvalues;
	}

	public void setEnumvalues(String enumvalues) {
		this.enumvalues = enumvalues;
	}

	public byte getIsnullable() {
		return this.isnullable;
	}

	public void setIsnullable(byte isnullable) {
		this.isnullable = isnullable;
	}

	public KeyType getKeytype() {
		return keytype;
	}

	public void setKeytype(KeyType keytype) {
		this.keytype = keytype;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ColumnType getType() {
		return type;
	}

	public void setType(ColumnType type) {
		this.type = type;
	}

	public Datasamplemodel getDatasamplemodel() {
		return this.datasamplemodel;
	}

	public void setDatasamplemodel(Datasamplemodel datasamplemodel) {
		this.datasamplemodel = datasamplemodel;
	}

	public Patterndetail getPatterndetail() {
		return patterndetail;
	}

	public void setPatterndetail(Patterndetail patterndetail) {
		this.patterndetail = patterndetail;
	}

	public Tabledetail getTabledetail() {
		return this.tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

	public Set<Constraintsdetail> getConstraintsdetails1() {
		return constraintsdetails1;
	}

	public void setConstraintsdetails1(Set<Constraintsdetail> constraintsdetails1) {
		this.constraintsdetails1 = constraintsdetails1;
	}

	public Constraintsdetail addConstraintsdetails1(Constraintsdetail constraintsdetails1) {
		if (getConstraintsdetails1() == null) {
			this.constraintsdetails1 = new HashSet<Constraintsdetail>();
		}
		getConstraintsdetails1().add(constraintsdetails1);
		constraintsdetails1.setColumnsdetail1(this);

		return constraintsdetails1;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public Constraintsdetail removeConstraintsdetails1(Constraintsdetail constraintsdetails1) {
		getConstraintsdetails1().remove(constraintsdetails1);
		constraintsdetails1.setColumnsdetail1(null);

		return constraintsdetails1;
	}

	public int getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(int decimalLength) {
		this.decimalLength = decimalLength;
	}

	public Set<Relationsdetail> getRelationsdetails() {
		return this.relationsdetails;
	}

	public void setRelationsdetails(Set<Relationsdetail> relationsdetails) {
		this.relationsdetails = relationsdetails;
	}

	public Relationsdetail addRelationsdetail(Relationsdetail relationsdetail) {
		getRelationsdetails().add(relationsdetail);
		relationsdetail.setColumnsdetail(this);

		return relationsdetail;
	}

	public Relationsdetail removeRelationsdetail(Relationsdetail relationsdetail) {
		getRelationsdetails().remove(relationsdetail);
		relationsdetail.setColumnsdetail(null);

		return relationsdetail;
	}

	@Override
	public String toString() {
		return name;
	}

	public PreDefinedModels getPredefinedModel() {
		return predefinedModel;
	}

	public void setPredefinedModel(PreDefinedModels predefinedModel) {
		this.predefinedModel = predefinedModel;
	}

}
