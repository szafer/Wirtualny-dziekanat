package pl.edu.us.client.accesproperties;

import java.math.BigDecimal;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.model.old.ViPrzychod;

public interface ViPrzychodProperties extends AccProperties<ViPrzychod> {

	ValueProvider<ViPrzychod, Integer> id();

	ValueProvider<ViPrzychod, Integer> rok();

	ValueProvider<ViPrzychod, Integer> mc();

	ValueProvider<ViPrzychod, String> miesiac();

	ValueProvider<ViPrzychod, String> kierunek();

	ValueProvider<ViPrzychod, BigDecimal> przychod();

	ValueProvider<ViPrzychod, BigDecimal> kosztNaStud();

	ValueProvider<ViPrzychod, Integer> ilStud();

	// pola wyliczeniowe
	ValueProvider<ViPrzychod, Boolean> zal();

	ValueProvider<ViPrzychod, BigDecimal> dochod();

	ValueProvider<ViPrzychod, BigDecimal> koszt();
}
