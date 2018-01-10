package pl.edu.us.client.main;

import com.google.inject.Singleton;

import pl.edu.us.shared.dto.ObrazDTO;
import pl.edu.us.shared.dto.UserDTO;

@Singleton
public class AppKontekst {
    private UserDTO user;
    private ObrazDTO obraz;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ObrazDTO getObraz() {
        return obraz;
    }

    public void setObraz(ObrazDTO obraz) {
        this.obraz = obraz;
    }
}
