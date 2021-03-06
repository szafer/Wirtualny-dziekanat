package pl.edu.us.shared.enums;

/**
 * @author marek
 * Enum określa rolę użytkownika
 */
public enum Rola implements KodNazwaProvider {

    STUDENT("Student"), NAUCZYCIEL("Wykładowca"), ADMIN("Administrator");

    private String kod;

    private Rola(String kod) {
        this.kod = kod;
    }

    @Override
    public String toString() {
        return kod;
    }

    public String getKod() {
        return kod;
    }

    public String getNazwa() {
        return kod;
    }
}
