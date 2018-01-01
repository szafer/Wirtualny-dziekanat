package pl.edu.us.shared.dto.przedmioty;

import java.io.Serializable;
import java.util.Date;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Semestr;

public class UPrzedmiotDTO implements Serializable {

    private static final long serialVersionUID = 8190762839778559308L;

    private Integer id;
    private Date dataSemestru;
    private UserDTO uzytkownik;
    private PrzedmiotDTO przedmiot;
    private Float ocena1;
    private Float ocena2;
    private Semestr semestr;
//    private String przedmiotNazwa, semestrNazwa;

    public UPrzedmiotDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSemestru() {
        return dataSemestru;
    }

    public void setDataSemestru(Date dataSemestru) {
        this.dataSemestru = dataSemestru;
    }

    public UserDTO getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(UserDTO uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public PrzedmiotDTO getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(PrzedmiotDTO przedmiot) {
        this.przedmiot = przedmiot;
    }

    public Float getOcena1() {
        return ocena1;
    }

    public void setOcena1(Float ocena1) {
        this.ocena1 = ocena1;
    }

    public Float getOcena2() {
        return ocena2;
    }

    public void setOcena2(Float ocena2) {
        this.ocena2 = ocena2;
    }

    public Semestr getSemestr() {
        return semestr;
    }

    public void setSemestr(Semestr semestr) {
        this.semestr = semestr;
    }

//    public String getPrzedmiotNazwa() {
//        return przedmiot.getNazwa();
//    }
//
//    public String getSemestrNazwa() {
//        return semestr.getNazwa();
//    }
}
