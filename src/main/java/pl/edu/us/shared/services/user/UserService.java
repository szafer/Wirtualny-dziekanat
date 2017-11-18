package pl.edu.us.shared.services.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.User;

@RemoteServiceRelativePath("usosweb/userService")
public interface UserService extends RemoteService {

	User getUser(String name, String password);

	List<User> zapisz(List<User> doZapisu, List<User> doUsuniecia);

	List<User> getUsers();

	User logout();

}