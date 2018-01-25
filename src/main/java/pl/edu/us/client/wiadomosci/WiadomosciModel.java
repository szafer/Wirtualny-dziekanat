package pl.edu.us.client.wiadomosci;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;

import pl.edu.us.client.accesproperties.NadawcaProperties;
import pl.edu.us.client.accesproperties.OdbiorcaProperties;
import pl.edu.us.client.accesproperties.UserProperties;
import pl.edu.us.client.main.grid.WiadomoscKeyProvider;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wiadomosci.BaseDto;
import pl.edu.us.shared.dto.wiadomosci.FolderDto;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;

@Singleton
public class WiadomosciModel {

    private TreeStore<BaseDto> storeTypySkrzynek;
    private UWniosekDTO wniosek;
    private UserDTO user;
    private ListStore<UserDTO> storeOdbiorcy;

    private ListStore<OdbiorcaDTO> storeOdebrane;
//    private ListStore<OdbiorcaDTO> storeNowe;
    private ListStore<NadawcaDTO> storeWyslane;
    private NadawcaDTO nowaWiadomosc;
    FolderDto root = new FolderDto(1, "Wiadomości");
    FolderDto wyslane = new FolderDto(3, "Wysłane");
    FolderDto odebrane = new FolderDto(4, "Odebrane");

    OdbiorcaProperties odbProp = GWT.create(OdbiorcaProperties.class);
    NadawcaProperties nadProp = GWT.create(NadawcaProperties.class);
    UserProperties uProp = GWT.create(UserProperties.class);

    @Inject
    public WiadomosciModel() {
        storeOdbiorcy = new ListStore<>(uProp.key());
        storeOdebrane = new ListStore<OdbiorcaDTO>(odbProp.key());
        storeWyslane = new ListStore<NadawcaDTO>(nadProp.key());

        storeTypySkrzynek = new TreeStore<BaseDto>(new WiadomoscKeyProvider());

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
        storeOdebrane.clear();
        storeWyslane.clear();
        storeOdbiorcy.clear();
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

    public void ladujDane(UserMessagesDTO result, Boolean tylkoNowe) {
        if (!tylkoNowe) {
            wyslane.setWyslane(result.getNadane());
            odebrane.setOdebrane(result.getOdebrane());
        }
        for (OdbiorcaDTO odb : result.getNowe()) {
            if (!odebrane.getNowe().contains(odb)){
                odebrane.getNowe().add(odb);
                storeOdebrane.add(odb);
            }
                
        }
        storeTypySkrzynek.update(wyslane);
        storeTypySkrzynek.update(odebrane);
        storeTypySkrzynek.update(root);

    }

    public ListStore<UserDTO> getStoreOdbiorcy() {
        return storeOdbiorcy;
    }

    public UserProperties getuProp() {
        return uProp;
    }

    public NadawcaDTO getNowaWiadomosc() {
        return nowaWiadomosc;
    }

    public void setNowaWiadomosc(NadawcaDTO nowaWiadomosc) {
        this.nowaWiadomosc = nowaWiadomosc;
    }

    public FolderDto getRoot() {
        return root;
    }
}
