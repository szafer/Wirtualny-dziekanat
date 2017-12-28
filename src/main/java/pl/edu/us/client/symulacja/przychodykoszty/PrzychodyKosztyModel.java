package pl.edu.us.client.symulacja.przychodykoszty;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.ViPrzychodProperties;
import pl.edu.us.shared.model.old.ViPrzychod;

@Singleton
public class PrzychodyKosztyModel {

	private ListStore<ViPrzychod> storePrzychody;
	private ListStore<ViPrzychod> storePrzychodyAll;

	ViPrzychodProperties props = GWT.create(ViPrzychodProperties.class);
	private ListStore<ViPrzychod> storeViPrzychod;
	// private ListStore<ViPrzychod> storeViPrzychodAll;

	@Inject
	public PrzychodyKosztyModel() {
		storePrzychody = new ListStore<ViPrzychod>(props.key());
		storePrzychodyAll = new ListStore<ViPrzychod>(props.key());
		storeViPrzychod = new ListStore<ViPrzychod>(props.key());
		// storeViPrzychodAll = new ListStore<ViPrzychod>(props.key());

	}

	public void wyczysc(){
	storePrzychody.clear();
	storePrzychodyAll.clear();
}
	public ListStore<ViPrzychod> getStorePrzychody() {
		return storePrzychody;
	}

	public ViPrzychodProperties getProps() {
		return props;
	}

	public ListStore<ViPrzychod> getStorePrzychodyAll() {
		return storePrzychodyAll;
	}

	public ListStore<ViPrzychod> getStoreViPrzychod() {
		return storeViPrzychod;
	}

// public ListStore<ViPrzychod> getStoreViPrzychodAll() {
// return storeViPrzychodAll;
// }
}
