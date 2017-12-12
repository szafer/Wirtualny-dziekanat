package pl.edu.us.shared.enums;

/**
 * @author marek
 * Enum określa rolę użytkownika
 */
public enum Rola {

    STUDENT("Student"), NAUCZYCIEL("Nauczyciel"), ADMIN("Administrator");

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

    public static Rola dajRole(int r) {
        switch (r) {
        case 0:
            return ADMIN;
        case 1:
            return STUDENT;
        case 2:
            return NAUCZYCIEL;
        default:
            return null;
        }
    }
}
