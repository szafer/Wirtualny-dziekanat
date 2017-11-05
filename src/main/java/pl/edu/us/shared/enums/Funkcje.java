package pl.edu.us.shared.enums;

public enum Funkcje {

	ADMIN("Administrator"), STUDENT("Lista studentow"), STUDENT_OCENY("Oceny Studenta");

	private String nazwa;
	private String kod;

	private Funkcje(String kod) {
		// this.nazwa = nazwa;
		this.kod = kod;
	}

	@Override
	public String toString() {
		return kod;
	}

	public String getKod() {
		return kod;
	}
}
