package pl.edu.us.shared.dto.wnioski;

import pl.edu.us.shared.dto.DTO;
import pl.edu.us.shared.enums.TypWniosku;
import pl.edu.us.shared.model.wnioski.Wniosek;

public class WniosekDTO extends DTO {

    private static final long serialVersionUID = -9100026837671778843L;
    private TypWniosku typ;
    private byte[] wzor;
    private String nazwaObrazu;
    private String obraz;

    public WniosekDTO() {
        super();
    }

    public WniosekDTO(Wniosek w) {
        this.setId(w.getId());
        this.setNazwaObrazu(w.getNazwaObrazu());
        this.setTyp(w.getTyp());
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

    public byte[] getWzor() {
        return wzor;
    }

    public void setWzor(byte[] wzor) {
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

    public void setObraz(String obraz) {
        this.obraz = obraz;
    }

    public String getObraz() {
        return obraz;
    }
}
