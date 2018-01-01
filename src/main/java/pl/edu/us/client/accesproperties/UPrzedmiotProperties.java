package pl.edu.us.client.accesproperties;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public interface UPrzedmiotProperties extends AccProperties<UPrzedmiotDTO> {


    ValueProvider<UPrzedmiotDTO, Date> dataSemestru();

    ValueProvider<UPrzedmiotDTO, Float> ocena1();

    ValueProvider<UPrzedmiotDTO, Float> ocena2();

    @Path("przedmiot.nazwa")
    ValueProvider<UPrzedmiotDTO, String> przedmiotNazwa();

    @Path("semestr.nazwa")
    ValueProvider<UPrzedmiotDTO, String> semestrNazwa();
    
    @Path("uzytkownik.imie")
    ValueProvider<UPrzedmiotDTO, String> uzytkownikImie();
    
    @Path("uzytkownik.nazwisko")
    ValueProvider<UPrzedmiotDTO, String> uzytkownikNazwisko();
    
    ValueProvider<UPrzedmiotDTO, UserDTO> uzytkownik();
}
