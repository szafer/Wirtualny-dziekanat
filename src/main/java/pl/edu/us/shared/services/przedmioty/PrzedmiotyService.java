package pl.edu.us.shared.services.przedmioty;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

@RemoteServiceRelativePath("usosweb/przedmiotyService")
public interface PrzedmiotyService extends RemoteService{

    List<UPrzedmiotDTO> getPrzedmiotyStudentow(Integer przedmiot);

    void zapisz(List<UPrzedmiotDTO> doZapisu);

    List<PrzedmiotDTO> zapisz(List<PrzedmiotDTO> doZapisu, List<UPrzedmiotDTO> doUsuniecia);

    List<PrzedmiotDTO> getKierunki();

    List<UserDTO> getWykladowcy();
}
