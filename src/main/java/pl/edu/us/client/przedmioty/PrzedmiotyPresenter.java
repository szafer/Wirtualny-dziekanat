package pl.edu.us.client.przedmioty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.shared.GWT;
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

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.przedmioty.ui.PrzedmiotyMainPanel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.services.przedmioty.PrzedmiotyService;
import pl.edu.us.shared.services.przedmioty.PrzedmiotyServiceAsync;

public class PrzedmiotyPresenter extends BasePresenter<PrzedmiotyPresenter.MyView, PrzedmiotyPresenter.MyProxy> implements
    PrzedmiotyUiHandlers {

    public interface MyView extends View, HasUiHandlers<PrzedmiotyUiHandlers> {

        PrzedmiotyModel getModel();

        PrzedmiotyMainPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.przedmioty)
    public interface MyProxy extends ProxyPlace<PrzedmiotyPresenter> {
    }

    private final PrzedmiotyServiceAsync service = GWT.create(PrzedmiotyService.class);

    @Inject
    public PrzedmiotyPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);

    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        pobierzDane();
        pobierzPrzedmioty();
    }

    private void pobierzDane() {
        getView().getModel().getStoreUzytkownicy().clear();
        service.getWykladowcy(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                if (result != null)
                    getView().getModel().getStoreUzytkownicy().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                System.out.println("fail");
            }
        });

    }

    private void pobierzPrzedmioty() {
        getView().getModel().wyczysc();
        service.getKierunki(new AsyncCallback<List<PrzedmiotDTO>>() {
            @Override
            public void onSuccess(List<PrzedmiotDTO> result) {
                if (result != null)
                    getView().getModel().getStorePrzedmioty().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                System.out.println("fail");
            }
        });

    }

    @Override
    public void wykonajZapisz() {
        List<PrzedmiotDTO> doZapisu = new ArrayList<PrzedmiotDTO>();
        List<PrzedmiotDTO> doUsuniecia = new ArrayList<PrzedmiotDTO>();
        List<UPrzedmiotDTO> zmiany = new ArrayList<UPrzedmiotDTO>();
        Collection<Store<UPrzedmiotDTO>.Record> wykladowca = getView().getModel().getStoreNauczyciele().getModifiedRecords();
        for (Record r : wykladowca) {
            r.commit(false);
            UPrzedmiotDTO wyk = (UPrzedmiotDTO) r.getModel();
            
            zmiany.add((UPrzedmiotDTO) r.getModel());
        }
        Collection<Store<UPrzedmiotDTO>.Record> studenci = getView().getModel().getStoreStudenci().getModifiedRecords();
        for (Record r : studenci) {
            r.commit(false);
            zmiany.add((UPrzedmiotDTO) r.getModel());
        }
        Collection<Store<PrzedmiotDTO>.Record> lista = getView().getModel().getStorePrzedmioty().getModifiedRecords();
        for (Record r : lista) {
            r.commit(false);
            doZapisu.add((PrzedmiotDTO) r.getModel());
        }
        service.zapisz(doZapisu, zmiany, new AsyncCallback<List<PrzedmiotDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<PrzedmiotDTO> result) {
                pobierzPrzedmioty();
            }
        });
    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStorePrzedmioty().rejectChanges();
        getView().getModel().getStoreNauczyciele().rejectChanges();
        getView().getModel().getStoreStudenci().rejectChanges();
        getView().getPanel().initialState();
//        getView().getModel().getPrzedmiotyDoUsuniecia().rejectChanges();

    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}