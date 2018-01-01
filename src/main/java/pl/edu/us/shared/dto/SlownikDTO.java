package pl.edu.us.shared.dto;

import java.io.Serializable;

public class SlownikDTO implements Serializable {

    private static final long serialVersionUID = 2111122870367597491L;

    private Integer id;
    private String nazwa;

    public SlownikDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
