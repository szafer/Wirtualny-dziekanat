package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class MojeWnioskiMainPanel extends BazowyPanel {

    private BorderLayoutData westData;
    private MojeWnioskiModel model;
    private MojeWnioskiGridPanel gridPanel;
    private ContentPanel centerPanel;
//    private ContentPanel rightContainer;
    private TextButton btnDrukuj = new TextButton("Drukuj");
    private TextField imie, nazwisko;
    private DateField data, dataR;
    private BigDecimalField kwota;

    private ContentPanel wniosekPanel;
    private static int AUTO_ID = 0;

    @Inject
    public MojeWnioskiMainPanel(final MojeWnioskiModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new MojeWnioskiGridPanel(model);
        westData = new BorderLayoutData(500);
        westData.setCollapsible(true);

        getBorderLayoutContainer().setWestWidget(gridPanel, westData);

//        centerPanel =new ContentPanel();
//        centerPanel.setHeadingText("Szczegóły wniosku");
//        centerPanel.add(createCenterPanel());
        getBorderLayoutContainer().setCenterWidget(createCenterPanel());

        gridPanel.getEditing().addStartEditHandler(new StartEditHandler<UWniosekDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UWniosekDTO> event) {
                if (model.getWniosek() != null) {
                    if (model.getWniosek().getStatus() != StatusWniosku.OCZEKJACY) {
                        gridPanel.getEditing().cancelEditing();
                    }
                }
                setSaveEnabled(false);
            }

        });
        gridPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UWniosekDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<UWniosekDTO> event) {
                setSaveEnabled(true);
            }
        });
        gridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UWniosekDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<UWniosekDTO> event) {
                setSaveEnabled(true);
            }
        });
        gridPanel.getBtnDodaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                UWniosekDTO wniosek = new UWniosekDTO();
                wniosek.setId(--AUTO_ID);
                wniosek.setDataZlozenia(new Date());
                wniosek.setStatus(StatusWniosku.OCZEKJACY);
                wniosek.setUzytkownik(model.getUser());
                model.getStoreWnioskiUzytkownika().add(0, wniosek);

                gridPanel.getComboTyp().setReadOnly(false);
            }
        });

    }

    private FormPanel createCenterPanel() {
        CenterLayoutContainer cp = new CenterLayoutContainer();
        
        FormPanel fp = new FormPanel();
        
        imie = new TextField();
        imie.setReadOnly(true);
        imie.setName("imie");

        nazwisko = new TextField();
        nazwisko.setReadOnly(true);
        nazwisko.setName("nazwisko");

        data = new DateField();
        data.setName("data");
        data.setReadOnly(true);

        dataR = new DateField();
        dataR.setName("data");
        dataR.setReadOnly(true);

        kwota = new BigDecimalField();
        kwota.setEditable(false);

        wniosekPanel = new ContentPanel();
        wniosekPanel.setHeaderVisible(false);


        btnDrukuj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                Window.open(GWT.getHostPageBaseURL() + "wniosek" + "?" + budujRequest(), "_blank", "resizable=yes");
            }
        });
        btnDrukuj.setEnabled(false);

        TextButton printButton = new TextButton("Print");
        printButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                Window.print();
            }
        });
        btnDrukuj.setScale(ButtonScale.LARGE);
        btnDrukuj.setWidth(100);
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(imie, "Imię"));
        vlc.add(new FieldLabel(nazwisko, "Nazwisko"));
        vlc.add(new FieldLabel(data, "Data urodzenia"));        
        vlc.add(new FieldLabel(kwota, "Przyznane stypendium"), new VerticalLayoutData(-1, -1, new Margins(30, 0, 0, 0)));
        vlc.add(new FieldLabel(dataR, "Data rozpatrzenia wniosku"));
        vlc.add(btnDrukuj, new VerticalLayoutData(-1, -1, new Margins(30, 0, 0, 156)));
        cp.add(vlc);
        fp.setWidget(cp);
        return fp;
    }

    private String budujRequest() {
        String s = "";
        s += "id=" + model.getWniosek().getId();
        return s;
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.getStoreWnioskiUzytkownika().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreWnioskiUzytkownika().getModifiedRecords().size() > 0);
    }

    public MojeWnioskiGridPanel getGridPanel() {
        return gridPanel;
    }

    public ContentPanel getWniosekPanel() {
        return wniosekPanel;
    }

    public TextField getImie() {
        return imie;
    }

    public TextField getNazwisko() {
        return nazwisko;
    }

    public DateField getData() {
        return data;
    }

    public TextButton getBtnDrukuj() {
        return btnDrukuj;
    }
    public DateField getDataR() {
        return dataR;
    }
    public BigDecimalField getKwota() {
        return kwota;
    }
}
