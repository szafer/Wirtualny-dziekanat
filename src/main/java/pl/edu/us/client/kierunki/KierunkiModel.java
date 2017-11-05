package pl.edu.us.client.kierunki;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.KierunekProperties;
import pl.edu.us.client.accesproperties.PrzedmiotProperties;
import pl.edu.us.shared.model.Kierunek;
import pl.edu.us.shared.model.Przedmiot;

@Singleton
public class KierunkiModel {

	private ListStore<Kierunek> storeKierunki;
	private ListStore<Kierunek> doUsuniecia;
	private ListStore<Przedmiot> storePrzedmioty;
	private ListStore<Przedmiot> przedmiotyDoUsuniecia;

	PrzedmiotProperties przedmiotProp = GWT.create(PrzedmiotProperties.class);
	KierunekProperties kierProp = GWT.create(KierunekProperties.class);

	@Inject
	public KierunkiModel() {
		storeKierunki = new ListStore<Kierunek>(kierProp.key());
		doUsuniecia = new ListStore<Kierunek>(kierProp.key());
		storePrzedmioty = new ListStore(przedmiotProp.key());
		przedmiotyDoUsuniecia = new ListStore(przedmiotProp.key());

	}

	public ListStore<Kierunek> getStoreKierunki() {
		return storeKierunki;
	}

	public ListStore<Kierunek> getDoUsuniecia() {
		return doUsuniecia;
	}

	public KierunekProperties getKierProp() {
		return kierProp;
	}

	public PrzedmiotProperties getPrzedmiotProp() {
		return przedmiotProp;
	}

	public ListStore<Przedmiot> getPrzedmiotyDoUsuniecia() {
		return przedmiotyDoUsuniecia;
	}

	public ListStore<Przedmiot> getStorePrzedmioty() {
		return storePrzedmioty;
	}

}
