package pl.edu.us.shared.services.przychodykoszty;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.ViPrzychod;

public interface PrzychodyKosztyServiceAsync {

	void getPrzychody(AsyncCallback<List<ViPrzychod>> callback);
}
