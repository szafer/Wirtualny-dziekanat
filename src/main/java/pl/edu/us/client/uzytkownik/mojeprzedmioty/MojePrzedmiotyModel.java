package pl.edu.us.client.uzytkownik.mojeprzedmioty;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.ListStore;

import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Semestr;

@Singleton
public class MojePrzedmiotyModel {

    private UserDTO user;
    private PrzedmiotDTO przedmiot;
    private ListStore<UPrzedmiotDTO> storePrzedmiotyUsera;
    private ListStore<UPrzedmiotDTO> storeOcenyStudentow;

    UPrzedmiotProperties uPrzedmiotProp = GWT.create(UPrzedmiotProperties.class);

    @Inject
    public MojePrzedmiotyModel() {
        storePrzedmiotyUsera = new ListStore<UPrzedmiotDTO>(uPrzedmiotProp.key());
        storeOcenyStudentow = new ListStore<UPrzedmiotDTO>(uPrzedmiotProp.key());
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
        if (user != null) {
            if (user.getPrzedmiotyUzytkownika() != null && !user.getPrzedmiotyUzytkownika().isEmpty()) {
                for (UPrzedmiotDTO u : user.getPrzedmiotyUzytkownika()) {
                    u.setSemestr(u.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
                }
            }
            storePrzedmiotyUsera.addAll(user.getPrzedmiotyUzytkownika());
        } else
            storePrzedmiotyUsera.clear();
    }

    public ListStore<UPrzedmiotDTO> getStorePrzedmiotyUsera() {
        return storePrzedmiotyUsera;
    }

    public UPrzedmiotProperties getUPrzedmiotProp() {
        return uPrzedmiotProp;
    }

    public ListStore<UPrzedmiotDTO> getStoreOcenyStudentow() {
        return storeOcenyStudentow;
    }

    public void wyczysc() {
        storePrzedmiotyUsera.clear();
        storeOcenyStudentow.clear();
    }

    public PrzedmiotDTO getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(PrzedmiotDTO przedmiot) {
        this.przedmiot = przedmiot;
    }
}
