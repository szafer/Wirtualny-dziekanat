package pl.edu.us.client.wiadomosci;

import pl.edu.us.client.PrzyciskiUiHandlers;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

public interface WiadomosciUiHandlers extends PrzyciskiUiHandlers {

    void notifyOdebrano(OdbiorcaDTO dto);

}
