package pl.edu.us.shared.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.enums.Plec;

@NamedQueries(value = {
    @NamedQuery(name = Student.DAJ_STUDENTA, query = "Select u from Student u where u.imie like  :imie or u.nazwisko like :nazwisko"),
    @NamedQuery(name = Student.POBIERZ_WSZYSTKIE, query = "Select u from Student u"),
    @NamedQuery(name = Student.NEXT_ID, query = "Select max(u.id) + 1 from Student u") })
@Entity
@Table(name = "STUDENT")
@SequenceGenerator(name = "SEQ_GEN_STUDENT", sequenceName = "SEQ_STUDENT", allocationSize = 1)
public class Student extends Person {

    private static final long serialVersionUID = 1438591092000300203L;
    public static final String DAJ_STUDENTA = "Student.DAJ_STUDENTA";
    public static final String POBIERZ_WSZYSTKIE = "Student.POBIERZ_WSZYSTKIE";
    public static final String NEXT_ID = "Student.NEXT_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_STUDENT")
    private Integer id;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "DATA_UR")
    @Temporal(TemporalType.DATE)
    private Date dataUrodzenia;
    @Column(name = "ULICA")
    private String ulica;
    @Column(name = "NR_DOMU")
    private Integer nrDomu;
    @Column(name = "NR_MIESZK")
    private Integer nrMieszkania;
    @Column(name = "MIASTO")
    private String miasto;
    @Column(name = "KOD_POCZT")
    private String kodPocztowy;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASS")
    private String password;
    @Column(name = "PLEC")
    @Enumerated(EnumType.ORDINAL)
    private Plec plec;
    @Column(name = "NR_ALBUMU")
    private Integer nrAlbumu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private List<OkresStudiow> okresy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<Ocena> oceny;

    public Student() {
    }

    public Integer getNrAlbumu() {
        return nrAlbumu;
    }

    public void setNrAlbumu(Integer nrAlbumu) {
        this.nrAlbumu = nrAlbumu;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public Integer getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(Integer nrDomu) {
        this.nrDomu = nrDomu;
    }

    public Integer getNrMieszkania() {
        return nrMieszkania;
    }

    public void setNrMieszkania(Integer nrMieszkania) {
        this.nrMieszkania = nrMieszkania;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public List<OkresStudiow> getOkresy() {
        return okresy;
    }

    public void setOkresy(List<OkresStudiow> okresy) {
        this.okresy = okresy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getImie() {
        return imie;
    }

    @Override
    public void setImie(String imie) {
        this.imie = imie;
    }

    @Override
    public String getNazwisko() {
        return nazwisko;
    }

    @Override
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public List<Ocena> getOceny() {
        return oceny;
    }

    public void setOceny(List<Ocena> oceny) {
        this.oceny = oceny;
    }
}
