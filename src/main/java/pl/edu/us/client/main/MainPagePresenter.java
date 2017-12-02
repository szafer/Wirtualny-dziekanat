package pl.edu.us.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.Dictionary;
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

public class MainPagePresenter extends Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> implements
    MainPageUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MENU = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<MainPageUiHandlers> {

        TextBox getLogin();

        PasswordTextBox getPass();
    }

    private final PlaceManager placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.main)
    public interface MyProxy extends ProxyPlace<MainPagePresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public MainPagePresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager) {
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
		//		$wnd.location = "Logout.html";
    }-*/;

    public void onZarejestrujClicked() {
//        Window.Location.replace("Usosweb.html#!rejestracja");
        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.rejestracja).build());

    }

    @Override
    public void onLogujClicked() {
        String login = getView().getLogin().getValue();
        String pass = getView().getPass().getValue();
        if (login.isEmpty() || pass.isEmpty()) {
            getView().getLogin().setValue("");
            getView().getPass().setValue("");
            return;
        } else {
            userService.getUser(login, pass, new AsyncCallback<User>() {

                @Override
                public void onFailure(Throwable caught) {
                    System.out.println("fail");
                    getView().getLogin().setValue("");
                    getView().getPass().setValue("");
                    shownextpage("Niepoprawna nazwa użytkownika lub hasło.");
                }

                @Override
                public void onSuccess(User result) {
                    if (result != null) {
                        Cookies.setCookie("loggedUser", result.getLogin());
                        Cookies.setCookie("userRole", String.valueOf(result.getRola().ordinal()));
                        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.app).build());
                    }
                }
            });
        }
    }
}
