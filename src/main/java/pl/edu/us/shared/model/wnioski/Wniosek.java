package pl.edu.us.shared.model.wnioski;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.us.shared.enums.TypWniosku;
import pl.edu.us.shared.model.Persistent;

@Entity
@Table(name = "WNIOSEK")
@SequenceGenerator(name = "SEQ_GEN_WNIOSEK", sequenceName = "SEQ_WNIOSEK", allocationSize = 1)
public class Wniosek implements Persistent<Integer> {

    private static final long serialVersionUID = -7960675005132642988L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_WNIOSEK")
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

    public Wniosek() {
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
