package pl.edu.us.client.accesproperties;

import java.util.List;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public interface PrzedmiotProperties extends AccProperties<PrzedmiotDTO> {

    ValueProvider<PrzedmiotDTO, String> nazwa();

    ValueProvider<PrzedmiotDTO, UPrzedmiotDTO> wykladowca();

    ValueProvider<PrzedmiotDTO, List<UPrzedmiotDTO>> studenci();
}
