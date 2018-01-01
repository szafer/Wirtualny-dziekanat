package pl.edu.us.shared.services.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.model.User;

public interface UserServiceAsync {

    void getUser(String name, String password, AsyncCallback<UserDTO> callback);

    void zapisz(List<User> doZapisu, List<User> doUsuniecia, AsyncCallback<List<User>> callback);

    void getUsers(AsyncCallback<List<User>> callback);

    void logout(AsyncCallback<User> callback);

    void getPassByEmail(String email, AsyncCallback<String> callback);
    
    void zarejestruj(UserDTO user, AsyncCallback<UserDTO> callback);
    
    void updateUser(UserDTO user,  AsyncCallback<UserDTO> asyncCallback);
    
    void pobierzDaneUzytkownika(String name, AsyncCallback<UserDTO> callback);
}
