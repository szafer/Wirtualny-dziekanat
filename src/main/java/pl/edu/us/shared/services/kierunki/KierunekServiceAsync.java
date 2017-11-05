package pl.edu.us.shared.services.kierunki;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.Kierunek;

public interface KierunekServiceAsync {

	void zapisz(List<Kierunek> doZapisu, List<Kierunek> doUsuniecia, AsyncCallback<List<Kierunek>> callback);

	void getKierunki(AsyncCallback<List<Kierunek>> callback);

}
