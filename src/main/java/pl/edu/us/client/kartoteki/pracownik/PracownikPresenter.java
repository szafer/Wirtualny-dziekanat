package pl.edu.us.client.kartoteki.pracownik;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;

public class PracownikPresenter extends BasePresenter<PracownikPresenter.MyView, PracownikPresenter.MyProxy> implements
		PracownikUiHandlers {

	public interface MyView extends View, HasUiHandlers<PracownikUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.pracownicy)
	public interface MyProxy extends ProxyPlace<PracownikPresenter> {
	}

	@Inject
	public PracownikPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
