package pl.edu.us.client.passremind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.util.msg.Message;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.sencha.gxt.widget.core.client.box.MessageBox;

import pl.edu.us.client.NameTokens;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class PassRemindPresenter extends Presenter<PassRemindPresenter.MyView, PassRemindPresenter.MyProxy> implements
    PassRemindUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MENU = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<PassRemindUiHandlers> {

        TextBox getEmail();

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
        final String to = getView().getEmail().getValue();
        userService.getPassByEmail(to, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println("fail");
                getView().getEmail().setValue("");
                shownextpage("Nie znaleziono użytkownika o takim adresie email.");
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    getView().getEmail().setValue("");
                    shownextpage("Na adres: " + to + " zostało wysłane hasło.");
                }
            }
        });
    }

}
