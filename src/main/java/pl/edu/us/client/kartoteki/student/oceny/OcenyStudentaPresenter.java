package pl.edu.us.client.kartoteki.student.oceny;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;

public class OcenyStudentaPresenter extends
    BasePresenter<OcenyStudentaPresenter.MyView, OcenyStudentaPresenter.MyProxy> implements OcenyStudentaUiHandlers {

    public interface MyView extends View, HasUiHandlers<OcenyStudentaUiHandlers> {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.ocenyStudenta)
    public interface MyProxy extends ProxyPlace<OcenyStudentaPresenter> {
    }

    @Inject
    public OcenyStudentaPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
        removeFromParentSlot();
    }
}
