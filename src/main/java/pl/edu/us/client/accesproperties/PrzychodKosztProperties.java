package pl.edu.us.client.accesproperties;

import java.math.BigDecimal;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.model.old.PrzychodKoszt;

public interface PrzychodKosztProperties extends PropertyAccess<PrzychodKoszt> {
	@Path("id")
	ModelKeyProvider<PrzychodKoszt> nameKey();

	ValueProvider<PrzychodKoszt, String> miesiac();

	ValueProvider<PrzychodKoszt, Integer> mc();

	ValueProvider<PrzychodKoszt, Integer> rok();

	ValueProvider<PrzychodKoszt, BigDecimal> przychod();

	ValueProvider<PrzychodKoszt, BigDecimal> koszt();

	ValueProvider<PrzychodKoszt, String> kierunek();
}
