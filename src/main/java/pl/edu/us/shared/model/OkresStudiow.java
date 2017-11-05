package pl.edu.us.shared.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "OKRES_ST")
public class OkresStudiow implements java.io.Serializable {

	private static final long serialVersionUID = 508147371112050508L;
	@Id
	@Column(name = "id")
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kierunek_id")
	private Kierunek kierunek;
	// @Column(name = "kierunek_id")
	// private Integer kierunek;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	@Column(name = "DATA_OD")
	@Temporal(TemporalType.DATE)
	private Date dataOd;
	@Column(name = "DATA_DO")
	@Temporal(TemporalType.DATE)
	private Date datado;

	public OkresStudiow() {
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
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Date getDataOd() {
		return dataOd;
	}
	public void setDataOd(Date dataOd) {
		this.dataOd = dataOd;
	}
	public Date getDatado() {
		return datado;
	}
	public void setDatado(Date datado) {
		this.datado = datado;
	}
	
}
