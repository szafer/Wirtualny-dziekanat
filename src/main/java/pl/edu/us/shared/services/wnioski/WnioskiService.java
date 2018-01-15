package pl.edu.us.shared.services.wnioski;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;

@RemoteServiceRelativePath("usosweb/wnioskiService")
public interface WnioskiService extends RemoteService{

    List<WniosekDTO> getWnioski();
    
    List<WniosekDTO> zapisz(List<WniosekDTO> doZapisu) throws Exception;

    List<UWniosekDTO> pobierzWnioskiStudentow();

    List<UWniosekDTO> zapiszWnoiski(List<UWniosekDTO> doZapisu);

}
