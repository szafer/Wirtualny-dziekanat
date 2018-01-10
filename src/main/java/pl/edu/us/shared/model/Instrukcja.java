package pl.edu.us.shared.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = Instrukcja.INSTRUKCJA, query = "SELECT i.instrukcja FROM Instrukcja i") })
@Entity
@Table(name = "PPLC_INSTRUKCJA")
public class Instrukcja {

	public static final String INSTRUKCJA = "Instrukcja.INSTRUKCJA";

	@Id
	private Long id;
	@Lob
	private byte[] instrukcja;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getInstrukcja() {
		return instrukcja;
	}

	public void setInstrukcja(byte[] instrukcja) {
		this.instrukcja = instrukcja;
	}

}
