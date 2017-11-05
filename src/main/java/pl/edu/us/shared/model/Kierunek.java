package pl.edu.us.shared.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import pl.edu.us.shared.enums.TypSemestru;

@NamedQueries(value = {
// @NamedQuery(name = Student.DAJ_STUDENTA, query =
// "Select u from Student u where u.imie like  :imie or u.nazwisko like :nazwisko"),
@NamedQuery(name = Kierunek.POBIERZ_WSZYSTKIE, query = "Select u from Kierunek u"),
// @NamedQuery(name = Student.NEXT_ID, query =
// "Select max(u.id) + 1 from Student u")
})
@Entity
@Table(name = "KIERUNEK")
public class Kierunek implements java.io.Serializable {

	private static final long serialVersionUID = -3303184554632383992L;

	public static final String POBIERZ_WSZYSTKIE = "Kierunek.POBIERZ_WSZYSTKIE";

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "nazwa")
	private String nazwa;

	@Column(name = "ILOSC_SEM")
	private Integer iloscSemestrow;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kierunek")
	private List<OkresStudiow> okresy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kierunek", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Semestr> semestry;

	@Column(name = "czesne")
	private BigDecimal czesne;

	@Column(name = "max_grupa")
	private Integer maxGrupa;

	@Transient
	private Integer rokOd;

	@Transient
	private TypSemestru typSemestru;

	public Kierunek() {

	}

	public Kierunek(Integer id, String nazwa, Integer iloscSemestrow, List<OkresStudiow> okresy, BigDecimal czesne) {
		super();
		this.id = id;
		this.nazwa = nazwa;
		this.iloscSemestrow = iloscSemestrow;
		this.okresy = okresy;
		this.czesne = czesne;
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

	public Integer getIloscSemestrow() {
		return iloscSemestrow;
	}

	public void setIloscSemestrow(Integer iloscSemestrow) {
		this.iloscSemestrow = iloscSemestrow;
	}

	public List<OkresStudiow> getOkresy() {
		return okresy;
	}

	public void setOkresy(List<OkresStudiow> okresy) {
		this.okresy = okresy;
	}

	public BigDecimal getCzesne() {
		return czesne;
	}

	public void setCzesne(BigDecimal czesne) {
		this.czesne = czesne;
	}

	@Override
	public String toString() {
		return nazwa;
	}

	public List<Semestr> getSemestry() {
		return semestry;
	}

	public void setSemestry(List<Semestr> semestry) {
		this.semestry = semestry;
	}

	public Integer getRokOd() {
		return rokOd;
	}

	public void setRokOd(Integer rokOd) {
		this.rokOd = rokOd;
	}

	public TypSemestru getTypSemestru() {
		return typSemestru;
	}

	public void setTypSemestru(TypSemestru typSemestru) {
		this.typSemestru = typSemestru;
	}

	public Integer getMaxGrupa() {
		return maxGrupa;
	}

	public void setMaxGrupa(Integer maxGrupa) {
		this.maxGrupa = maxGrupa;
	}
}
