package pl.edu.us.client.uzytkownik.kartoteka;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.PlecProperties;
import pl.edu.us.client.accesproperties.RolaProperties;
import pl.edu.us.client.accesproperties.UserProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;

@Singleton
public class UzytkownicyModel {

    private final ListStore<Plec> storePlec;
    private final ListStore<Rola> storeRola;

    private ListStore<UserDTO> users;
    private UserDTO selected;

    PlecProperties plecProp = GWT.create(PlecProperties.class);
    RolaProperties rolaProp = GWT.create(RolaProperties.class);
    UserProperties userProp = GWT.create(UserProperties.class);

    @Inject
    public UzytkownicyModel() {
        storePlec = new ListStore<Plec>(plecProp.kod());
        storePlec.addAll(Arrays.asList(Plec.values()));
        storeRola = new ListStore<Rola>(rolaProp.kod());
        storeRola.addAll(Arrays.asList(Rola.values()));
        users = new ListStore<UserDTO>(userProp.key());
    }

    public void wyczysc() {
        users.clear();
    }

    public ListStore<Rola> getStoreRola() {
        return storeRola;
    }

    public ListStore<Plec> getStorePlec() {
        return storePlec;
    }

    public PlecProperties getPlecProp() {
        return plecProp;
    }

    public ListStore<UserDTO> getStoreUsers() {
        return users;
    }

    public UserDTO getSelected() {
        return selected;
    }

    public void setSelected(UserDTO selected) {
        this.selected = selected;
        if (selected != null) {
        }
    }

    public UserProperties getUserProp() {
        return userProp;
    }
}
