package pl.edu.us.client.main;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import pl.edu.us.shared.dto.ObrazDTO;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

@Singleton
public class AppKontekst {
    private UserDTO user;
    private ObrazDTO obraz;
    List<OdbiorcaDTO> nowe = new ArrayList<OdbiorcaDTO>();
    private volatile boolean lock = false;

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

    public List<OdbiorcaDTO> getNowe() {
        return nowe;
    }

    public void setNowe(List<OdbiorcaDTO> nowe) {
        this.nowe = nowe;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
