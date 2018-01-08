package pl.edu.us.client.accesproperties;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;

import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

public interface OdbiorcaProperties extends AccProperties<OdbiorcaDTO>{

    @Path("nadawca.imie")
    ValueProvider<OdbiorcaDTO, String> nadawcaImie();
    
    @Path("nadawca.nazwisko")
    ValueProvider<OdbiorcaDTO, String> nadawcaNazwisko();

    @Path("nadawca.temat")
    ValueProvider<OdbiorcaDTO, String> nadawcaTemat();
    
    ValueProvider<OdbiorcaDTO, Date> dataOdbioru();

    ValueProvider<OdbiorcaDTO, Boolean> odebrano();
    
    ValueProvider<OdbiorcaDTO, Boolean> email();

//    private Integer userId;
//    private Nadawca nadawca;
//    private Boolean odebrano;
//    private Date dataOdbioru;
//    private Boolean email;
//
//    private String imie;
//    private String nazwisko;
}
