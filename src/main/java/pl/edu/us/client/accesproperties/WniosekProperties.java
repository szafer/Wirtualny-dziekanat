package pl.edu.us.client.accesproperties;

import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.TypWniosku;

public interface WniosekProperties extends AccProperties<WniosekDTO> {

    ValueProvider<WniosekDTO, Integer> id();

    ValueProvider<WniosekDTO, TypWniosku> typ();

    ValueProvider<WniosekDTO, String> nazwaObrazu();

//    ValueProvider<WniosekDTO, byte[]> wzor();

    ValueProvider<WniosekDTO, String> obraz();
}
