package pl.edu.us.shared.enums;

public enum StatusWniosku implements KodNazwaProvider {

    OCZEKJACY("O", "Oczekujący"), ROZPATRYWANY("R", "Rozpatrywany"), ZAAKCEPTOWANY("Z", "Zaakceptowany"), ODRZUCONY("X", "Odrzucony");

    private String kod;
    private String nazwa;

    private StatusWniosku(String kod, String nazwa) {
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
