package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;

public interface PrzedmiotProperties extends PropertyAccess<PrzedmiotDTO> {

    @Path("id")
    ModelKeyProvider<PrzedmiotDTO> key();

    ValueProvider<PrzedmiotDTO, String> nazwa();
}
