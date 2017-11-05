package pl.edu.us.shared.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ROLA")
public class Rola implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4371340968804978953L;

	@Id
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "NAZWA")
	private String nazwa;

	@Column(name = "KOD")
	private String kod;

	@Column(name = "PRZ")
	private boolean prz;

	@Column(name = "AKT")
	private boolean akt;

	public Rola() {
	}

	public Rola(int id, User user, String nazwa, String kod, boolean prz, boolean akt) {
		this.id = id;
		this.user = user;
		this.nazwa = nazwa;
		this.kod = kod;
		this.prz = prz;
		this.akt = akt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public boolean isPrz() {
		return prz;
	}

	public void setPrz(boolean prz) {
		this.prz = prz;
	}

	public boolean isAkt() {
		return akt;
	}

	public void setAkt(boolean akt) {
		this.akt = akt;
	}

}
