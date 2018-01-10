package pl.edu.us.shared.services.instrukcja;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.Instrukcja;

public interface InstrukcjaServiceAsync {

    void pobierz(AsyncCallback<byte[]> callback);

}
