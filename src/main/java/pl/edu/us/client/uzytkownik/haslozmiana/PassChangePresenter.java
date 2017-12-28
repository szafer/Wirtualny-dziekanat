package pl.edu.us.client.uzytkownik.haslozmiana;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import pl.edu.us.client.NameTokens;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class PassChangePresenter extends Presenter<PassChangePresenter.MyView, PassChangePresenter.MyProxy> implements
    PassChangeUiHandlers {
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<PassChangeUiHandlers> {

        PassChangeModel getModel();

        PassChangePanel getPanel();

        void niePoprawneHaslo();

    }

    private final PlaceManager placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.passchange)
    public interface MyProxy extends ProxyPlace<PassChangePresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public PassChangePresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);

    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @Override
    public void onPowrotClicked() {
        Window.Location.replace("Usosweb.html");
    }

    @Override
    public void onZmianaClicked() {
        String login = Cookies.getCookie("loggedUser");
        String pass = getView().getPanel().getOldPassword().getValue();
        userService.getUser(login, pass, new AsyncCallback<UserDTO>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println("fail");
                getView().niePoprawneHaslo();
            }

            @Override
            public void onSuccess(UserDTO result) {
                if (result != null) {
                    result.setAktywny(true);
                    result.setIloscLogowan(3);
                    result.setPassword(getView().getPanel().getPassword().getValue());
                    getView().getModel().setUser(result);
//                    Cookies.setCookie("loggedUser", result.getLogin());
//                    Cookies.setCookie("userRole", String.valueOf(result.getRola().ordinal()));
                    zmien();
                } else {
                    getView().niePoprawneHaslo();
                }
            }
        });

    }

    public void zmien() {
        userService.updateUser(getView().getModel().getUser(), new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                shownextpage("Hasło zostało zmienione.");
                getView().getPanel().clearForm();
                placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.app).build());
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });

    }

    @Override
    public void sprawdzCzyStareHasloJestPoprawne(String login) {

//        userService.zarejestruj(getView().getModel().getUser(), new AsyncCallback<User>() {
//
//            @Override
//            public void onSuccess(User result) {
//
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//                getView().niePoprawneHaslo();
//            }
//        });

    }

    private native void alertMessage(String message) /*-{
		//    $wnd.close();
		$wnd.alert(message);
		//      $wnd.location = "Logout.html";
    }-*/;

    private native void shownextpage(String message) /*-{
		$wnd.alert(message);
		$wnd.close();
		//      $wnd.location = "Logout.html";
    }-*/;
}
