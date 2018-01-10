package pl.edu.us.shared.enums;

public enum TypWniosku implements KodNazwaProvider {

    STYPENDIUM("ST", "Stypendium"), PRZED_SESJI("PS", "Przedłużenie sesji"), WARUNEK("WA", "Warunek"), KOMISJA("EK", "Egzamin komisyjny");

    private String kod;
    private String nazwa;

    private TypWniosku(String kod, String nazwa) {
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
