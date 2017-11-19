package pl.edu.us.client.passremind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
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
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class PassRemindPresenter extends Presenter<PassRemindPresenter.MyView, PassRemindPresenter.MyProxy> implements
    PassRemindUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MENU = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<PassRemindUiHandlers> {

        TextBox getLogin();

    }

    private final PlaceManager placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.passremind)
    public interface MyProxy extends ProxyPlace<PassRemindPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public PassRemindPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    private native void shownextpage(String message) /*-{
		$wnd.close();
		$wnd.alert(message);
		//      $wnd.location = "Logout.html";
    }-*/;

    public void onPowrotClicked() {
        Window.Location.replace("Usosweb.html");
    }

    @Override
    public void onWyslijClicked() {
// placeManager.revealPlace(new
// PlaceRequest.Builder().nameToken(NameTokens.app).build());
// TODO odkomentowac
        String login = getView().getLogin().getValue();
        String pass = null;//getView().getPass().getValue();
        if (login.isEmpty() || pass.isEmpty()) {
            getView().getLogin().setValue("");
            return;
        } else {
            userService.getUser(login, pass, new AsyncCallback<User>() {

                @Override
                public void onFailure(Throwable caught) {
                    System.out.println("fail");
                    getView().getLogin().setValue("");
                    shownextpage("Niepoprawna nazwa użytkownika lub hasło.");
                }

                @Override
                public void onSuccess(User result) {
                    if (result != null) {
                        Cookies.setCookie("loggedUser", result.getLogin());
                        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.app).build());
                    }
                }
            });
        }
    }
}
