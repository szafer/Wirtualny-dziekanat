package pl.edu.us.shared.model.old;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "VI_PRZYCHOD_KOSZT")
public class ViPrzychod implements java.io.Serializable {

	private static final long serialVersionUID = -1972945249769228085L;
	// private static int AUTO_ID = 0;
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ROK")
	private Integer rok;
	@Column(name = "MC")
	private Integer mc;
	@Column(name = "MIESIAC")
	private String miesiac;
	@Column(name = "KIERUNEK")
	private String kierunek;
	@Column(name = "PRZYCHOD")
	private BigDecimal przychod;
	@Column(name = "KOSZT")
	private BigDecimal koszt;
	@Column(name = "DOCHOD")
	private BigDecimal dochod;
	@Column(name = "KOSZT_NA_ST")
	private BigDecimal kosztNaStud;
	@Column(name = "IL_STUD")
	private Integer ilStud;

	@Transient
	private Boolean zal;

	public ViPrzychod() {
		// id = AUTO_ID++;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public Integer getMc() {
		return mc;
	}

	public void setMc(Integer mc) {
		this.mc = mc;
	}

	public String getMiesiac() {
		return miesiac;
	}

	public void setMiesiac(String miesiac) {
		this.miesiac = miesiac;
	}

	public String getKierunek() {
		return kierunek;
	}

	public void setKierunek(String kierunek) {
		this.kierunek = kierunek;
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

	public BigDecimal getDochod() {
		return dochod;
	}

	public void setDochod(BigDecimal dochod) {
		this.dochod = dochod;
	}

	public BigDecimal getKosztNaStud() {
		return kosztNaStud;
	}

	public void setKosztNaStud(BigDecimal kosztNaStud) {
		this.kosztNaStud = kosztNaStud;
	}

	public Boolean getZal() {
		return zal;
	}

	public void setZal(Boolean zal) {
		this.zal = zal;
	}

	public Integer getIlStud() {
		return ilStud;
	}

	public void setIlStud(Integer ilStud) {
		this.ilStud = ilStud;
	}
}
