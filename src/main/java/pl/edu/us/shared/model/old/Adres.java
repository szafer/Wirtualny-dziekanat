package pl.edu.us.shared.model.old;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

@Entity
//@NamedQuery(name = "Adres.findAll", query = "SELECT a FROM Adres a")
@SequenceGenerator(name = "SEQ_GEN_ADRES", sequenceName = "SEQ_ADRES", allocationSize = 1)
public class Adres implements Persistent<Long> {

    private static final long serialVersionUID = -45916188278312292L;

    public static final String FIND_ALL = "Adres.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_ADRES")

    private Long id;

    @Column(name = "KOD_POCZTOWY")
    private String kodPocztowy;

    private String miasto;

    @Column(name = "NUMER_DOMU")
    private String numerDomu;

    private String ulica;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    // bi-directional many-to-one association to Uzytkownik
    @ManyToOne
    @JoinColumn(name = "ID_UZYTKOWNIK")
    private User uzytkownik;

    public Adres() {
    }

    public String getKodPocztowy() {
        return this.kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getMiasto() {
        return this.miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getNumerDomu() {
        return this.numerDomu;
    }

    public void setNumerDomu(String numerDomu) {
        this.numerDomu = numerDomu;
    }

    public String getUlica() {
        return this.ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public User getUzytkownik() {
        return this.uzytkownik;
    }

    public void setUzytkownik(User uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Override
    public String toString() {
        return id + " | " + ulica + " | " + numerDomu + " | " + kodPocztowy + " | " + miasto;
    }

}
