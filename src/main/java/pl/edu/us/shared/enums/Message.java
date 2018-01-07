package pl.edu.us.shared.enums;

public enum Message {
    LOADING("Ładowanie danych"),
    SAVING("Zapis danych"),
    CHECKING("Sprawdzanie danych"),
    WAITING("Oczekiwanie");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
