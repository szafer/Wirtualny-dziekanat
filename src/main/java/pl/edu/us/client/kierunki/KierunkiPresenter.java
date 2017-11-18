package pl.edu.us.client.kierunki;

import java.util.ArrayList;
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

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.shared.model.Kierunek;
import pl.edu.us.shared.services.kierunki.KierunekService;
import pl.edu.us.shared.services.kierunki.KierunekServiceAsync;

public class KierunkiPresenter extends BasePresenter<KierunkiPresenter.MyView, KierunkiPresenter.MyProxy> implements
    KierunkiUiHandlers {

    public interface MyView extends View, HasUiHandlers<KierunkiUiHandlers> {

        KierunkiModel getModel();

        KierunkiPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.kierunki)
    public interface MyProxy extends ProxyPlace<KierunkiPresenter> {
    }

    private final KierunekServiceAsync service = GWT.create(KierunekService.class);

    @Inject
    public KierunkiPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);

    }

    @Override
    protected void onReset() {
        // TODO Auto-generated method stub
        super.onReset();

        pobierzKierunki();
    }

    private void pobierzKierunki() {
        getView().getModel().getStoreKierunki().clear();
        getView().getModel().getDoUsuniecia().clear();
        service.getKierunki(new AsyncCallback<List<Kierunek>>() {
            @Override
            public void onSuccess(List<Kierunek> result) {
                if (result != null)
                    getView().getModel().getStoreKierunki().addAll(result);
                // getView().getPanel().getGridPanel().getGrid().getSelectionModel().select(0,
                // true);
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
        List<Kierunek> doZapisu = new ArrayList<Kierunek>();
        List<Kierunek> doUsuniecia = new ArrayList<Kierunek>();
        doZapisu.addAll(getView().getModel().getStoreKierunki().getAll());
        doUsuniecia.addAll(getView().getModel().getDoUsuniecia().getAll());
        service.zapisz(doZapisu, doUsuniecia, new AsyncCallback<List<Kierunek>>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Kierunek> result) {
                pobierzKierunki();
            }
        });
    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreKierunki().rejectChanges();
        getView().getModel().getDoUsuniecia().rejectChanges();

    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
