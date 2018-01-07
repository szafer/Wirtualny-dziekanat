package pl.edu.us.client.przedmioty;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.PrzedmiotProperties;
import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.client.accesproperties.UserProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Rola;

@Singleton
public class PrzedmiotyModel {

    private ListStore<PrzedmiotDTO> storePrzedmioty;
//    private ListStore<PrzedmiotDTO> przedmiotyDoUsuniecia;
    private ListStore<UPrzedmiotDTO> storeStudenci;
    private ListStore<UPrzedmiotDTO> storeNauczyciele;
    private ListStore<UserDTO> storeUsersNauczyciele;
    private ListStore<UserDTO> storeUsersStudenci;

    private PrzedmiotDTO przedmiot;
    PrzedmiotProperties przedmiotProp = GWT.create(PrzedmiotProperties.class);
    UPrzedmiotProperties uPrzedmiotProp = GWT.create(UPrzedmiotProperties.class);
    UserProperties userProperties = GWT.create(UserProperties.class);

    @Inject
    public PrzedmiotyModel() {
        storePrzedmioty = new ListStore<PrzedmiotDTO>(przedmiotProp.key());
//        przedmiotyDoUsuniecia = new ListStore<PrzedmiotDTO>(przedmiotProp.key());
        storeStudenci = new ListStore<UPrzedmiotDTO>(uPrzedmiotProp.key());
        storeNauczyciele = new ListStore<UPrzedmiotDTO>(uPrzedmiotProp.key());
        storeUsersNauczyciele = new ListStore<UserDTO>(userProperties.key());
        storeUsersStudenci = new ListStore<UserDTO>(userProperties.key());
    }

    public UPrzedmiotProperties getuPrzedmiotProp() {
        return uPrzedmiotProp;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public PrzedmiotProperties getPrzedmiotProp() {
        return przedmiotProp;
    }

//    public ListStore<PrzedmiotDTO> getPrzedmiotyDoUsuniecia() {
//        return przedmiotyDoUsuniecia;
//    }

    public ListStore<PrzedmiotDTO> getStorePrzedmioty() {
        return storePrzedmioty;
    }

    public ListStore<UPrzedmiotDTO> getStoreNauczyciele() {
        return storeNauczyciele;
    }

    public ListStore<UPrzedmiotDTO> getStoreStudenci() {
        return storeStudenci;
    }

    public ListStore<UserDTO> getStoreUsersStudenci() {
        return storeUsersStudenci;
    }

    public ListStore<UserDTO> getStoreUsersNauczyciele() {
        return storeUsersNauczyciele;
    }

    public void wyczysc() {
        storeStudenci.clear();
        storeNauczyciele.clear();
        storePrzedmioty.clear();
        przedmiot = null;
    }

    public PrzedmiotDTO getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(PrzedmiotDTO przedmiot) {
        this.przedmiot = przedmiot;
        storeNauczyciele.clear();
        storeStudenci.clear();
        if (przedmiot.getWykladowca() != null)
            storeNauczyciele.add(przedmiot.getWykladowca());
        if (przedmiot.getStudenci() != null && !przedmiot.getStudenci().isEmpty()) {
            storeStudenci.addAll(przedmiot.getStudenci());
        }
    }

    public boolean isDirty() {
        return !storePrzedmioty.getModifiedRecords().isEmpty()
            || !storeNauczyciele.getModifiedRecords().isEmpty()
            || !storeStudenci.getModifiedRecords().isEmpty();
    }

    public void loadUsers(List<UserDTO> result) {
        List<UserDTO> studenci = new ArrayList<>(result.size());
        List<UserDTO> nauczyciele = new ArrayList<>(result.size());
        for (UserDTO u : result) {
            if (u.getRola() == Rola.STUDENT)
                studenci.add(u);
            else if (u.getRola() == Rola.NAUCZYCIEL)
                nauczyciele.add(u);
        }
        storeUsersNauczyciele.addAll(nauczyciele);
        storeUsersStudenci.addAll(studenci);
    }
}
