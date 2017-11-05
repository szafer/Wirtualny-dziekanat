package pl.edu.us.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class PrzychodKoszt implements Serializable {

	private static final long serialVersionUID = 3998855619543013128L;
	@Id
	private Integer id;
	private String miesiac;
	private Integer mc;
	private Integer rok;
	private BigDecimal przychod;
	private BigDecimal koszt;
	private String kierunek;
	public PrzychodKoszt() {
		}

	public PrzychodKoszt(Integer id, String miesiac, Integer mc, Integer rok, BigDecimal przychod, BigDecimal koszt,
			String kierunek) {
		super();
		this.id = id;
		this.miesiac = miesiac;
		this.mc = mc;
		this.rok = rok;
		this.przychod = przychod;
		this.koszt = koszt;
		this.kierunek = kierunek;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMiesiac() {
		return miesiac;
	}

	public void setMiesiac(String miesiac) {
		this.miesiac = miesiac;
	}

	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public BigDecimal getPrzychod() {
		return przychod;
	}

	public void setPrzychod(BigDecimal przychod) {
		this.przychod = przychod;
	}

	public BigDecimal getKoszt() {
		return koszt;
	}

	public void setKoszt(BigDecimal koszt) {
		this.koszt = koszt;
	}

	public Integer getMc() {
		return mc;
	}

	public void setMc(Integer mc) {
		this.mc = mc;
	}

	public void setKierunek(String kierunek) {
		this.kierunek = kierunek;
	}

	public String getKierunek() {
		return kierunek;
	}
}
