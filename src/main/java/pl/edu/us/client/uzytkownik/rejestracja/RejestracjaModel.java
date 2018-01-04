package pl.edu.us.client.uzytkownik.rejestracja;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.PlecProperties;
import pl.edu.us.client.accesproperties.RolaProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;

@Singleton
public class RejestracjaModel {

    private final ListStore<Plec> storePlec;
    private final ListStore<Rola> storeRola;
    private UserDTO user;

    PlecProperties plecProp = GWT.create(PlecProperties.class);
    RolaProperties rolaProp = GWT.create(RolaProperties.class);

    @Inject
    public RejestracjaModel() {
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
