package pl.edu.us.client.accesproperties;

import java.math.BigDecimal;
import java.util.List;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.enums.TypSemestru;
import pl.edu.us.shared.model.old.Kierunek;
import pl.edu.us.shared.model.old.OkresStudiow;
import pl.edu.us.shared.model.old.Semestr;

public interface KierunekProperties extends AccProperties<Kierunek> {
	//
	// ValueProvider<Kierunek, Integer> id();

	ValueProvider<Kierunek, String> nazwa();

	ValueProvider<Kierunek, Integer> iloscSemestrow();

	ValueProvider<Kierunek, List<OkresStudiow>> okresy();

	ValueProvider<Kierunek, List<Semestr>> semestry();

	ValueProvider<Kierunek, BigDecimal> czesne();

	ValueProvider<Kierunek, Integer> rokOd();

	ValueProvider<Kierunek, TypSemestru> typSemestru();

	ValueProvider<Kierunek, Integer> maxGrupa();
}
