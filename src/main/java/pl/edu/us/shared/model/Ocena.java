package pl.edu.us.shared.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.edu.us.shared.enums.TypOceny;

@Entity
@Table(name = "OCENA")
public class Ocena implements java.io.Serializable {

	private static final long serialVersionUID = 3675061993569461677L;
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "OCENA")
	private BigDecimal ocena;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "przedmiot_id")
	private Przedmiot przedmiot;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYP_ID")
	private TypOceny typ;

	public Ocena() {

	}

	public Ocena(Integer id, BigDecimal ocena, Przedmiot przedmiot, Student student, TypOceny typ) {
		super();
		this.id = id;
		this.przedmiot = przedmiot;
		this.student = student;
		this.typ = typ;
		this.ocena = ocena;
	}

	public BigDecimal getOcena() {
		return ocena;
	}

	public void setOcena(BigDecimal ocena) {
		this.ocena = ocena;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Przedmiot getPrzedmiot() {
		return przedmiot;
	}

	public void setPrzedmiot(Przedmiot przedmiot) {
		this.przedmiot = przedmiot;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public TypOceny getTyp() {
		return typ;
	}

	public void setTyp(TypOceny typ) {
		this.typ = typ;
	}

}
