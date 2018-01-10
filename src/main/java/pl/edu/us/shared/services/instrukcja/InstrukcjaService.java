package pl.edu.us.shared.services.instrukcja;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.Instrukcja;

@RemoteServiceRelativePath("usosweb/manualService")
public interface InstrukcjaService extends RemoteService {

    byte[] pobierz();

}
