package pl.edu.us.client.main;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.sencha.gxt.widget.core.client.Component;

public abstract class BasePresenter<V extends View, Proxy_ extends Proxy<?>> extends Presenter<V, Proxy_> implements
		HasHandlers {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> CONTENT_SLOT = new Type<RevealContentHandler<?>>();

	public BasePresenter(EventBus eventBus, V view, Proxy_ proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		Component mainPanel = (Component) getView().asWidget();
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, ContentPagePresenter.TYPE_CONTENT, this);
	}
}