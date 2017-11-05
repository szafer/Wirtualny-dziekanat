package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.model.Przedmiot;

public interface PrzedmiotProperties extends PropertyAccess<Przedmiot> {

	@Path("id")
	ModelKeyProvider<Przedmiot> key();

	ValueProvider<Przedmiot, String> nazwa();

	ValueProvider<Przedmiot, Integer> godzinyWyklad();

	ValueProvider<Przedmiot, Integer> godzinyCw();
}
