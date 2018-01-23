package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
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
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class MojeWnioskiMainPanel extends BazowyPanel {

    private BorderLayoutData westData;
    private MojeWnioskiModel model;
    private MojeWnioskiGridPanel gridPanel;
    private FormPanel centerPanel;
//    private ContentPanel rightContainer;
    private TextButton btnDrukuj = new TextButton("Drukuj");
    private TextField imie, nazwisko;
    private DateField data;
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

        centerPanel = createCenterPanel();
        getBorderLayoutContainer().setCenterWidget(centerPanel);

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

//    /* chapter12/MyJTable.java */
//    public void createPdf(boolean shapes) {
//       Document document = new Document();
//       try {
//          PdfWriter writer;
//          if (shapes)
//             writer = PdfWriter.getInstance(document,
//                new FileOutputStream("my_jtable_shapes.pdf"));
//          else
//             writer = PdfWriter.getInstance(document,
//                new FileOutputStream("my_jtable_fonts.pdf"));
//          document.open();
//          PdfContentByte cb = writer.getDirectContent();
//          PdfTemplate tp = cb.createTemplate(500, 500);
//          Graphics2D g2;
//          if (shapes)
//             g2 = tp.createGraphicsShapes(500, 500);
//          else
//             g2 = tp.createGraphics(500, 500);
//          table.print(g2);
//          g2.dispose();
//          cb.addTemplate(tp, 30, 300);
//          } catch (Exception e) {
//          System.err.println(e.getMessage());
//       }
//       document.close();
//    }
    private FormPanel createCenterPanel() {
//        VerticalLayoutContainer vl = new VerticalLayoutContainer();
//        vl.add(btnDrukuj);
//        ContentPanel cp = new ContentPanel();
        imie = new TextField();
        imie.setReadOnly(true);
        imie.setName("imie");

        nazwisko = new TextField();
        nazwisko.setReadOnly(true);
        nazwisko.setName("nazwisko");

        data = new DateField();
        data.setName("data");
        data.setReadOnly(true);

        wniosekPanel = new ContentPanel();
        wniosekPanel.setHeaderVisible(false);

        final FormPanel fp = new FormPanel();
        fp.setAction(GWT.getHostPageBaseURL() + "wniosek");
        fp.setEncoding(Encoding.MULTIPART);
        fp.setMethod(Method.GET);
        fp.setHeight(100);
        fp.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {

//                String resultHtml = event.getResults();
////
//                Info.display("Upload Response", resultHtml);
////
//                Window.open(GWT.getHostPageBaseURL() + "raport_img" + "?imie=Marek Szafraniec", "_blank", "resizable=yes");

            }
        });

        btnDrukuj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                Window.open(GWT.getHostPageBaseURL() + "wniosek" + "?" + budujRequest(), "_blank", "resizable=yes");
            }
        });
//        btnDrukuj.setEnabled(false);
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(btnDrukuj, new VerticalLayoutData(1, -1));
        vlc.add(new FieldLabel(imie, "Imię"));
        vlc.add(new FieldLabel(nazwisko, "Nazwisko"));
        vlc.add(new FieldLabel(data, "Data urodzenia"));
        vlc.add(wniosekPanel, new VerticalLayoutData(1, 1));
        fp.setWidget(vlc);
//        cp.add(vlc);
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
}
