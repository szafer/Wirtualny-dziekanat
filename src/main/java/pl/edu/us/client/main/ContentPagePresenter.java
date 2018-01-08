package pl.edu.us.client.main;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
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
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.details.DetailPresenter;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.services.wiadomosci.WiadomosciService;
import pl.edu.us.shared.services.wiadomosci.WiadomosciServiceAsync;

public class ContentPagePresenter extends Presenter<ContentPagePresenter.MyView, ContentPagePresenter.MyProxy>
    implements ContentPageUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_CONTENT = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MENU = new Type<RevealContentHandler<?>>();

    public interface MyView extends View, HasUiHandlers<ContentPageUiHandlers> {
    }

    private final MenuPresenter menuPresenter;
    private final DetailPresenter detailPresenter;
    private AppKontekst kontekst;
    private static final int MESSAGE_TIME = 30 * 1000;//1min

    WiadomosciServiceAsync messageService = GWT.create(WiadomosciService.class);

    @ProxyCodeSplit
    @NameToken(NameTokens.app)
    public interface MyProxy extends ProxyPlace<ContentPagePresenter> {
    }

    @Inject
    public ContentPagePresenter(EventBus eventBus, MyView view, MyProxy proxy, final MenuPresenter menuPesenter,
        final DetailPresenter detailPresenter, AppKontekst kontekst) {
        super(eventBus, view, proxy);
        this.menuPresenter = menuPesenter;
        this.detailPresenter = detailPresenter;
        this.kontekst = kontekst;
        getView().setUiHandlers(this);

    }

    @Override
    protected void onBind() {
        super.onBind();
        setInSlot(TYPE_MENU, menuPresenter);
//        setInSlot(TYPE_CONTENT, detailPresenter);
    }

    @Override
    protected void revealInParent() {
        if (Cookies.getCookie("loggedUser") == null) {
            shownextpage("Sesja została zakończona. Proszę ponownie zalogować się do aplikacji.");
            Window.Location.replace("Usosweb.html");
        } else {
            RevealRootContentEvent.fire(this, this);
            if (kontekst.getUser() != null) {
                new Timer() {
                    @Override
                    public void run() {
                        pobierzWiadomosci();
                    }

                }.scheduleRepeating(MESSAGE_TIME);
            }
        }
    }

    private void pobierzWiadomosci() {
        messageService.getNoweWiadomosci(kontekst.getUser().getId(), new AsyncCallback<List<OdbiorcaDTO>>() {

            @Override
            public void onSuccess(List<OdbiorcaDTO> result) {
                if (result != null) {
                  menuPresenter.loadMessages(result);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }

        });
    }

    private native void shownextpage(String message) /*-{
		$wnd.close();
		$wnd.alert(message);
		//      $wnd.location = "Logout.html";
    }-*/;
}
