package pl.edu.us.client.symulacja.przychodykosztykierunki;

import java.util.List;

import com.google.gwt.core.client.GWT;
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
import pl.edu.us.shared.model.old.ViPrzychod;
import pl.edu.us.shared.services.przychodykoszty.PrzychodyKosztyService;
import pl.edu.us.shared.services.przychodykoszty.PrzychodyKosztyServiceAsync;

public class PKKierunkiPresenter extends
    BasePresenter<PKKierunkiPresenter.MyView, PKKierunkiPresenter.MyProxy> implements
    PKKierunkiUiHandlers {

    public interface MyView extends View, HasUiHandlers<PKKierunkiUiHandlers> {

        PKKierunkiModel getModel();

        void filtrujChart();

        void filtrujStoreRok();

        void filtrujStoreKierunki();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.przychodykosztykierunki)
    public interface MyProxy extends ProxyPlace<PKKierunkiPresenter> {
    }

    private final PrzychodyKosztyServiceAsync service = GWT.create(PrzychodyKosztyService.class);

    @Inject
    public PKKierunkiPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);

    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        pobierzPrzychody();
    }

    private void pobierzPrzychody() {
        service.getPrzychody(new AsyncCallback<List<ViPrzychod>>() {
            @Override
            public void onSuccess(List<ViPrzychod> result) {
                if (result != null)
                    getView().getModel().getStoreAll().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                System.out.println("fail");
            }
        });
        // getView().getModel().getStorePrzychodyAll().addAll(getPrzychody());
    }

    @Override
    public void wykonajZapisz() {
// TODO Auto-generated method stub

    }

    @Override
    public void wykonajAnuluj() {
// TODO Auto-generated method stub

    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();

    }
}
