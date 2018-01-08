package pl.edu.us.client.accesproperties;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;

public interface NadawcaProperties extends AccProperties<NadawcaDTO>{

    
    ValueProvider<NadawcaDTO, String> temat();

    ValueProvider<NadawcaDTO, Date> data();
    
    ValueProvider<NadawcaDTO, String> wiadomosc();
    
    ValueProvider<NadawcaDTO, String> imie();

    ValueProvider<NadawcaDTO, String> nazwisko();


//    @Path("nadawca.imie")
//    ValueProvider<OdbiorcaDTO, String> nadawcaImie();
//    
//    @Path("nadawca.nazwisko")
//    ValueProvider<OdbiorcaDTO, String> nadawcaNazwisko();
//    
//
//    ValueProvider<OdbiorcaDTO, Boolean> odebrano();
//    
//    ValueProvider<OdbiorcaDTO, Boolean> email();
    
    
    
//    private Integer userId;
//
//    private String temat;
//
//    private String wiadomosc;
//
//    private Date data;
//
//    private List<OdbiorcaDTO> odbiorcy;
//
//    private String imie;
//    private String nazwisko;
}
