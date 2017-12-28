package pl.edu.us.shared.model.old;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VI_KOSZT")
public class ViKoszt implements java.io.Serializable {

	private static final long serialVersionUID = 4118795917696717011L;

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "ROK")
	private Integer rok;
	@Column(name = "MC")
	private Integer mc;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_OD")
	private Date dataDo;
	@Column(name = "KIERUNEK_ID")
	private Integer kierunekId;
	@Column(name = "KOSZT_WYKLADW")
	private BigDecimal kosztWyk;

	public ViKoszt() {
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

	public Date getDataDo() {
		return dataDo;
	}

	public void setDataDo(Date dataDo) {
		this.dataDo = dataDo;
	}

	public Integer getKierunekId() {
		return kierunekId;
	}

	public void setKierunekId(Integer kierunekId) {
		this.kierunekId = kierunekId;
	}

	public BigDecimal getKosztWyk() {
		return kosztWyk;
	}

	public void setKosztWyk(BigDecimal kosztWyk) {
		this.kosztWyk = kosztWyk;
	}

}
