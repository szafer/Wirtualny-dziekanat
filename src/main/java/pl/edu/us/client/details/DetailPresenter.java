package pl.edu.us.client.details;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.ContentPagePresenter;
/**
 * 
 * @author marek
 *  Klasa do usuniecia- aktualnie nie uzywane
 */
public class DetailPresenter extends Presenter<DetailPresenter.MyView, DetailPresenter.MyProxy> {


	@ContentSlot
	public static final Type<RevealContentHandler<?>> TAB_CONTENT = new Type<RevealContentHandler<?>>();

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.details)
	public interface MyProxy extends ProxyPlace<DetailPresenter> {
	}

	@Inject
	public DetailPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, ContentPagePresenter.TYPE_CONTENT, this);
	}
	
}