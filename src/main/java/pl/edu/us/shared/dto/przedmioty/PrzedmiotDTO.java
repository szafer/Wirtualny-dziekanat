package pl.edu.us.shared.dto.przedmioty;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.shared.dto.SlownikDTO;
import pl.edu.us.shared.model.przedmioty.Przedmiot;

public class PrzedmiotDTO extends SlownikDTO {

    private static final long serialVersionUID = 5229143312328387201L;

    public PrzedmiotDTO() {
        super();
        studenci = new ArrayList<UPrzedmiotDTO>();
    }

    public PrzedmiotDTO(Przedmiot p) {
        super(p.getId(), p.getNazwa());
    }

    private UPrzedmiotDTO wykladowca;
    private List<UPrzedmiotDTO> studenci;

    public UPrzedmiotDTO getWykladowca() {
        return wykladowca;
    }

    public void setWykladowca(UPrzedmiotDTO wykladowca) {
        this.wykladowca = wykladowca;
    }

    public List<UPrzedmiotDTO> getStudenci() {
        return studenci;
    }

    public void setStudenci(List<UPrzedmiotDTO> studenci) {
        this.studenci = studenci;
    }
}
