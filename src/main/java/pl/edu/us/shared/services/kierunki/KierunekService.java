package pl.edu.us.shared.services.kierunki;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.Kierunek;

@RemoteServiceRelativePath("usosweb/kierunekService")
public interface KierunekService extends RemoteService {

	List<Kierunek> zapisz(List<Kierunek> doZapisu, List<Kierunek> doUsuniecia);

	List<Kierunek> getKierunki();
}