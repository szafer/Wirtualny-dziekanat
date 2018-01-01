package pl.edu.us.client.accesproperties;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.model.Person;

public interface PersonProperties<T> extends AccProperties<T> {

	ValueProvider<T, Integer> id();

	ValueProvider<T, String> imie();

	ValueProvider<T, String> nazwisko();


}