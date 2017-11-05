package pl.edu.us.client.symulacja.przychodykosztykierunki;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.ViPrzychodProperties;
import pl.edu.us.client.main.TestData;
import pl.edu.us.shared.model.ViPrzychod;

@Singleton
public class PKKierunkiModel {

	private ListStore<ViPrzychod> storeLata;
	// private ListStore<ViPrzychod> storeRokAll;
	// private ListStore<PrzychodKoszt> storePrzychodKoszt;
	private ListStore<ViPrzychod> storeRok;
	private ListStore<ViPrzychod> storeAll;
	private ListStore<ViPrzychod> storeKierunek;
	// private ListStore<ViPrzychod> storeKierunekAll;
	ViPrzychodProperties props = GWT.create(ViPrzychodProperties.class);

	@Inject
	public PKKierunkiModel() {
		TestData d = new TestData();// dane na kilka lat do charta
		storeLata = new ListStore<ViPrzychod>(props.key());
		// dane roczne do symulacja rok dialog
		storeRok = new ListStore<ViPrzychod>(props.key());
		storeAll = new ListStore<ViPrzychod>(props.key());
		// storeAll.addAll(d.getDane());
		// dane roczne kierunek
		storeKierunek = new ListStore<ViPrzychod>(props.key());

	}

	public void wyczysc() {
		storeLata.clear();
		storeRok.clear();
		storeAll.clear();
		storeKierunek.clear();
	}
	public ListStore<ViPrzychod> getStoreLata() {
		return storeLata;
	}

	public ViPrzychodProperties getProps() {
		return props;
	}

	public ListStore<ViPrzychod> getStoreRok() {
		return storeRok;
	}

	public ListStore<ViPrzychod> getStoreAll() {
		return storeAll;
	}

	public ListStore<ViPrzychod> getStoreKierunek() {
		return storeKierunek;
	}
}
