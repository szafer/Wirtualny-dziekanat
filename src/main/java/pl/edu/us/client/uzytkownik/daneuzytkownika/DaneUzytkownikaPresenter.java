package pl.edu.us.client.uzytkownik.daneuzytkownika;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Cookies;
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
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class DaneUzytkownikaPresenter extends
    BasePresenter<DaneUzytkownikaPresenter.MyView, DaneUzytkownikaPresenter.MyProxy> implements
    DaneUzytkownkaUiHandlers {

    public interface MyView extends View, HasUiHandlers<DaneUzytkownkaUiHandlers> {

        DaneUzytkownikaModel getModel();

        DaneUzytkownikaPanel getPanel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.daneUzytkownika)
    public interface MyProxy extends ProxyPlace<DaneUzytkownikaPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);

    @Inject
    public DaneUzytkownikaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);

    }

    @Override
    protected void onReset() {
        super.onReset();
//        getView().getModel().wyczysc();
        pobierzDaneUzytkownika();
    }

    private void pobierzDaneUzytkownika() {
        userService.pobierzDaneUzytkownika(Cookies.getCookie("loggedUser"), new AsyncCallback<User>() {

            @Override
            public void onSuccess(User result) {
                getView().getPanel().bind(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().getPanel().bind(null);
                alertMessage("Nie udało się pobrać danych");
            }
        });
    }

    private native void alertMessage(String message) /*-{
		//    $wnd.close();
		$wnd.alert(message);
		//      $wnd.location = "Logout.html";
    }-*/;

    @Override
    public void wykonajZapisz() {
//        List<Student> doZapisu = new ArrayList<Student>();
//        doZapisu.addAll(getView().getModel().getStudents().getAll());
//        List<Student> doUsuniecia = new ArrayList<Student>();// getView().getModel().getStudents().getAll();
//        doUsuniecia.addAll(getView().getModel().getDoUsunieccia().getAll());
//        studentService.zapisz(doZapisu, doUsuniecia, new AsyncCallback<List<Student>>() {
//
//            @Override
//            public void onFailure(Throwable caught) {
//                // TODO Auto-generated method stub
//                System.out.println(caught.getLocalizedMessage());
//            }
//
//            @Override
//            public void onSuccess(List<Student> result) {
//                getView().getModel().getStudents().clear();
//                getView().getModel().getDoUsunieccia().clear();
//                pobierzStudentow();
//            }
//        });

    }

    @Override
    public void wykonajAnuluj() {
        pobierzDaneUzytkownika();

    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
