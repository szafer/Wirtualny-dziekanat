package pl.edu.us.shared.enums;

public enum StatusWniosku {

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

    public String getKod() {
        return kod;
    }

    public String getNazwa() {
        return nazwa;
    }
}
