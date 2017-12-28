package pl.edu.us.client.symulacja.przychodykoszty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.shared.model.old.ViPrzychod;
import pl.edu.us.shared.services.przychodykoszty.PrzychodyKosztyService;
import pl.edu.us.shared.services.przychodykoszty.PrzychodyKosztyServiceAsync;

public class PrzychodyKosztyPresenter extends
		BasePresenter<PrzychodyKosztyPresenter.MyView, PrzychodyKosztyPresenter.MyProxy> implements
		PrzychodyKosztyUiHandlers {

	public interface MyView extends View, HasUiHandlers<PrzychodyKosztyUiHandlers> {

		PrzychodyKosztyModel getModel();

		void filtrujGrida();

		void filtrujStoreRok();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.przychodykoszty)
	public interface MyProxy extends ProxyPlace<PrzychodyKosztyPresenter> {
	}

	private final PrzychodyKosztyServiceAsync service = GWT.create(PrzychodyKosztyService.class);

	@Inject
	public PrzychodyKosztyPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);

	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().getModel().wyczysc();
		pobierzPrzychody();
	}

	private void pobierzPrzychody() {
		service.getPrzychody(new AsyncCallback<List<ViPrzychod>>() {
			@Override
			public void onSuccess(List<ViPrzychod> result) {
				if (result != null)
					getView().getModel().getStorePrzychodyAll().addAll(result);
				getView().filtrujGrida();
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail");
			}
		});
		// getView().getModel().getStorePrzychodyAll().addAll(getPrzychody());
	}

	public List<ViPrzychod> getPrzychody() {// TODO metoda tymczasowa do
											// usuniecia
		List<ViPrzychod> przychody = new ArrayList<ViPrzychod>();
		int j = 0;
		Integer i = 1;
		for (i = 1; i < 25; i++) {
			ViPrzychod p = new ViPrzychod();
			p.setId(i);
			// p.setIloscStud(5);
			p.setRok(2014);
			p.setMc(i);
			p.setKoszt(BigDecimal.TEN.multiply(BigDecimal.valueOf(i.longValue())));
			// p.setKierunekId(1);
			p.setPrzychod(new BigDecimal(100.0));
			p.setKosztNaStud(new BigDecimal(50.0));
			p.setDochod(new BigDecimal(50.0));
			if (i > 12) {
				j++;
				p.setMc(j);
				p.setRok(2015);
			}
			przychody.add(p);

		}
		return przychody;
	}
	@Override
	public void wykonajZapisz() {
	}

	@Override
	public void wykonajAnuluj() {
		onReset();
	}

	@Override
	public void wykonajZamknij() {
		// TODO Auto-generated method stub

	}

}
