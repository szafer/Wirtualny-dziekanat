package pl.edu.us.client.uzytkownik.mojeprzedmioty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.model.old.Kierunek;
import pl.edu.us.shared.services.PracownikServiceAsync;
import pl.edu.us.shared.services.przedmioty.PrzedmiotyService;
import pl.edu.us.shared.services.przedmioty.PrzedmiotyServiceAsync;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class MojePrzedmiotyPresenter extends
    BasePresenter<MojePrzedmiotyPresenter.MyView, MojePrzedmiotyPresenter.MyProxy> implements
    MojePrzedmiotyUiHandlers {

    public interface MyView extends View, HasUiHandlers<MojePrzedmiotyUiHandlers> {
        MojePrzedmiotyModel getModel();

        MojePrzedmiotyMainPanel getPanel();

        UserDTO dajUsera();

    }

    @ProxyCodeSplit
    @NameToken(NameTokens.mojePrzedmioty)
    public interface MyProxy extends ProxyPlace<MojePrzedmiotyPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final PrzedmiotyServiceAsync przedmiotyService = GWT.create(PrzedmiotyService.class);

    @Inject
    public MojePrzedmiotyPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);

    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        pobierzDaneUzytkownika();
    }

    private void pobierzDaneUzytkownika() {
        userService.pobierzDaneUzytkownika(Cookies.getCookie("loggedUser"), new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                getView().getModel().setUser(result);
//                getView().getPanel().bind();
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getModel().setUser(null);
//                getView().getPanel().bind();
//                alertMessage("Nie udało się pobrać danych");
            }
        });
    }

    @Override
    public void wykonajZapisz() {
        Collection<Store<UPrzedmiotDTO>.Record> lista = getView().getModel().getStoreOcenyStudentow().getModifiedRecords();
        List<UPrzedmiotDTO> doZapisu = new ArrayList<UPrzedmiotDTO>();
        for (Record r : lista) {
            r.commit(false);
            doZapisu.add((UPrzedmiotDTO) r.getModel());
        }
        przedmiotyService.zapisz(doZapisu, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println(caught.getLocalizedMessage());
                Info.display("Info", "Błąd zapisywania ocen");
                getView().getPanel().initialState();
                pobierzStudentow();
            }

            @Override
            public void onSuccess(Void result) {
                Info.display("Info", "Zapisano oceny");
                getView().getPanel().initialState();
                pobierzStudentow();
            }
        });
    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreOcenyStudentow().rejectChanges();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }

    @Override
    public void pobierzStudentow() {
        getView().getModel().getStoreOcenyStudentow().clear();
        przedmiotyService.getPrzedmiotyStudentow(getView().getModel().getPrzedmiot().getId(), new AsyncCallback<List<UPrzedmiotDTO>>() {
            @Override
            public void onSuccess(List<UPrzedmiotDTO> result) {
                if (result != null)
                    getView().getModel().getStoreOcenyStudentow().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                System.out.println("fail");
            }
        });
    }
}
