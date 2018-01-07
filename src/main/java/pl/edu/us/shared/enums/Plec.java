package pl.edu.us.shared.enums;

public enum Plec implements KodNazwaProvider {

    M("M", "Mężczyzna"), K("K", "Kobieta");

    private String kod;
    private String nazwa;

    private Plec(String kod, String nazwa) {
        this.kod = kod;
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public String getKod() {
        return kod;
    }

    @Override
    public String getNazwa() {
        return nazwa;
    }
}
