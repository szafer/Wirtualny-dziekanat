package pl.edu.us.client.wnioski.kartoteka;

import java.util.ArrayList;
import java.util.Collection;
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
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.wnioski.WnioskiService;
import pl.edu.us.shared.services.wnioski.WnioskiServiceAsync;

public class WnioskiKartPresenter extends BasePresenter<WnioskiKartPresenter.MyView, WnioskiKartPresenter.MyProxy>
    implements WnioskiKartUiHandlers {

    public interface MyView extends View, HasUiHandlers<WnioskiKartUiHandlers> {
        WnioskiKartModel getModel();

        WnioskiKartMainPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.wnioskiKart)
    public interface MyProxy extends ProxyPlace<WnioskiKartPresenter> {
    }

    private final RpcMasking rpcMasking;
    private final WnioskiServiceAsync wnioskiService = GWT.create(WnioskiService.class);

    @Inject
    public WnioskiKartPresenter(EventBus eventBus, MyView view, MyProxy proxy, final RpcMasking rpcMasking) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
        this.rpcMasking = rpcMasking;
        this.rpcMasking.setMaskedComponent((Component) getView().asWidget());
    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        pobierzWnioski();
    }

    private void pobierzWnioski() {
        wnioskiService.pobierzWnioskiStudentow(rpcMasking.call(Message.LOADING, new AsyncCallback<List<UWniosekDTO>>() {
            @Override
            public void onSuccess(List<UWniosekDTO> result) {
                if (result != null)
                    getView().getModel().getStoreWnioski().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                new AlertMessageBox("Wnioski", "Błąd pobierania danych: <br>" + caught.getMessage()).show();
            }
        }));

    }

    @Override
    public void wykonajZapisz() {
        List<UWniosekDTO> doZapisu = new ArrayList<UWniosekDTO>();
        Collection<Store<UWniosekDTO>.Record> lista = getView().getModel().getStoreWnioski().getModifiedRecords();
        for (Record r : lista) {
            r.commit(false);
            doZapisu.add((UWniosekDTO) r.getModel());
        }
        wnioskiService.zapiszWnoiski(doZapisu, rpcMasking.call(Message.SAVING,
            new ActionCallback<List<UWniosekDTO>>() {

                @Override
                public void onSuccess(List<UWniosekDTO> result) {
                    Info.display("Kartoteka wniosków", "Zapisano dane");
                    getView().getModel().wyczysc();
                    getView().getPanel().initialState();
                    getView().getModel().getStoreWnioski().addAll(result);
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Kartoteka wniosków", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
                }
            }));

    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreWnioski().rejectChanges();
        getView().getPanel().initialState();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }

    @Override
    public void pobierzPodgladWniosku(byte[] zlozonyWniosek) {
        // TODO Auto-generated method stub

    }
}
