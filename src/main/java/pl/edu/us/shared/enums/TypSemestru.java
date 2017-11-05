package pl.edu.us.shared.enums;

import java.text.ParseException;

public enum TypSemestru {

	LETNI("Letni"), ZIMOWY("Zimowy");
	public static TypSemestru parseString(String object) throws ParseException {
		if (TypSemestru.LETNI.toString().equals(object)) {
			return TypSemestru.LETNI;
		} else if (TypSemestru.ZIMOWY.toString().equals(object)) {
			return TypSemestru.ZIMOWY;
		} else {
			throw new ParseException(object.toString() + " could not be parsed", 0);
		}
	}

	private String kod;

	private TypSemestru(String kod) {
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