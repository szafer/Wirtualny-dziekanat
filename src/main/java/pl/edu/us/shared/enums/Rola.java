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
        return Rola.values()[r];
//        switch (r) {
//        case 0:
//            return STUDENT;
//        case 1:
//            return NAUCZYCIEL;
//        case 2:
//            return ADMIN;
//        default:
//            return null;
//        }
    }
}
