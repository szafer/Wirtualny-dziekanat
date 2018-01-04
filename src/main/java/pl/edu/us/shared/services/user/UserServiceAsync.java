package pl.edu.us.shared.services.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.model.User;

public interface UserServiceAsync {

    void getUser(String name, String password, AsyncCallback<UserDTO> callback);

    void zapisz(List<UserDTO> doZapisu, AsyncCallback<List<UserDTO>> callback);

    void getUsers(AsyncCallback<List<UserDTO>> callback);

    void logout(AsyncCallback<User> callback);

    void getPassByEmail(String email, AsyncCallback<String> callback);
    
    void zarejestruj(UserDTO user, AsyncCallback<UserDTO> callback);
    
    void updateUser(UserDTO user,  AsyncCallback<UserDTO> asyncCallback);
    
    void pobierzDaneUzytkownika(String name, AsyncCallback<UserDTO> callback);
    
    void getUsers(PagingLoadConfig config, AsyncCallback<PagingLoadResult<UserDTO>> callback);
}
