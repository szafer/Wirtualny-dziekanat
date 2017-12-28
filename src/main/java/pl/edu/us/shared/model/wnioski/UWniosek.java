package pl.edu.us.shared.model.wnioski;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

@Entity
@Table(name = "U_WNIOSEK")
@SequenceGenerator(name = "SEQ_GEN_U_WNIOSEK", sequenceName = "SEQ_U_WNIOSEK", allocationSize = 1)
public class UWniosek implements Persistent<Integer> {

    private static final long serialVersionUID = 4718627897692145300L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_U_WNIOSEK")
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private User uzytkownik;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "WNIOSEK_ID")
//    private Wniosek wniosek;

    @Column(name = "WNIOSEK_ZLOZONY")
    private Byte[] zlozonyWniosek;

    @Column(name = "DATA_ZLOZENIA")
    @Temporal(TemporalType.DATE)
    private Date dataZlozenia;

    @Column(name = "STATUS")
    @Enumerated(EnumType.ORDINAL)
    private StatusWniosku status;

    @Column(name = "DATA_ROZPATRZENIA")
    @Temporal(TemporalType.DATE)
    private Date dataRozpatrzenia;

    @Column(name = "KWOTA")
    private BigDecimal kwota;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "STYPENDIUM_ID")
//    private Stypendia stypendia;
    public UWniosek() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(User uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

//    public Wniosek getWniosek() {
//        return wniosek;
//    }
//
//    public void setWniosek(Wniosek wniosek) {
//        this.wniosek = wniosek;
//    }

    public Byte[] getZlozonyWniosek() {
        return zlozonyWniosek;
    }

    public void setZlozonyWniosek(Byte[] zlozonyWniosek) {
        this.zlozonyWniosek = zlozonyWniosek;
    }

    public Date getDataZlozenia() {
        return dataZlozenia;
    }

    public void setDataZlozenia(Date dataZlozenia) {
        this.dataZlozenia = dataZlozenia;
    }

    public StatusWniosku getStatus() {
        return status;
    }

    public void setStatus(StatusWniosku status) {
        this.status = status;
    }

    public Date getDataRozpatrzenia() {
        return dataRozpatrzenia;
    }

    public void setDataRozpatrzenia(Date dataRozpatrzenia) {
        this.dataRozpatrzenia = dataRozpatrzenia;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }
}
