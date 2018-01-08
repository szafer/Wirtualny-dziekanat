package pl.edu.us.shared.dto.wiadomosci;

import java.util.Date;

import pl.edu.us.shared.model.wiadomosci.Nadawca;

public class OdbiorcaDTO extends WiadomoscDTO {

    private static final long serialVersionUID = 6099193096136912146L;
    private Integer userId;
    private NadawcaDTO nadawca;
    private Boolean odebrano;
    private Date dataOdbioru;
    private Boolean email;

    private String imie;
    private String nazwisko;

    public OdbiorcaDTO() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public NadawcaDTO getNadawca() {
        return nadawca;
    }

    public void setNadawca(NadawcaDTO nadawca) {
        this.nadawca = nadawca;
    }

    public Boolean getOdebrano() {
        return odebrano;
    }

    public void setOdebrano(Boolean odebrano) {
        this.odebrano = odebrano;
    }

    public Date getDataOdbioru() {
        return dataOdbioru;
    }

    public void setDataOdbioru(Date dataOdbioru) {
        this.dataOdbioru = dataOdbioru;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public String toString() {
        return nazwisko + " " + imie;
    }
}
