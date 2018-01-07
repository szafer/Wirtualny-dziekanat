package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.StatusWnioskuProperties;
import pl.edu.us.client.accesproperties.TypWnioskuProperties;
import pl.edu.us.client.accesproperties.UWniosekProperties;
import pl.edu.us.client.accesproperties.WniosekProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.enums.TypWniosku;

@Singleton
public class MojeWnioskiModel {

    private ListStore<WniosekDTO> storeWzory;
    private ListStore<UWniosekDTO> storeWnioskiUzytkownika;
    private ListStore<TypWniosku> storeTypWniosku;
    private ListStore<StatusWniosku> storeStatusWniosku;

    private UWniosekDTO wniosek;
    private UserDTO user;
    UWniosekProperties wnioskiUzProp = GWT.create(UWniosekProperties.class);
    WniosekProperties wzoryProp = GWT.create(WniosekProperties.class);
    TypWnioskuProperties typWnioskuProp = GWT.create(TypWnioskuProperties.class);
    StatusWnioskuProperties statusProp = GWT.create(StatusWnioskuProperties.class);

    @Inject
    public MojeWnioskiModel() {
        storeWzory = new ListStore<WniosekDTO>(wzoryProp.key());
        storeTypWniosku = new ListStore<TypWniosku>(typWnioskuProp.kod());
        storeTypWniosku.addAll(Arrays.asList(TypWniosku.values()));
        storeWnioskiUzytkownika = new ListStore<UWniosekDTO>(wnioskiUzProp.key());
        storeStatusWniosku = new ListStore<StatusWniosku>(statusProp.kod());
        storeStatusWniosku.addAll(Arrays.asList(StatusWniosku.values()));
    }

    public ListStore<StatusWniosku> getStoreStatusWniosku() {
        return storeStatusWniosku;
    }

    public ListStore<WniosekDTO> getStoreWzory() {
        return storeWzory;
    }

    public ListStore<UWniosekDTO> getStoreWnioskiUzytkownika() {
        return storeWnioskiUzytkownika;
    }

    public UWniosekProperties getWnioskiUzProp() {
        return wnioskiUzProp;
    }

    public ListStore<TypWniosku> getStoreTypWniosku() {
        return storeTypWniosku;
    }

    public void wyczysc() {
        storeWzory.clear();
    }

    public UWniosekDTO getWniosek() {
        return wniosek;
    }

    public void setWniosek(UWniosekDTO wniosek) {
        this.wniosek = wniosek;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
        if (user != null && user.getWnioskiUzytkownika() != null) {
            storeWnioskiUzytkownika.addAll(user.getWnioskiUzytkownika());
        }
    }

    public WniosekProperties getWzoryProp() {
        return wzoryProp;
    }
}
