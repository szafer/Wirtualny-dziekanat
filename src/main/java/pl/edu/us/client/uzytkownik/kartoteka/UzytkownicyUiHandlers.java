package pl.edu.us.client.uzytkownik.kartoteka;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import pl.edu.us.client.PrzyciskiUiHandlers;
import pl.edu.us.shared.dto.UserDTO;

public interface UzytkownicyUiHandlers extends PrzyciskiUiHandlers {

    void getUsers(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UserDTO>> callback);

}
