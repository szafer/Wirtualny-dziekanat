package pl.edu.us.client.uzytkownik.kartoteka;

import java.util.ArrayList;
import java.util.Collection;
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
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.NameTokens;
import pl.edu.us.client.main.BasePresenter;
import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Message;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class UzytkownicyPresenter extends
    BasePresenter<UzytkownicyPresenter.MyView, UzytkownicyPresenter.MyProxy> implements
    UzytkownicyUiHandlers {

    public interface MyView extends View, HasUiHandlers<UzytkownicyUiHandlers> {

        UzytkownicyModel getModel();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.kartotekaUzytkownikow)
    public interface MyProxy extends ProxyPlace<UzytkownicyPresenter> {
    }

    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final RpcMasking rpcMasking;

    @Inject
    public UzytkownicyPresenter(EventBus eventBus, MyView view, MyProxy proxy,   final RpcMasking rpcMasking) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
        this.rpcMasking = rpcMasking;
        this.rpcMasking.setMaskedComponent((Component) getView().asWidget());

    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().getModel().wyczysc();
//        pobierzUzytkownikow();
    }

    private void pobierzUzytkownikow() {
        userService.getUsers(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                if (result != null)
                    getView().getModel().getStoreUsers().addAll(result);
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
        List<UserDTO> doZapisu = new ArrayList<UserDTO>();
        Collection<Store<UserDTO>.Record> lista = getView().getModel().getStoreUsers().getModifiedRecords();
        for (Record r : lista) {
            r.commit(true);
            doZapisu.add((UserDTO) r.getModel());
        }
//        doZapisu.addAll(getView().getModel().getUsers().getAll());
//        List<Student> doUsuniecia = new ArrayList<Student>();// getView().getModel().getStudents().getAll();
//        doUsuniecia.addAll(getView().getModel().getDoUsunieccia().getAll());
//        userService.zapisz(doZapisu, new AsyncCallback<List<UserDTO>>() {
//
//            @Override
//            public void onFailure(Throwable caught) {
//                // TODO Auto-generated method stub
//                System.out.println(caught.getLocalizedMessage());
//            }
//
//            @Override
//            public void onSuccess(List<UserDTO> result) {
//                Info.display("Użytkowncy", "Zapisano dane");
////                getView().getModel().getStoreUsers().clear();
////                getView().getModel().getStoreUsers().addAll(result);
//            }
//        });
        
        userService.zapisz(doZapisu, rpcMasking.call(Message.SAVING,
            new ActionCallback<List<UserDTO>>() {

            @Override
            public void onSuccess(List<UserDTO> result) {
                Info.display("Użytkowncy", "Zapisano dane");
            }
        }));

    }

    @Override
    public void wykonajAnuluj() {
      getView().getModel().getStoreUsers().rejectChanges();
    }

    @Override
    public void wykonajZamknij() {
        removeFromParentSlot();
    }
}
