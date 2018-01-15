package pl.edu.us.client.wiadomosci;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.AppKontekst;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.MenuPresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.client.wiadomosci.ui.WiadomosciMainPanel;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;
import pl.edu.us.shared.services.wiadomosci.WiadomosciService;
import pl.edu.us.shared.services.wiadomosci.WiadomosciServiceAsync;
import pl.edu.us.shared.services.wnioski.WnioskiService;
import pl.edu.us.shared.services.wnioski.WnioskiServiceAsync;

public class WiadomosciPresenter extends BasePresenter<WiadomosciPresenter.MyView, WiadomosciPresenter.MyProxy> implements
    WiadomosciUiHandlers {

    public interface MyView extends View, HasUiHandlers<WiadomosciUiHandlers> {
        WiadomosciModel getModel();

        WiadomosciMainPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.wiadomosci)
    public interface MyProxy extends ProxyPlace<WiadomosciPresenter> {
    }

    private AppKontekst kontekst;
    private final MenuPresenter menuPresenter;
    private final RpcMasking rpcMasking;
//    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final WiadomosciServiceAsync wiadomosciService = GWT.create(WiadomosciService.class);

    @Inject
    public WiadomosciPresenter(EventBus eventBus, MyView view, MyProxy proxy, final RpcMasking rpcMasking, AppKontekst kontekst,
        final MenuPresenter menuPesenter) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
        this.rpcMasking = rpcMasking;
        this.rpcMasking.setMaskedComponent((Component) getView().asWidget());
        this.kontekst = kontekst;
        this.menuPresenter = menuPesenter;
        kontekst.setLock(true);
    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
        pobierzWiadomosci();
    }

    private void pobierzWiadomosci() {

        getView().getModel().wyczysc();
        wiadomosciService.getWiadomosci(kontekst.getUser().getId(), rpcMasking.call(Message.LOADING,
            new ActionCallback<UserMessagesDTO>() {

                @Override
                public void onSuccess(UserMessagesDTO result) {
                    if (result != null) {
                        menuPresenter.loadMessages(null);//czyści toolbar
                        getView().getModel().ladujDane(result);
                    }
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Wnioski", "Błąd pobierania danych: <br>" + caught.getMessage()).show();
                }
            }));

    }

    @Override
    public void wykonajZapisz() {
//        UserDTO user = getView().getModel().getUser();
//        List<UWniosekDTO> zmiany = new ArrayList<UWniosekDTO>();
//        Collection<Store<UWniosekDTO>.Record> wykladowca = getView().getModel().getStoreWnioskiUzytkownika().getModifiedRecords();
//        for (Record r : wykladowca) {
//            r.commit(false);
//            UWniosekDTO wyk = (UWniosekDTO) r.getModel();
//            zmiany.add((UWniosekDTO) r.getModel());
//        }
//        if (user.getWnioskiUzytkownika() != null) {
//            user.getWnioskiUzytkownika().addAll(zmiany);
//        } else
//            user.setWnioskiUzytkownika(zmiany);
//
//        userService.updateUser(user, rpcMasking.call(Message.SAVING, new AsyncCallback<UserDTO>() {
//
//            @Override
//            public void onSuccess(UserDTO result) {
//                Info.display("Info", "Zapisano wnioski");
//                pobierzDaneUzytkownika();
//                getView().getPanel().initialState();
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//                new AlertMessageBox("Użytkownicy", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
//            }
//        }));
    }

    @Override
    public void wykonajAnuluj() {
//        getView().getModel().getStoreWnioskiUzytkownika().rejectChanges();
//        getView().getPanel().getWniosekPanel().clear();
    }

    @Override
    public void wykonajZamknij() {
        kontekst.setLock(false);
        removeFromParentSlot();
    }

    @Override
    public void notifyOdebrano(OdbiorcaDTO dto) {
        wiadomosciService.updateMessage(dto, new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
//              Info.display("Info", "Zapisano wnioski");
//              pobierzDaneUzytkownika();
//              getView().getPanel().initialState();
            }

            @Override
            public void onFailure(Throwable caught) {
//              new AlertMessageBox("Użytkownicy", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
            }
        });
    }
}
