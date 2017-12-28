package pl.edu.us.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -5020784293279382645L;

    private Integer id;
    private String login;
    private String password;
    private String email;
    private String imie;
    private String nazwisko;
    private Date dataUrodzenia;
    private String ulica;
    private String nrDomu;
    private String nrMieszkania;
    private String miasto;
    private String kodPocztowy;
    private Plec plec;
    private Rola rola;
    private Boolean aktywny;
    private Integer iloscLogowan;
    private List<UWniosekDTO> wnioskiUzytkownika;

    public UserDTO() {
        // TODO Auto-generated constructor stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<UWniosekDTO> getWnioskiUzytkownika() {
        return wnioskiUzytkownika;
    }

    public void setWnioskiUzytkownika(List<UWniosekDTO> wnioskiUzytkownika) {
        this.wnioskiUzytkownika = wnioskiUzytkownika;
    }
}