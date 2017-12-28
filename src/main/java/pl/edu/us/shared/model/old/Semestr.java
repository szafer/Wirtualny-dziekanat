package pl.edu.us.shared.model.old;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.edu.us.shared.model.przedmioty.Przedmiot;

//@NamedQueries(value = {
		// @NamedQuery(name = Semestr.POBIERZ_PO_KIERUNEK_I_NUMER, query =
		// "Select u from Semestr u where u.kierunek.id =  :kierunekId and u.numer = :numer"),
//		@NamedQuery(name = Semestr.POBIERZ_WSZYSTKIE, query = "Select u from Semestr u"),
//		@NamedQuery(name = Semestr.MAX_ID, query = "Semestr max(u.id) + 1 from Semestr u") })
@Entity
@Table(name = "SEMESTR")
public class Semestr implements java.io.Serializable {

	private static final long serialVersionUID = -5676255783992077642L;
	public static final String POBIERZ_WSZYSTKIE = "Semestr.POBIERZ_WSZYSTKIE";
	public static final String MAX_ID = "Semestr.MAX_ID";
	public static final String POBIERZ_PO_KIERUNEK_I_NUMER = "Semestr.POBIERZ_PO_KIERUNEK_I_NUMER";
	@Id
	@Column(name = "ID")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KIERUNEK_ID")
	private Kierunek kierunek;
	@Column(name = "NUMER")
	private Integer numer;

//	@ManyToMany(fetch = FetchType.LAZY/* , cascade = CascadeType.ALL */)
//	@JoinTable(name = "SEMESTR_PRZEDMIOT", joinColumns = { @JoinColumn(name = "SEMESTR_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PRZEDMIOT_ID", nullable = false, updatable = false) })
//	private List<Przedmiot> przedmioty;

	public Semestr() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Kierunek getKierunek() {
		return kierunek;
	}

	public void setKierunek(Kierunek kierunek) {
		this.kierunek = kierunek;
	}

	public Integer getNumer() {
		return numer;
	}

	public void setNumer(Integer numer) {
		this.numer = numer;
	}
//
//	public List<Przedmiot> getPrzedmioty() {
//		return przedmioty;
//	}
//
//	public void setPrzedmioty(List<Przedmiot> przedmioty) {
//		this.przedmioty = przedmioty;
//	}
}
