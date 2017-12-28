package pl.edu.us.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.old.Pracownik;

public interface PracownikServiceAsync {

	void zapisz(Pracownik pracownik, AsyncCallback<Void> callback);

	void znajdz(long employeeId, AsyncCallback<Pracownik> callback);

}
