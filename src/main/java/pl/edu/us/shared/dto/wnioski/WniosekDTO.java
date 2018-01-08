package pl.edu.us.shared.dto.wnioski;

import java.io.Serializable;

import pl.edu.us.shared.dto.DTO;
import pl.edu.us.shared.enums.TypWniosku;

public class WniosekDTO extends DTO implements Serializable {

    private static final long serialVersionUID = -9100026837671778843L;
    private TypWniosku typ;
    private Byte[] wzor;
    private String nazwaObrazu;

    public WniosekDTO() {
        super();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public TypWniosku getTyp() {
        return typ;
    }

    public void setTyp(TypWniosku typ) {
        this.typ = typ;
    }

    public Byte[] getWzor() {
        return wzor;
    }

    public void setWzor(Byte[] wzor) {
        this.wzor = wzor;
    }

    @Override
    public String toString() {
        return typ.toString();
    }

    public String getNazwaObrazu() {
        return nazwaObrazu;
    }

    public void setNazwaObrazu(String nazwaObrazu) {
        this.nazwaObrazu = nazwaObrazu;
    }
}
