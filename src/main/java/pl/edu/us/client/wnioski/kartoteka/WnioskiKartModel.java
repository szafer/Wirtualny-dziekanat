package pl.edu.us.client.wnioski.kartoteka;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.StatusWnioskuProperties;
import pl.edu.us.client.accesproperties.TypWnioskuProperties;
import pl.edu.us.client.accesproperties.UWniosekProperties;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.enums.TypWniosku;

@Singleton
public class WnioskiKartModel {

    private ListStore<UWniosekDTO> storeWnioski;
    private ListStore<TypWniosku> storeTypWniosku;
    private ListStore<StatusWniosku> storeStatusWniosku;
    private UWniosekDTO wniosek;
    UWniosekProperties wnioskiUzProp = GWT.create(UWniosekProperties.class);
    TypWnioskuProperties typWnioskuProp = GWT.create(TypWnioskuProperties.class);
    StatusWnioskuProperties statusProp = GWT.create(StatusWnioskuProperties.class);

    @Inject
    public WnioskiKartModel() {
        storeWnioski = new ListStore<UWniosekDTO>(wnioskiUzProp.key());
        storeTypWniosku = new ListStore<TypWniosku>(typWnioskuProp.kod());
        storeTypWniosku.addAll(Arrays.asList(TypWniosku.values()));
        storeStatusWniosku = new ListStore<StatusWniosku>(statusProp.kod());
        storeStatusWniosku.addAll(Arrays.asList(StatusWniosku.values()));
    }

    public ListStore<UWniosekDTO> getStoreWnioski() {
        return storeWnioski;
    }

    public UWniosekProperties getWnioskiUzProp() {
        return wnioskiUzProp;
    }

    public ListStore<TypWniosku> getStoreTypWniosku() {
        return storeTypWniosku;
    }

    public void wyczysc() {
        storeWnioski.clear();
    }

    public UWniosekDTO getWniosek() {
        return wniosek;
    }

    public void setWniosek(UWniosekDTO wniosek) {
        this.wniosek = wniosek;
    }

    public TypWnioskuProperties getTypWnioskuProp() {
        return typWnioskuProp;
    }

    public StatusWnioskuProperties getStatusProp() {
        return statusProp;
    }

    public ListStore<StatusWniosku> getStoreStatusWniosku() {
        return storeStatusWniosku;
    }
}
