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

import org.hibernate.annotations.Cascade;

import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;
import pl.edu.us.shared.model.wnioski.UWniosek;

@NamedQueries(value = {
    @NamedQuery(name = User.DAJ_USERA, query = "Select u from User u where u.login = :login and u.password = :password"),
    @NamedQuery(name = User.POBIERZ_WSZYSTKIE, query = "Select u from User u"),
    @NamedQuery(name = User.NEXT_ID, query = "Select max(u.id) + 1 from User u"),
    @NamedQuery(name = User.POBIERZ_HASLO_PO_EMAIL, query = "Select u.password from User u where u.email = :email"),
    @NamedQuery(name = User.DAJ_USERA_PO_LOGINIE, query = "Select u from User u where u.login = :login"),
    @NamedQuery(name = User.CZY_EMAIL_WYSTEPUJE, query = "Select 1 from User u where u.email = :email")
})
@Entity
@Table(name = "UZYTKOWNIK")
@SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "SEQ_USER", allocationSize = 1)
public class User extends Person {

    private static final long serialVersionUID = -608313502889986477L;
    public static final String DAJ_USERA = "User.DAJ_USERA";
    public static final String POBIERZ_WSZYSTKIE = "User.POBIERZ_WSZYSTKIE";
    public static final String NEXT_ID = "User.NEXT_ID";
    public static final String POBIERZ_HASLO_PO_EMAIL = "User.POBIERZ_HASLO_PO_EMAIL";
    public static final String DAJ_USERA_PO_LOGINIE = "User.DAJ_USERA_PO_LOGINIE";
    public static final String CZY_EMAIL_WYSTEPUJE = "User.CZY_EMAIL_WYSTEPUJE";

    public static final String ID = "id";
    public static final String IMIE = "imie";
    public static final String NAZWISKO = "nazwisko";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
    @Column(name = "ID")
    private Integer id;
    //Dane logowania
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASS")
    private String password;
    @Column(name = "EMAIL")
    private String email;
    //Dane osobowe
    @Column(name = "IMIE")
    private String imie;
    @Column(name = "NAZWISKO")
    private String nazwisko;
    @Column(name = "DATA_UR")
    @Temporal(TemporalType.DATE)
    private Date dataUrodzenia;
    @Column(name = "ULICA")
    private String ulica;
    @Column(name = "NR_DOMU")
    private String nrDomu;
    @Column(name = "NR_MIESZKANIA")
    private String nrMieszkania;
    @Column(name = "MIASTO")
    private String miasto;
    @Column(name = "KOD_POCZT")
    private String kodPocztowy;
    @Column(name = "PLEC")
    @Enumerated(EnumType.ORDINAL)
    private Plec plec;
    @Column(name = "ROLA")
    @Enumerated(EnumType.ORDINAL)
    private Rola rola;
    @Column(name = "AKTYWNY")
    private Boolean aktywny;
    // licznik w dół ile logowań do zmiany hasła
    @Column(name = "ILOSC_LOGOWAN")
    private Integer iloscLogowan;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "U_WNIOSEK", joinColumns = { @JoinColumn(name = "UZYTKOWNIK_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
//        @JoinColumn(name = "WNIOSEK_ID", nullable = false, updatable = false) })
//    private List<Wniosek> wnioski;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uzytkownik", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<UWniosek> wnioskiUzytkownika;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "U_PRZEDMIOT", joinColumns = { @JoinColumn(name = "UZYTKOWNIK_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
//        @JoinColumn(name = "PRZEDMIOT_ID", nullable = false, updatable = false) })
//    private List<Przedmiot> przedmioty;

    public User() {
        this.aktywny = false;
        this.iloscLogowan = 0;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String toString() {
        return imie + " " + nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }

    public String getNrMieszkania() {
        return nrMieszkania;
    }

    public void setNrMieszkania(String nrMieszkania) {
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

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    public Rola getRola() {
        return rola;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }

    public Boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(Boolean aktywny) {
        this.aktywny = aktywny;
    }

    public Integer getIloscLogowan() {
        return iloscLogowan;
    }

    public void setIloscLogowan(Integer iloscLogowan) {
        this.iloscLogowan = iloscLogowan;
    }

    public List<UWniosek> getWnioskiUzytkownika() {
        return wnioskiUzytkownika;
    }

    public void setWnioskiUzytkownika(List<UWniosek> wnioskiUzytkownika) {
        this.wnioskiUzytkownika = wnioskiUzytkownika;
    }
//
//    public List<Przedmiot> getPrzedmioty() {
//        return przedmioty;
//    }
//
//    public void setPrzedmioty(List<Przedmiot> przedmioty) {
//        this.przedmioty = przedmioty;
//    }
}
