package pl.edu.us.shared.services.kierunki;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.old.Semestr;

@RemoteServiceRelativePath("usosweb/semestrService")
public interface SemestrService extends RemoteService {

	List<Semestr> zapisz(List<Semestr> doZapisu, List<Semestr> doUsuniecia);

	List<Semestr> getKierunki();
}