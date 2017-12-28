package pl.edu.us.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.old.Pracownik;

@RemoteServiceRelativePath("springGwtServices/pracownikService")
public interface PracownikService extends RemoteService {
	
	Pracownik znajdz(long employeeId);
	void zapisz(Pracownik pracownik);
}
