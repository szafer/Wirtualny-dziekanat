package pl.edu.us.client.kartoteki.student.kartoteka;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
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
import pl.edu.us.shared.model.Kierunek;
import pl.edu.us.shared.model.Student;
import pl.edu.us.shared.services.kierunki.KierunekService;
import pl.edu.us.shared.services.kierunki.KierunekServiceAsync;
import pl.edu.us.shared.services.student.OkresStudiowService;
import pl.edu.us.shared.services.student.OkresStudiowServiceAsync;
import pl.edu.us.shared.services.student.StudentService;
import pl.edu.us.shared.services.student.StudentServiceAsync;

public class KartotekaStudentaPresenter extends
		BasePresenter<KartotekaStudentaPresenter.MyView, KartotekaStudentaPresenter.MyProxy> implements
		KartotekaStudentaUiHandlers {

	public interface MyView extends View, HasUiHandlers<KartotekaStudentaUiHandlers> {

		KartotekaStudentaModel getModel();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.studenci)
	public interface MyProxy extends ProxyPlace<KartotekaStudentaPresenter> {
	}

	private final StudentServiceAsync studentService = GWT.create(StudentService.class);
	private final OkresStudiowServiceAsync service2 = GWT.create(OkresStudiowService.class);
	private final KierunekServiceAsync kierunkiService = GWT.create(KierunekService.class);

	@Inject
	public KartotekaStudentaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);

	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().getModel().wyczysc();
		pobierzStudentow();
	}

	private void pobierzKierunki() {
		getView().getModel().getKierunki().clear();
		kierunkiService.getKierunki(new AsyncCallback<List<Kierunek>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Nie udało się pobrać kierunków");
			}

			@Override
			public void onSuccess(List<Kierunek> result) {
				if (result != null)
					getView().getModel().getKierunki().addAll(result);
				// getView().getModel().setFakedKkierunki(Lists.newArrayList(result));
			}
		});
	}

	private void pobierzStudentow() {
		studentService.getStudents(new AsyncCallback<List<Student>>() {
			@Override
			public void onSuccess(List<Student> result) {
				if (result != null)
					getView().getModel().getStudents().addAll(result);
				pobierzKierunki();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("Nie udało sie pobrać studentów");
			}
		});

	}

	@Override
	public void wykonajZapisz() {
		List<Student> doZapisu = new ArrayList<Student>();
		doZapisu.addAll(getView().getModel().getStudents().getAll());
		List<Student> doUsuniecia = new ArrayList<Student>();// getView().getModel().getStudents().getAll();
		doUsuniecia.addAll(getView().getModel().getDoUsunieccia().getAll());
		studentService.zapisz(doZapisu, doUsuniecia, new AsyncCallback<List<Student>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(List<Student> result) {
				getView().getModel().getStudents().clear();
				getView().getModel().getDoUsunieccia().clear();
				pobierzStudentow();
			}
		});

	}

	@Override
	public void wykonajAnuluj() {
		// TODO Auto-generated method stub

	}

	@Override
	public void wykonajZamknij() {
		// TODO Auto-generated method stub
		// clearSlot(ContentPagePresenter.TYPE_CONTENT);
	}
}
