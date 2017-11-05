package pl.edu.us.client.kartoteki.student.kierunki;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;

public class KierunkiStudentaPresenter extends
		BasePresenter<KierunkiStudentaPresenter.MyView, KierunkiStudentaPresenter.MyProxy> implements
		KierunkiStudentaUiHandlers {

	public interface MyView extends View, HasUiHandlers<KierunkiStudentaUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.kierunkiStudenta)
	public interface MyProxy extends ProxyPlace<KierunkiStudentaPresenter> {
	}

	@Inject
	public KierunkiStudentaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
