package pl.edu.us.client.admin;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;

public class AdminPresenter extends BasePresenter<AdminPresenter.MyView, AdminPresenter.MyProxy>
implements AdminUiHandlers {

	// private static final OsobaProperties props =
	// GWT.create(OsobaProperties.class);
	public interface MyView extends View, HasUiHandlers<AdminUiHandlers> {
	}
	@ProxyCodeSplit
	@NameToken(NameTokens.admin)
	public interface MyProxy extends ProxyPlace<AdminPresenter> {
	}
	
	@Inject
	public AdminPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);

	}

	@Override
	public void wykonajZapisz() {
		// TODO Auto-generated method stub

	}

	@Override
	public void wykonajAnuluj() {
		// TODO Auto-generated method stub

	}

	@Override
	public void wykonajZamknij() {
		// TODO Auto-generated method stub

	}
}
