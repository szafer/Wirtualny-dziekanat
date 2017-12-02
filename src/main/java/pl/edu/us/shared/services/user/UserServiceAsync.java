package pl.edu.us.shared.services.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.User;

public interface UserServiceAsync {

    void getUser(String name, String password, AsyncCallback<User> callback);

    void zapisz(List<User> doZapisu, List<User> doUsuniecia, AsyncCallback<List<User>> callback);

    void getUsers(AsyncCallback<List<User>> callback);

    void logout(AsyncCallback<User> callback);

    void getPassByEmail(String email, AsyncCallback<String> callback);
    
    void zarejestruj(User user, AsyncCallback<User> callback);
}
