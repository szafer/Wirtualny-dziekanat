package pl.edu.us.shared.dto.wiadomosci;

import java.util.List;

import pl.edu.us.shared.dto.DTO;

public class UserMessagesDTO extends DTO {

    private static final long serialVersionUID = -6047745045346672825L;
    private List<NadawcaDTO> nadane;
    private List<OdbiorcaDTO> odebrane;
    private List<OdbiorcaDTO> nowe;

    public UserMessagesDTO() {
        super(1);
    }

    public List<NadawcaDTO> getNadane() {
        return nadane;
    }

    public void setNadane(List<NadawcaDTO> nadane) {
        this.nadane = nadane;
    }

    public List<OdbiorcaDTO> getOdebrane() {
        return odebrane;
    }

    public void setOdebrane(List<OdbiorcaDTO> odebrane) {
        this.odebrane = odebrane;
    }

    public List<OdbiorcaDTO> getNowe() {
        return nowe;
    }

    public void setNowe(List<OdbiorcaDTO> nowe) {
        this.nowe = nowe;
    }
}
