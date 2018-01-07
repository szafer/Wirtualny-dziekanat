package pl.edu.us.shared.enums;

public enum Semestr implements KodNazwaProvider {

    LETNI("L", "Letni"), ZIMOWY("Z", "Zimowy");
    private String kod;
    private String nazwa;

    private Semestr(String kod, String nazwa) {
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
