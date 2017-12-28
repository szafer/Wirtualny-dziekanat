package pl.edu.us.shared.model.wnioski;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import pl.edu.us.shared.model.Persistent;

@Entity
@Table(name = "STYPENDIA")
public class Stypendia implements Persistent<Integer> {

    private static final long serialVersionUID = -1641222140184318577L;

    @Id
    @Column(name = "ID")
    private Integer id;

//    @JoinColumn(name = "U_WNIOSEK_ID")
//    private UWniosek wniosekUzytkownika;

    @Column(name = "KWOTA")
    private BigDecimal kwota;

    public Stypendia() {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

//    public UWniosek getWniosekUzytkownika() {
//        return wniosekUzytkownika;
//    }
//
//    public void setWniosekUzytkownika(UWniosek wniosekUzytkownika) {
//        this.wniosekUzytkownika = wniosekUzytkownika;
//    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }
}
