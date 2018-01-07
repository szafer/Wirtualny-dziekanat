package pl.edu.us.shared.dto.wnioski;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import pl.edu.us.shared.dto.DTO;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.model.wnioski.UWniosek;

public class UWniosekDTO extends DTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1659084593805480130L;

    private UserDTO uzytkownik;

    private WniosekDTO wniosek;

    private Byte[] zlozonyWniosek;

    private Date dataZlozenia;

    private StatusWniosku status;

    private Date dataRozpatrzenia;

    private BigDecimal kwota;

    private String image;

    public UWniosekDTO() {
        super();
    }

    public UWniosekDTO(UWniosek uw, WniosekDTO wniosek) {
        super(uw.getId());
        this.uzytkownik = new UserDTO(uw.getUzytkownik());
        this.wniosek = wniosek;
        this.zlozonyWniosek = uw.getZlozonyWniosek();
        this.dataZlozenia = uw.getDataZlozenia();
        this.status = uw.getStatus();
        this.dataRozpatrzenia = uw.getDataRozpatrzenia();
        this.kwota = uw.getKwota();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
