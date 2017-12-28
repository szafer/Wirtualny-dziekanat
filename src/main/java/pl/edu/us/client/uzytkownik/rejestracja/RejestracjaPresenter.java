package pl.edu.us.client.uzytkownik.rejestracja;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
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
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import pl.edu.us.client.NameTokens;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class RejestracjaPresenter extends Presenter<RejestracjaPresenter.MyView, RejestracjaPresenter.MyProxy> implements
    RejestracjaUiHandlers {
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<RejestracjaUiHandlers> {

        RejestracjaModel getModel();

        RejestracjaPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.rejestracja)
    public interface MyProxy extends ProxyPlace<RejestracjaPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public RejestracjaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
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
    public void onZarejestrujClicked() {
        //TODO walidacja po stronie klienta
        //dalej walidacja po stronie serwera
        userService.zarejestruj(getView().getModel().getUser(), new AsyncCallback<User>() {

            @Override
            public void onSuccess(User result) {
                getView().getPanel().clearForm();
                alertMessage("Zarejestrowano użytkownika. Na podany adres email zostanie potwierdzenie odblokowania konta. ");
                Window.Location.replace("Usosweb.html");
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getPanel().getLogin().setValue("");
//                getView().getPanel().getP.setValue("");
                alertMessage("Proszę wybrać inny login.");
            }
        });

    }

    @Override
    public void sprawdzCzyLoginWBazie(String login) {
        //Do uzgodnienia czy walidować przed zapisem czy podczas zapisu
        // TODO Auto-generated method stub

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
