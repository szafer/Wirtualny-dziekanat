package pl.edu.us.client.uzytkownik.mojeprzedmioty;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.client.uzytkownik.mojeprzedmioty.nauczyciel.OcenyGridPanel;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Rola;

public class MojePrzedmiotyMainPanel extends BazowyPanel {

    private MojePrzedmiotyModel model;
    private MojePrzedmiotyGridPanel gridPanel;
    private OcenyGridPanel ocenyPanel;
    private BorderLayoutData westData;
    private Rola rola;

    interface OcenyDriver extends SimpleBeanEditorDriver<UPrzedmiotDTO, OcenyGridPanel> {

    }

    private OcenyDriver driver = GWT.create(OcenyDriver.class);

    @Inject
    public MojePrzedmiotyMainPanel(final MojePrzedmiotyModel model) {
        this.model = model;
        String r = Cookies.getCookie("userRole");
        rola = Rola.values()[Integer.valueOf(r)];
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new MojePrzedmiotyGridPanel(model.getStorePrzedmiotyUsera(), model.getUPrzedmiotProp(), rola);
        ocenyPanel = new OcenyGridPanel(model.getStoreOcenyStudentow(), model.getUPrzedmiotProp());
        ocenyPanel.getEditing().addStartEditHandler(new StartEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UPrzedmiotDTO> event) {
                setSaveEnabled(false);

            }
        });
        ocenyPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<UPrzedmiotDTO> event) {
                setSaveEnabled(true);

            }
        });
        ocenyPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<UPrzedmiotDTO> event) {
                setSaveEnabled(true);
            }
        });
        westData = new BorderLayoutData(500);
        westData.setCollapsible(true);
        driver.initialize(ocenyPanel);
        getBorderLayoutContainer().setWestWidget(gridPanel, westData);
        if (rola != Rola.STUDENT) {
            getBorderLayoutContainer().setCenterWidget(ocenyPanel);
        }

//Dodawanie jest wycofane - mozliwy tylko zapis
/*        nowy.addSelectHandler(new SelectHandler() {

    @Override
    public void onSelect(SelectEvent event) {
UPrzedmiotDTO s1 = new UPrzedmiotDTO();
int size = 0;
for (UPrzedmiotDTO s : model.getStorePrzedmiotyUsera().getAll()) {
    if (s.getId() != null && s.getId() > 0)
size = s.getId();
}
size++;
s1.setId(size);
gridPanel.getEditing().cancelEditing();
model.getStorePrzedmiotyUsera().add(0, s1);
int row = model.getStorePrzedmiotyUsera().indexOf(s1);
gridPanel.getEditing().startEditing(new GridCell(row, 0));
    }
});
usun.addSelectHandler(new SelectHandler() {

    @Override
    public void onSelect(SelectEvent event) {
try {
    UPrzedmiotDTO edited = driver.flush();
    if (edited == null)
return;
    model.getStorePrzedmiotyUsera().remove(edited);
//                    model.getDoUsuniecia().add(edited);

} catch (IllegalStateException e) {
    return;
}
    }
});
zatwierdz.addSelectHandler(new SelectHandler() {

    @Override
    public void onSelect(SelectEvent event) {
model.getStorePrzedmiotyUsera().commitChanges();
setSaveEnabled(true);
    }
});*/
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public MojePrzedmiotyGridPanel getGridPanel() {
        return gridPanel;
    }

    public OcenyGridPanel getOcenyPanel() {
        return ocenyPanel;
    }


    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.getStoreOcenyStudentow().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreOcenyStudentow().getModifiedRecords().size() > 0);
    }
}
