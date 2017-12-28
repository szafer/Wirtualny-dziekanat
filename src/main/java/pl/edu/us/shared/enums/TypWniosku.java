package pl.edu.us.shared.enums;

public enum TypWniosku {

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

    public String getKod() {
        return kod;
    }

    public String getNazwa() {
        return nazwa;
    }
}
