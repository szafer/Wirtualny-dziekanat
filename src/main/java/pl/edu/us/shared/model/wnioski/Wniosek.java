package pl.edu.us.shared.model.wnioski;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import pl.edu.us.shared.enums.TypWniosku;
import pl.edu.us.shared.model.Persistent;

@Entity
@Table(name = "WNIOSEK")
public class Wniosek implements Persistent<Integer> {

    private static final long serialVersionUID = -7960675005132642988L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYP")
    @Enumerated(EnumType.ORDINAL)
    private TypWniosku typ;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "WZOR")
    private byte[] wzor;

    @Column(name = "NAZWA_OBRAZU")
    private String nazwaObrazu;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wniosek")
//    private List<UWniosek> wnioskiUzytkownika;

    public Wniosek() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public TypWniosku getTyp() {
        return typ;
    }

    public void setTyp(TypWniosku typ) {
        this.typ = typ;
    }

    public byte[] getWzor() {
        return wzor;
    }

    public void setWzor(byte[] wzor) {
        this.wzor = wzor;
    }

    public String getNazwaObrazu() {
        return nazwaObrazu;
    }

    public void setNazwaObrazu(String nazwaObrazu) {
        this.nazwaObrazu = nazwaObrazu;
    }
}
