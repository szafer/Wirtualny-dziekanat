package pl.edu.us.client.symulacja.przychodykosztyroczne;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;

public class PKRocznePresenter extends BasePresenter<PKRocznePresenter.MyView, PKRocznePresenter.MyProxy> implements
		PKRoczneUiHandlers {

	public interface MyView extends View, HasUiHandlers<PKRoczneUiHandlers> {

		PKRoczneModel getModel();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.przychodykosztyroczne)
	public interface MyProxy extends ProxyPlace<PKRocznePresenter> {
	}

	// private final StudentServiceAsync service =
	// GWT.create(StudentService.class);

	@Inject
	public PKRocznePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
