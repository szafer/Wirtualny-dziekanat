package pl.edu.us.client.wiadomosci;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;

import pl.edu.us.client.accesproperties.NadawcaProperties;
import pl.edu.us.client.accesproperties.OdbiorcaProperties;
import pl.edu.us.client.accesproperties.StatusWnioskuProperties;
import pl.edu.us.client.accesproperties.TypWnioskuProperties;
import pl.edu.us.client.accesproperties.UWniosekProperties;
import pl.edu.us.client.accesproperties.WniosekProperties;
import pl.edu.us.client.main.grid.WiadomoscKeyProvider;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wiadomosci.BaseDto;
import pl.edu.us.shared.dto.wiadomosci.FolderDto;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.enums.TypWniosku;

@Singleton
public class WiadomosciModel {

    private TreeStore<BaseDto> storeTypySkrzynek;
    private UWniosekDTO wniosek;
    private UserDTO user;

    private ListStore<OdbiorcaDTO> storeOdebrane;
//    private ListStore<OdbiorcaDTO> storeNowe;
    private ListStore<NadawcaDTO> storeWyslane;

    FolderDto root = new FolderDto(1, "Wiadomości");
//    FolderDto nowe = new FolderDto(2, "Nowe");
    FolderDto wyslane = new FolderDto(3, "Wysłane");
    FolderDto odebrane = new FolderDto(4, "Odebrane");

    OdbiorcaProperties odbProp = GWT.create(OdbiorcaProperties.class);
    NadawcaProperties nadProp = GWT.create(NadawcaProperties.class);

    @Inject
    public WiadomosciModel() {

        storeOdebrane = new ListStore<OdbiorcaDTO>(odbProp.key());
//        storeNowe = new ListStore<OdbiorcaDTO>(odbProp.key());
        storeWyslane = new ListStore<NadawcaDTO>(nadProp.key());

        storeTypySkrzynek = new TreeStore<BaseDto>(new WiadomoscKeyProvider());
//        root.addChild(nowe);
        root.addChild(odebrane);
        root.addChild(wyslane);
        storeTypySkrzynek.add(root);
        for (BaseDto child : root.getChildren()) {
            storeTypySkrzynek.add(root, child);
            if (child instanceof FolderDto) {
                processFolder(storeTypySkrzynek, (FolderDto) child);
            }
        }
    }

    public void wyczysc() {
//        storeNowe.clear();
        storeOdebrane.clear();
        storeWyslane.clear();
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
        }
    }

    public TreeStore<BaseDto> getStoreTypySkrzynek() {
        return storeTypySkrzynek;
    }

    private void processFolder(TreeStore<BaseDto> store, FolderDto folder) {
        for (BaseDto child : folder.getChildren()) {
            store.add(folder, child);
            if (child instanceof FolderDto) {
                processFolder(store, (FolderDto) child);
            }
        }
    }

//    public ListStore<OdbiorcaDTO> getStoreNowe() {
//        return storeNowe;
//    }

    public ListStore<OdbiorcaDTO> getStoreOdebrane() {
        return storeOdebrane;
    }

    public ListStore<NadawcaDTO> getStoreWyslane() {
        return storeWyslane;
    }

    public OdbiorcaProperties getOdbProp() {
        return odbProp;
    }

    public NadawcaProperties getNadProp() {
        return nadProp;
    }

    public void ladujDane(UserMessagesDTO result) {
        wyslane.setWyslane(result.getNadane());
        odebrane.setOdebrane(result.getOdebrane());
//        nowe.setNowe(result.getNowe());
    }
}
