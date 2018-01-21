package pl.edu.us.client.wiadomosci;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
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

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.AppKontekst;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.MenuPresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.client.wiadomosci.ui.WiadomosciMainPanel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;
import pl.edu.us.shared.services.wiadomosci.WiadomosciService;
import pl.edu.us.shared.services.wiadomosci.WiadomosciServiceAsync;

public class WiadomosciPresenter extends BasePresenter<WiadomosciPresenter.MyView, WiadomosciPresenter.MyProxy> implements
    WiadomosciUiHandlers {

    public interface MyView extends View, HasUiHandlers<WiadomosciUiHandlers> {
        WiadomosciModel getModel();

        WiadomosciMainPanel getPanel();

        void odnotujWyslanie();
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
    private final UserServiceAsync userService = GWT.create(UserService.class);

    private static final int MESSAGE_TIME = 30 * 1000;//1min
    private Timer timer;

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
        pobierzDaneUzytkownika();

        utworzTimer();
    }

    private void utworzTimer() {
        timer = new Timer() {
            @Override
            public void run() {
                pobierzWiadomosci(true);
            }

        };
        timer.scheduleRepeating(MESSAGE_TIME);
    }

    private void pobierzDaneUzytkownika() {

        userService.pobierzDaneUzytkownika(Cookies.getCookie("loggedUser"), rpcMasking.call(Message.SAVING, new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                getView().getModel().setUser(result);
                pobierzUzytkownikow();
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getModel().setUser(null);
                new AlertMessageBox("Wiadomości", "Błąd pobierania wiadomości: <br>" + caught.getMessage()).show();
            }
        }));
    }

    private void pobierzWiadomosci(final Boolean tylkoNowe) {
//        getView().getModel().wyczysc();
        if (!tylkoNowe) {
            wiadomosciService.getWiadomosci(1
//            kontekst.getUser().getId()
                , rpcMasking.call(Message.LOADING,
                    new ActionCallback<UserMessagesDTO>() {

                        @Override
                        public void onSuccess(UserMessagesDTO result) {
                            if (result != null) {
                                menuPresenter.loadMessages(null);//czyści toolbar
                                getView().getModel().ladujDane(result, tylkoNowe);
                            }
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            new AlertMessageBox("Wiadomości", "Błąd pobierania wiadomości: <br>" + caught.getMessage()).show();
                        }
                    }));
        } else {
            wiadomosciService.getWiadomosci(1
//            kontekst.getUser().getId()
                ,
                new ActionCallback<UserMessagesDTO>() {

                    @Override
                    public void onSuccess(UserMessagesDTO result) {
                        if (result != null) {
                            menuPresenter.loadMessages(null);//czyści toolbar
                            getView().getModel().ladujDane(result, tylkoNowe);
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        new AlertMessageBox("Wiadomości", "Błąd pobierania wiadomości: <br>" + caught.getMessage()).show();
                    }
                });
        }
    }

    /*
     * Metoda zastąpiona loaderem z maskowaniem
     */
    private void pobierzUzytkownikow() {
        userService.getUsers(rpcMasking.call(Message.LOADING, new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                if (result != null) {
                    getView().getModel().getStoreOdbiorcy().addAll(result);
                    if (kontekst.getUser() != null)
                        getView().getModel().getStoreOdbiorcy().remove(getView().getModel().getUser());
                }
                pobierzWiadomosci(false);
            }

            @Override
            public void onFailure(Throwable caught) {

            }
        }));

    }

    @Override
    public void wykonajZapisz() {
    }

    @Override
    public void wykonajAnuluj() {
    }

    @Override
    public void wykonajZamknij() {
        kontekst.setLock(false);
        timer.cancel();
        removeFromParentSlot();
    }

    @Override
    public void notifyOdebrano(OdbiorcaDTO dto) {
        wiadomosciService.updateMessage(dto, new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {

            }

            @Override
            public void onFailure(Throwable caught) {
//              new AlertMessageBox("Użytkownicy", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
            }
        });
    }

    @Override
    public void wyslij(NadawcaDTO nowaWiadomosc) {
        wiadomosciService.wyslij(nowaWiadomosc,
            rpcMasking.call(Message.LOADING,
                new ActionCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        getView().odnotujWyslanie();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        new AlertMessageBox("Wiadomości", "Błąd wysyłania wiadomości: <br>" + caught.getMessage()).show();
                    }
                }));
    }
}
