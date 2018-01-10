package pl.edu.us.shared.dto;

public class ObrazDTO extends DTO {

    private static final long serialVersionUID = 6484493857936340410L;

    private Byte[] bs;
    private String rozszerzenie;
    private String nazwa;
    private Long size;
    private String obraz;

    public ObrazDTO() {
        super();
    }

    public Byte[] getBs() {
        return bs;
    }

    public void setBs(Byte[] bs) {
        this.bs = bs;
    }

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getObraz() {
        return obraz;
    }

    public void setObraz(String obraz) {
        this.obraz = obraz;
    }
}
