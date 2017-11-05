package pl.edu.us.shared.enums;

public enum TypOceny {

	CZASTKOWA("Cząstkowa"), KONCOWA("Semestralna"), T1("Termin 1"), T2("Termin 2");

	private String kod;

	private TypOceny(String kod) {
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
