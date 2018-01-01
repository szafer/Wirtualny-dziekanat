package pl.edu.us.shared.dto.wnioski;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class UWniosekDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1659084593805480130L;

    private Integer id;

    private UserDTO uzytkownik;

    private WniosekDTO wniosek;

    private Byte[] zlozonyWniosek;

    private Date dataZlozenia;

    private StatusWniosku status;

    private Date dataRozpatrzenia;

    private BigDecimal kwota;

    public UWniosekDTO() {
        // TODO Auto-generated constructor stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(UserDTO uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

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

    public WniosekDTO getWniosek() {
        return wniosek;
    }

    public void setWniosek(WniosekDTO wniosek) {
        this.wniosek = wniosek;
    }
}
