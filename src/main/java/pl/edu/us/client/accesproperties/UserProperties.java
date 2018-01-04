package pl.edu.us.client.accesproperties;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;

public interface UserProperties extends PersonProperties<UserDTO> {

    ValueProvider<UserDTO, String> login();

    ValueProvider<UserDTO, String> email();

    ValueProvider<UserDTO, Date> dataUrodzenia();

    ValueProvider<UserDTO, String> ulica();

    ValueProvider<UserDTO, String> nrDomu();

    ValueProvider<UserDTO, String> nrMieszkania();

    ValueProvider<UserDTO, String> miasto();

    ValueProvider<UserDTO, String> kodPocztowy();

    ValueProvider<UserDTO, Plec> plec();

    ValueProvider<UserDTO, Rola> rola();

    ValueProvider<UserDTO, Boolean> aktywny();
    
    ValueProvider<UserDTO, Integer> iloscLogowan();

    @Path("rola.kod")
    ValueProvider<UserDTO, String> rolaKod();

    @Path("plec.kod")
    ValueProvider<UserDTO, String> plecKod();
    
    ValueProvider<UserDTO, Boolean> powiadomic();

}
