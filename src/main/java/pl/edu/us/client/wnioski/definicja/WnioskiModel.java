package pl.edu.us.client.wnioski.definicja;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.TypWnioskuProperties;
import pl.edu.us.client.accesproperties.WniosekProperties;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.TypWniosku;

@Singleton
public class WnioskiModel {

    private ListStore<WniosekDTO> storeWnioski;
    private ListStore<TypWniosku> storeTypWniosku;
    private WniosekDTO wniosek;
    WniosekProperties wnioskiProp = GWT.create(WniosekProperties.class);
    TypWnioskuProperties typWnioskuProp = GWT.create(TypWnioskuProperties.class);

    @Inject
    public WnioskiModel() {
        storeWnioski = new ListStore<WniosekDTO>(wnioskiProp.key());
        storeTypWniosku = new ListStore<TypWniosku>(typWnioskuProp.kod());
        storeTypWniosku.addAll(Arrays.asList(TypWniosku.values()));
    }

    public ListStore<WniosekDTO> getStoreWnioski() {
        return storeWnioski;
    }

    public WniosekProperties getWnioskiProp() {
        return wnioskiProp;
    }

    public ListStore<TypWniosku> getStoreTypWniosku() {
        return storeTypWniosku;
    }

    public void wyczysc() {
        storeWnioski.clear();
    }

    public WniosekDTO getWniosek() {
        return wniosek;
    }

    public void setWniosek(WniosekDTO wniosek) {
        this.wniosek = wniosek;
    }
}
