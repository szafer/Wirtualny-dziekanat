package pl.edu.us.shared.model;

public abstract class Person implements Persistent<Integer> {

	private static final long serialVersionUID = -357986238285513846L;

	public Person() {
	}

	public abstract String getImie();

	public abstract void setImie(String imie);

	public abstract String getNazwisko();

	public abstract void setNazwisko(String nazwisko);


}
