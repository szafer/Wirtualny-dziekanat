package pl.edu.us.shared.dto;

import java.io.Serializable;

public class DTO implements Serializable {

    private static final long serialVersionUID = -984428140082057437L;
    private Integer id;

    public DTO() {
    }

    public DTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
