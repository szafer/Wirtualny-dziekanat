package pl.edu.us.client.uzytkownik.haslozmiana;

import com.google.inject.Singleton;

import pl.edu.us.shared.dto.UserDTO;

@Singleton
public class PassChangeModel {

    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
