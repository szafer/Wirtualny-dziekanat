package pl.edu.us.shared.enums;

public enum Message {
    LOADING("£adowanie danych"),
    SAVING("Zapis danych"),
    CHECKING("Sprawdzanie danych"),
    WAITING("Oczekiwanie"),
    ANALYZING_ABSENCE("<center>Proszê czekaæ</center>Trwa analiza absencji pracowników"),
    PROCESSING("<center>Proszê czekaæ</center>Trwa wykonywanie procedury"),
    ROZLICZANIE("<center>Proszê czekaæ</center>Trwa wykonywanie rozliczenia kosztowego"),
    TWORZENIE_PACZKI("<center>Proszê czekaæ</center>Trwa tworzenie paczki"),
    PRZEKAZANIE_PACZKI_DO_KFK("<center>Proszê czekaæ</center>Trwa przekazywanie paczki do KFK"),
    DELETING("Usuwanie danych"),
    PLEASE_WAIT("Proszê czekaæ"),
    ROZLICZANIE_ZASILKU("Rozliczanie pracownika");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
