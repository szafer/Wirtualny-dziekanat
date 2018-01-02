package pl.edu.us.shared.services.przedmioty;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public interface PrzedmiotyServiceAsync {

    void getPrzedmiotyStudentow(Integer przedmiot, AsyncCallback<List<UPrzedmiotDTO>> callback);

    void zapisz(List<UPrzedmiotDTO> doZapisu, AsyncCallback<Void> asyncCallback);
    
    void zapisz(List<PrzedmiotDTO> doZapisu, List<UPrzedmiotDTO> zmiany, AsyncCallback<List<PrzedmiotDTO>> callback);

    void getKierunki(AsyncCallback<List<PrzedmiotDTO>> callback);
    
    void getWykladowcy(AsyncCallback<List<UserDTO>> callback);

}
