package pl.edu.us.shared.services.przychodykoszty;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.old.ViPrzychod;

@RemoteServiceRelativePath("usosweb/przychodyKosztyService")
public interface PrzychodyKosztyService extends RemoteService {

	List<ViPrzychod> getPrzychody();


}
