package pl.edu.us.shared.services.wiadomosci;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;

@RemoteServiceRelativePath("usosweb/wiadomosciService")
public interface WiadomosciService extends RemoteService{


    UserMessagesDTO getWiadomosci(Integer userId);

    List<OdbiorcaDTO> getNoweWiadomosci(Integer userId);

    void updateMessage(OdbiorcaDTO dto);

}
