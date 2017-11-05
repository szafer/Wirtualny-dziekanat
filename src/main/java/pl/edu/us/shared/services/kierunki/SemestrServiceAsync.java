package pl.edu.us.shared.services.kierunki;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.Semestr;

public interface SemestrServiceAsync {

	void zapisz(List<Semestr> doZapisu, List<Semestr> doUsuniecia, AsyncCallback<List<Semestr>> callback);

	void getKierunki(AsyncCallback<List<Semestr>> callback);

}
