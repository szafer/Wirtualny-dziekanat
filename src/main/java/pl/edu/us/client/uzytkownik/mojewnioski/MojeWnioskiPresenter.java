package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;
import pl.edu.us.shared.services.wnioski.WnioskiService;
import pl.edu.us.shared.services.wnioski.WnioskiServiceAsync;

public class MojeWnioskiPresenter extends BasePresenter<MojeWnioskiPresenter.MyView, MojeWnioskiPresenter.MyProxy> implements
    MojeWnioskiUiHandlers {

    public interface MyView extends View, HasUiHandlers<MojeWnioskiUiHandlers> {
        MojeWnioskiModel getModel();

        MojeWnioskiMainPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.mojeWnioski)
    public interface MyProxy extends ProxyPlace<MojeWnioskiPresenter> {
    }

    private final RpcMasking rpcMasking;
    private final WnioskiServiceAsync wnioskiService = GWT.create(WnioskiService.class);
    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public MojeWnioskiPresenter(EventBus eventBus, MyView view, MyProxy proxy, final RpcMasking rpcMasking) {
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
                    getView().getModel().getStoreWzory().addAll(result);
                    pobierzDaneUzytkownika();
                }

                @Override
                public void onFailure(Throwable caught) {
                    new AlertMessageBox("Wnioski", "Błąd pobierania danych: <br>" + caught.getMessage()).show();
                }
            }));

    }

    private void pobierzDaneUzytkownika() {
        getView().getModel().getStoreWnioskiUzytkownika().clear();
        userService.pobierzDaneUzytkownika(Cookies.getCookie("loggedUser"), rpcMasking.call(Message.SAVING, new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                getView().getModel().setUser(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getModel().setUser(null);
                new AlertMessageBox("Wnioski", "Błąd pobierania danych: <br>" + caught.getMessage()).show();
            }
        }));
    }

    @Override
    public void wykonajZapisz() {
        UserDTO user = getView().getModel().getUser();
        List<UWniosekDTO> zmiany = new ArrayList<UWniosekDTO>();
        Collection<Store<UWniosekDTO>.Record> wykladowca = getView().getModel().getStoreWnioskiUzytkownika().getModifiedRecords();
        for (Record r : wykladowca) {
            r.commit(false);
            UWniosekDTO wyk = (UWniosekDTO) r.getModel();
            zmiany.add((UWniosekDTO) r.getModel());
        }
        if (user.getWnioskiUzytkownika() != null) {
            user.getWnioskiUzytkownika().addAll(zmiany);
        } else
            user.setWnioskiUzytkownika(zmiany);

        userService.updateUser(user, rpcMasking.call(Message.SAVING, new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                Info.display("Info", "Zapisano wnioski");
                pobierzDaneUzytkownika();
                getView().getPanel().initialState();
            }

            @Override
            public void onFailure(Throwable caught) {
                new AlertMessageBox("Użytkownicy", "Błąd zapisywania danych: <br>" + caught.getMessage()).show();
            }
        }));
    }

    @Override
    public void wykonajAnuluj() {
        getView().getModel().getStoreWnioskiUzytkownika().rejectChanges();
        getView().getPanel().getWniosekPanel().clear();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
