package pl.edu.us.shared.dto;

public class SlownikDTO extends DTO {

    private static final long serialVersionUID = 2111122870367597491L;

    private String nazwa;

    public SlownikDTO() {
        super();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
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
