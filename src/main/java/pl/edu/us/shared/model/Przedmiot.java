package pl.edu.us.shared.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRZEDMIOT")
public class Przedmiot implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -941343760426901334L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "nazwa")
	private String nazwa;

	@Column(name = "IL_WYK")
	private Integer godzinyWyklad;

	@Column(name = "IL_CW")
	private Integer godzinyCw;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "przedmiot")
	private List<Ocena> oceny;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SEMESTR_PRZEDMIOT", joinColumns = { @JoinColumn(name = "PRZEDMIOT_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "SEMESTR_ID", nullable = false, updatable = false) })
	private List<Semestr> semestry;

	public Przedmiot() {

	}

	public Przedmiot(Integer id, String nazwa, List<Ocena> oceny, Integer godzinyWyklad, Integer godzinyCw) {
		super();
		this.id = id;
		this.nazwa = nazwa;
		this.oceny = oceny;
		this.godzinyWyklad = godzinyWyklad;
		this.godzinyCw = godzinyCw;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Ocena> getOceny() {
		return oceny;
	}

	public void setOceny(List<Ocena> oceny) {
		this.oceny = oceny;
	}

	public Integer getGodzinyWyklad() {
		return godzinyWyklad;
	}

	public void setGodzinyWyklad(Integer godzinyWyklad) {
		this.godzinyWyklad = godzinyWyklad;
	}

	public Integer getGodzinyCw() {
		return godzinyCw;
	}

	public void setGodzinyCw(Integer godzinyCw) {
		this.godzinyCw = godzinyCw;
	}

	public List<Semestr> getSemestry() {
		return semestry;
	}

	public void setSemestry(List<Semestr> semestry) {
		this.semestry = semestry;
	}
}
