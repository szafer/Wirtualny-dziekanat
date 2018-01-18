package pl.edu.us.shared.model.przedmioty;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

@NamedQueries(value = {
    @NamedQuery(name = UPrzedmiot.DAJ_STUDENTOW_PRZEDMIOTU, query = "Select u from UPrzedmiot u "
        + " where u.uzytkownik.rola = pl.edu.us.shared.enums.Rola.STUDENT "
        + " and u.przedmiot.id = :przedmiot "
        + " order by u.dataSemestru desc, u.uzytkownik.nazwisko, u.uzytkownik.imie "),
    @NamedQuery(name = UPrzedmiot.DAJ_WYKLADOWCE, query = "Select u from UPrzedmiot u "
        + " where u.uzytkownik.rola = pl.edu.us.shared.enums.Rola.NAUCZYCIEL "
        + " and u.przedmiot.id = :przedmiot "
        + " order by u.uzytkownik.nazwisko, u.uzytkownik.imie "),
    @NamedQuery(name = UPrzedmiot.DAJ_WYKLADOWCOW, query = "Select u from User u where u.rola = pl.edu.us.shared.enums.Rola.NAUCZYCIEL")

})
@Entity
@Table(name = "U_PRZEDMIOT")
@SequenceGenerator(name = "SEQ_GEN_U_PRZEDMIOT", sequenceName = "SEQ_U_PRZEDMIOT", allocationSize = 1)
public class UPrzedmiot implements Persistent<Integer> {

    private static final long serialVersionUID = 4718627897692145300L;
    public static final String DAJ_STUDENTOW_PRZEDMIOTU = "UPrzedmiot.DAJ_STUDENTOW_PRZEDMIOTU";
    public static final String DAJ_WYKLADOWCE = "UPrzedmiot.DAJ_WYKLADOWCE";
    public static final String DAJ_WYKLADOWCOW = "UPrzedmiot.DAJ_WYKLADOWCOW";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_U_PRZEDMIOT")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATA_SEMESTRU")
    @Temporal(TemporalType.DATE)
    private Date dataSemestru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private User uzytkownik;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRZEDMIOT_ID")
    private Przedmiot przedmiot;

    @Column(name = "OCENA1")
    private Float ocena1;

    @Column(name = "OCENA2")
    private Float ocena2;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSemestru() {
        return dataSemestru;
    }

    public void setDataSemestru(Date dataSemestru) {
        this.dataSemestru = dataSemestru;
    }

    public User getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(User uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Przedmiot getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(Przedmiot przedmiot) {
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

}
