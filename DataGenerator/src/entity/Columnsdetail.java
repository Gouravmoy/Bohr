package entity;

import java.io.Serializable;
import javax.persistence.*;

import enums.ColumnType;
import enums.KeyType;

import java.util.List;

/**
 * The persistent class for the columnsdetails database table.
 * 
 */
@Entity
@Table(name = "columnsdetails")
@NamedQuery(name = "Columnsdetail.findAll", query = "select p,c from Columnsdetail p join p.patterndetail c")
public class Columnsdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idcolumnsdetails;

	@Column(length = 45)
	private String columnsdetailscol;

	@Column(length = 200)
	private String enumvalues;

	private byte isnullable;

	@Enumerated(EnumType.STRING)
	private KeyType keytype;

	private int length;

	@Column(length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	private ColumnType type;

	// bi-directional many-to-one association to Datasamplemodel
	@ManyToOne
	@JoinColumn(name = "datasampleid")
	private Datasamplemodel datasamplemodel;

	// bi-directional many-to-one association to Patterndetail
	@OneToMany(mappedBy = "columnsdetail", fetch = FetchType.LAZY)
	private List<Patterndetail> patterndetail;

	// bi-directional many-to-one association to Tabledetail
	@ManyToOne
	@JoinColumn(name = "tableId", nullable = false)
	private Tabledetail tabledetail;

	// bi-directional many-to-one association to Constraintsdetail
	@OneToMany(mappedBy = "columnsdetail1", fetch = FetchType.LAZY)
	private List<Constraintsdetail> constraintsdetails1;

	// bi-directional many-to-one association to Constraintsdetail
	@OneToMany(mappedBy = "columnsdetail2", fetch = FetchType.LAZY)
	private List<Constraintsdetail> constraintsdetails2;

	// bi-directional many-to-one association to Datasamplemodel
	@OneToMany(mappedBy = "columnsdetail", fetch = FetchType.LAZY)
	private List<Datasamplemodel> datasamplemodels;

	// bi-directional many-to-one association to Relationsdetail
	@OneToMany(mappedBy = "columnsdetail", fetch = FetchType.LAZY)
	private List<Relationsdetail> relationsdetails;

	public Columnsdetail() {
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

	public int getLength() {
		return this.length;
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

	public List<Patterndetail> getPatterndetail() {
		return patterndetail;
	}

	public void setPatterndetail(List<Patterndetail> patterndetail) {
		this.patterndetail = patterndetail;
	}

	public Tabledetail getTabledetail() {
		return this.tabledetail;
	}

	public void setTabledetail(Tabledetail tabledetail) {
		this.tabledetail = tabledetail;
	}

	public List<Constraintsdetail> getConstraintsdetails1() {
		return this.constraintsdetails1;
	}

	public void setConstraintsdetails1(List<Constraintsdetail> constraintsdetails1) {
		this.constraintsdetails1 = constraintsdetails1;
	}

	public Constraintsdetail addConstraintsdetails1(Constraintsdetail constraintsdetails1) {
		getConstraintsdetails1().add(constraintsdetails1);
		constraintsdetails1.setColumnsdetail1(this);

		return constraintsdetails1;
	}

	public Constraintsdetail removeConstraintsdetails1(Constraintsdetail constraintsdetails1) {
		getConstraintsdetails1().remove(constraintsdetails1);
		constraintsdetails1.setColumnsdetail1(null);

		return constraintsdetails1;
	}

	public List<Constraintsdetail> getConstraintsdetails2() {
		return this.constraintsdetails2;
	}

	public void setConstraintsdetails2(List<Constraintsdetail> constraintsdetails2) {
		this.constraintsdetails2 = constraintsdetails2;
	}

	public Constraintsdetail addConstraintsdetails2(Constraintsdetail constraintsdetails2) {
		getConstraintsdetails2().add(constraintsdetails2);
		constraintsdetails2.setColumnsdetail2(this);

		return constraintsdetails2;
	}

	public Constraintsdetail removeConstraintsdetails2(Constraintsdetail constraintsdetails2) {
		getConstraintsdetails2().remove(constraintsdetails2);
		constraintsdetails2.setColumnsdetail2(null);

		return constraintsdetails2;
	}

	public List<Datasamplemodel> getDatasamplemodels() {
		return this.datasamplemodels;
	}

	public void setDatasamplemodels(List<Datasamplemodel> datasamplemodels) {
		this.datasamplemodels = datasamplemodels;
	}

	public Datasamplemodel addDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().add(datasamplemodel);
		datasamplemodel.setColumnsdetail(this);

		return datasamplemodel;
	}

	public Datasamplemodel removeDatasamplemodel(Datasamplemodel datasamplemodel) {
		getDatasamplemodels().remove(datasamplemodel);
		datasamplemodel.setColumnsdetail(null);

		return datasamplemodel;
	}

	public List<Relationsdetail> getRelationsdetails() {
		return this.relationsdetails;
	}

	public void setRelationsdetails(List<Relationsdetail> relationsdetails) {
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

}