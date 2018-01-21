package pl.edu.us.shared.services.wiadomosci;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;

public interface WiadomosciServiceAsync {

    void getWiadomosci(Integer userId, AsyncCallback<UserMessagesDTO> callback);

    void getNoweWiadomosci(Integer userId, AsyncCallback<List<OdbiorcaDTO>> callback);

    void updateMessage(OdbiorcaDTO dto, AsyncCallback<Void> asyncCallback);

    void wyslij(NadawcaDTO dto, AsyncCallback<Void> asyncCallback);
}
