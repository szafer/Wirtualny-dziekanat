package pl.edu.us.client.uzytkownik.kartoteka;

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
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class UzytkownicyPresenter extends
    BasePresenter<UzytkownicyPresenter.MyView, UzytkownicyPresenter.MyProxy> implements
    UzytkownicyUiHandlers {

    public interface MyView extends View, HasUiHandlers<UzytkownicyUiHandlers> {

        UzytkownicyModel getModel();

        UzytkownicyMainPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.kartotekaUzytkownikow)
    public interface MyProxy extends ProxyPlace<UzytkownicyPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final RpcMasking rpcMasking;

    @Inject
    public UzytkownicyPresenter(EventBus eventBus, MyView view, MyProxy proxy, final RpcMasking rpcMasking) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
        this.rpcMasking = rpcMasking;
        this.rpcMasking.setMaskedComponent((Component) getView().asWidget());
    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        getView().getPanel().getPanel().getGrid().getLoader().load();
//        pobierzUzytkownikow();
    }

/*
 * Metoda zastąpiona loaderem z maskowaniem
 */
    private void pobierzUzytkownikow() {
        userService.getUsers(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                if (result != null)
                    getView().getModel().getStoreUsers().addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                System.out.println("Nie udało sie pobrać studentów");
            }
        });

    }

    @Override
    public void wykonajZapisz() {
        List<UserDTO> doZapisu = new ArrayList<UserDTO>();
        Collection<Store<UserDTO>.Record> lista = getView().getModel().getStoreUsers().getModifiedRecords();
        for (Record r : lista) {
            r.commit(false);
            doZapisu.add((UserDTO) r.getModel());
        }
        userService.zapisz(doZapisu, rpcMasking.call(Message.SAVING,
            new ActionCallback<List<UserDTO>>() {

                @Override
                public void onSuccess(List<UserDTO> result) {
                    getView().getPanel().setSaveEnabled(true);
                    Info.display("Użytkownicy", "Zapisano dane");
                    getView().getPanel().getPanel().getGrid().getLoader().load();
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Użytkownicy", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
                }
            }));

    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreUsers().rejectChanges();
        getView().getPanel().getPanel().getGrid().getLoader().load();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }

    @Override
    public void getUsers(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UserDTO>> callback) {
        userService.getUsers(loadConfig, rpcMasking.call(Message.LOADING, callback));
    }
}
