package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public interface OcenyStudentaProperties extends AccProperties<UPrzedmiotDTO> {

    ValueProvider<UPrzedmiotDTO, Float> ocena1();

    ValueProvider<UPrzedmiotDTO, Float> ocena2();

    @Path("uzytkowik.imie")
    ValueProvider<UPrzedmiotDTO, String> uzytkownikImie();

    @Path("uzytkowik.nazwisko")
    ValueProvider<UPrzedmiotDTO, String> uzytkownikNazwisko();
}
