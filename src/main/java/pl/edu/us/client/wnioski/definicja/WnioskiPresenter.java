package pl.edu.us.client.wnioski.definicja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.wnioski.WnioskiService;
import pl.edu.us.shared.services.wnioski.WnioskiServiceAsync;

public class WnioskiPresenter extends BasePresenter<WnioskiPresenter.MyView, WnioskiPresenter.MyProxy>
    implements WnioskiUiHandlers {

    public interface MyView extends View, HasUiHandlers<WnioskiUiHandlers> {
        WnioskiMainPanel getPanel();

        WnioskiModel getModel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.wnioski)
    public interface MyProxy extends ProxyPlace<WnioskiPresenter> {
    }

    private final RpcMasking rpcMasking;
    private final WnioskiServiceAsync wnioskiService = GWT.create(WnioskiService.class);

    @Inject
    public WnioskiPresenter(EventBus eventBus, MyView view, MyProxy proxy, final RpcMasking rpcMasking) {
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
        getView().getModel().wyczysc();
        wnioskiService.getWnioski(rpcMasking.call(Message.LOADING,
            new ActionCallback<List<WniosekDTO>>() {

                @Override
                public void onSuccess(List<WniosekDTO> result) {
                    getView().getModel().getStoreWnioski().addAll(result);
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Wnioski", "Błąd pobierania danych").show();
                }
            }));

    }

    @Override
    public void wykonajZapisz() {
        List<WniosekDTO> doZapisu = new ArrayList<WniosekDTO>();
        Collection<Store<WniosekDTO>.Record> lista = getView().getModel().getStoreWnioski().getModifiedRecords();
        for (Record r : lista) {
            r.commit(false);
            doZapisu.add((WniosekDTO) r.getModel());
        }
        wnioskiService.zapisz(doZapisu, rpcMasking.call(Message.SAVING,
            new ActionCallback<List<WniosekDTO>>() {

                @Override
                public void onSuccess(List<WniosekDTO> result) {
                    getView().getPanel().setSaveEnabled(true);
                    Info.display("Zapisano", "Zapisano dane");
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Wnioski", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
                }
            }));
    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreWnioski().rejectChanges();
        pobierzWnioski();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
