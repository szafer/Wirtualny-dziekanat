package pl.edu.us.shared.enums;

public enum Semestr {

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

    public String getKod() {
        return kod;
    }

    public String getNazwa() {
        return nazwa;
    }
}
