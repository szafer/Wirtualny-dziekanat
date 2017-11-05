package pl.edu.us.client.accesproperties;

import java.math.BigDecimal;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.model.ViPrzychod;

public interface ObjectProperties<T> extends AccProperties<T> {


	ValueProvider<ViPrzychod, Integer> id();

	ValueProvider<ViPrzychod, Integer> rok();

	ValueProvider<ViPrzychod, Integer> mc();

	ValueProvider<ViPrzychod, Integer> kierunekId();

	ValueProvider<ViPrzychod, Integer> iloscStud();

	ValueProvider<ViPrzychod, BigDecimal> przychod();

	ValueProvider<ViPrzychod, BigDecimal> kosztNaStud();

	// pola wyliczeniowe
	ValueProvider<ViPrzychod, Boolean> zal();

	ValueProvider<ViPrzychod, BigDecimal> dochod();

	ValueProvider<ViPrzychod, BigDecimal> koszt();

}