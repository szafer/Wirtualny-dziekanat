package pl.edu.us.client.accesproperties;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public interface UWniosekProperties extends AccProperties<UWniosekDTO> {

    ValueProvider<UWniosekDTO, Integer> id();

    @Path("uzytkownik.imie")
    ValueProvider<UWniosekDTO, String> uzytkownikImie();

    @Path("uzytkownik.nazwisko")
    ValueProvider<UWniosekDTO, String> uzytkownikNazwisko();

    ValueProvider<UWniosekDTO, UserDTO> uzytkownik();

    ValueProvider<UWniosekDTO, WniosekDTO> wniosek();

    @Path("wniosek.typ.nazwa")
    ValueProvider<UWniosekDTO, String> wniosekTypNazwa();

    ValueProvider<UWniosekDTO, Date> dataZlozenia();

    ValueProvider<UWniosekDTO, StatusWniosku> status();

    @Path("status.nazwa")
    ValueProvider<UWniosekDTO, String> statusNazwa();

    ValueProvider<UWniosekDTO, Date> dataRozpatrzenia();

    ValueProvider<UWniosekDTO, BigDecimal> kwota();

}
