package pl.edu.us.client.uzytkownik.daneuzytkownika;

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
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class DaneUzytkownikaPresenter extends
    BasePresenter<DaneUzytkownikaPresenter.MyView, DaneUzytkownikaPresenter.MyProxy> implements
    DaneUzytkownkaUiHandlers {

    public interface MyView extends View, HasUiHandlers<DaneUzytkownkaUiHandlers> {

        DaneUzytkownikaModel getModel();

        DaneUzytkownikaPanel getPanel();

        UserDTO dajUsera();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.daneUzytkownika)
    public interface MyProxy extends ProxyPlace<DaneUzytkownikaPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public DaneUzytkownikaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
                getView().getPanel().bind();
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getModel().setUser(null);
                getView().getPanel().bind();
                alertMessage("Nie udało się pobrać danych");
            }
        });
    }

    private native void alertMessage(String message) /*-{
		//    $wnd.close();
		$wnd.alert(message);
		//      $wnd.location = "Logout.html";
    }-*/;

    @Override
    public void wykonajZapisz() {
        userService.updateUser(getView().dajUsera(), new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                Info.display("Info", "Zmieniono dane użytkownika");
                getView().getPanel().initialState();
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getModel().setUser(null);
                getView().getPanel().bind();
                alertMessage("Nie udało się zaktualizować danych");
            }
        });

    }

    @Override
    public void wykonajAnuluj() {
        pobierzDaneUzytkownika();

    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
