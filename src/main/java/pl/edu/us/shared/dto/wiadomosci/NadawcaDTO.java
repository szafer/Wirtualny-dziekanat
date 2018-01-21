package pl.edu.us.shared.dto.wiadomosci;

import java.util.Date;
import java.util.List;

import pl.edu.us.shared.dto.DTO;

public class NadawcaDTO extends DTO {

    private static final long serialVersionUID = -809042943471730072L;

    private Integer userId;

    private String temat;

    private String wiadomosc;

    private Date data;

    private List<OdbiorcaDTO> odbiorcy;

    private String imie;
    private String nazwisko;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        this.wiadomosc = wiadomosc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<OdbiorcaDTO> getOdbiorcy() {
        return odbiorcy;
    }

    public void setOdbiorcy(List<OdbiorcaDTO> odbiorcy) {
        this.odbiorcy = odbiorcy;
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
