package pl.edu.us.shared.enums;

public enum Message {
    LOADING("�adowanie danych"),
    SAVING("Zapis danych"),
    CHECKING("Sprawdzanie danych"),
    WAITING("Oczekiwanie"),
    ANALYZING_ABSENCE("<center>Prosz� czeka�</center>Trwa analiza absencji pracownik�w"),
    PROCESSING("<center>Prosz� czeka�</center>Trwa wykonywanie procedury"),
    ROZLICZANIE("<center>Prosz� czeka�</center>Trwa wykonywanie rozliczenia kosztowego"),
    TWORZENIE_PACZKI("<center>Prosz� czeka�</center>Trwa tworzenie paczki"),
    PRZEKAZANIE_PACZKI_DO_KFK("<center>Prosz� czeka�</center>Trwa przekazywanie paczki do KFK"),
    DELETING("Usuwanie danych"),
    PLEASE_WAIT("Prosz� czeka�"),
    ROZLICZANIE_ZASILKU("Rozliczanie pracownika");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
