package pl.edu.us.client.uzytkownik.daneuzytkownika;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;
import pl.edu.us.shared.model.User;

@Singleton
public class DaneUzytkownikaModel {

    private final ListStore<Plec> storePlec;
    private final ListStore<Rola> storeRola;
    private User user;

    
	interface PlecProperties extends PropertyAccess<Plec> {
		ModelKeyProvider<Plec> kod();

		LabelProvider<Plec> nazwa();
	}

    interface RolaProperties extends PropertyAccess<Rola> {
        ModelKeyProvider<Rola> kod();

        LabelProvider<Rola> nazwa();
    }

    PlecProperties plecProp = GWT.create(PlecProperties.class);
    RolaProperties rolaProp = GWT.create(RolaProperties.class);

	@Inject
	public DaneUzytkownikaModel() {
	    storePlec = new ListStore<Plec>(plecProp.kod());
        storePlec.addAll(Arrays.asList(Plec.values()));
        storeRola = new ListStore<Rola>(rolaProp.kod());
        storeRola.addAll(Arrays.asList(Rola.STUDENT, Rola.NAUCZYCIEL));
	}

    public ListStore<Plec> getStorePlec() {
        return storePlec;
    }

    public PlecProperties getPlecProp() {
        return plecProp;
    }

    public ListStore<Rola> getStoreRola() {
        return storeRola;
    }

    public RolaProperties getRolaProp() {
        return rolaProp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
