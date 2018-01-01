package pl.edu.us.shared.dto.wnioski;

import java.io.Serializable;

import pl.edu.us.shared.enums.TypWniosku;

public class WniosekDTO implements Serializable {

    private static final long serialVersionUID = -9100026837671778843L;
    private Integer id;
    private TypWniosku typ;
    private Byte[] wzor;

    public WniosekDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
