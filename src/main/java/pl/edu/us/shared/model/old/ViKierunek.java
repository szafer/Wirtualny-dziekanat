package pl.edu.us.shared.model.old;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VI_KIERUNEK")
public class ViKierunek implements java.io.Serializable {

	private static final long serialVersionUID = -2704098502576275289L;
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "KIERUNEK_ID")
	private Integer kierunekId;
	@Column(name = "DATA_OD")
	@Temporal(TemporalType.DATE)
	private Date dataod;
	@Column(name = "DATA_DO")
	@Temporal(TemporalType.DATE)
	private Date dataDo;

	public ViKierunek() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataod() {
		return dataod;
	}

	public void setDataod(Date dataod) {
		this.dataod = dataod;
	}

	public Date getDataDo() {
		return dataDo;
	}

	public void setDataDo(Date dataDo) {
		this.dataDo = dataDo;
	}

}
