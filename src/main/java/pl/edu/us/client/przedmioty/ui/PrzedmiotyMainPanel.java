package pl.edu.us.client.przedmioty.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreAddEvent.StoreAddHandler;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent.StoreRemoveHandler;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.client.przedmioty.PrzedmiotyModel;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public class PrzedmiotyMainPanel extends BazowyPanel {
//	private PrzedmiotyPanel panel;
    private final PrzedmiotyModel model;
    private PrzedmiotyGridPanel przedmiotyGridPanel;
    private WykladowcaGridPanel wykladowcaPanel;
    private BorderLayoutData westData;

    interface KierunkiDriver extends SimpleBeanEditorDriver<PrzedmiotDTO, PrzedmiotyGridPanel> {

    }

    private KierunkiDriver driver = GWT.create(KierunkiDriver.class);

    @Inject
    public PrzedmiotyMainPanel(final PrzedmiotyModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        przedmiotyGridPanel = new PrzedmiotyGridPanel(model.getStorePrzedmioty(), model.getPrzedmiotProp());
        przedmiotyGridPanel.getEditing().addStartEditHandler(new StartEditHandler<PrzedmiotDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<PrzedmiotDTO> event) {
                setSaveEnabled(false);

            }
        });
        przedmiotyGridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<PrzedmiotDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<PrzedmiotDTO> event) {
                setSaveEnabled(true);
                PrzedmiotDTO dto = przedmiotyGridPanel.getGrid().getSelectionModel().getSelectedItem();
                Record r = model.getStorePrzedmioty().getRecord(dto);
                if (r.isDirty()) {
                    model.setDirty(true);
                }
            }
        });
        wykladowcaPanel = new WykladowcaGridPanel(model);
        wykladowcaPanel.getEditing().addStartEditHandler(new StartEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajWykladowceEnabled(false);

            }

        });
        wykladowcaPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajWykladowceEnabled(true);

            }
        });
        wykladowcaPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajWykladowceEnabled(true);
//                model.getStoreOcenyStudentow().commitChanges();
            }
        });
        model.getStoreNauczyciele().addStoreAddHandler(new StoreAddHandler<UPrzedmiotDTO>() {

            @Override
            public void onAdd(StoreAddEvent<UPrzedmiotDTO> event) {
                wykladowcaPanel.getBtnDodaj().setEnabled(model.getStoreNauczyciele().size() == 0);
            }
        });
        model.getStoreNauczyciele().addStoreRemoveHandler(new StoreRemoveHandler<UPrzedmiotDTO>() {

            @Override
            public void onRemove(StoreRemoveEvent<UPrzedmiotDTO> event) {
                wykladowcaPanel.getBtnDodaj().setEnabled(model.getStoreNauczyciele().size() == 0);
            }
        });
        westData = new BorderLayoutData(350);
        westData.setCollapsible(true);
        driver.initialize(przedmiotyGridPanel);
//        hlc.add(przedmiotyGridPanel);
//		createCenterPanel();
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        getBorderLayoutContainer().setWestWidget(przedmiotyGridPanel, westData);
        vlc.add(wykladowcaPanel, new VerticalLayoutData(1, 500));//TODO
        getBorderLayoutContainer().setCenterWidget(vlc);
        // nowy.setVisible(false);
        // usun.setVisible(false);

    }

    public void setBtnDodajWykladowceEnabled(boolean b) {
        wykladowcaPanel.getBtnDodaj().setEnabled(b && model.getStoreNauczyciele().size() == 0);
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public void setSaveEnabled(boolean enabled) {
        nowy.setEnabled(enabled);
        usun.setEnabled(enabled);
        zapisz.setEnabled(enabled);
        // zatwierdz.setEnabled(enabled);

        if (!enabled) {

        }
    }

    private void createCenterPanel() {
//		panel = new PrzedmiotyPanel(model);

    }

    public PrzedmiotyGridPanel getGridPanel() {
        return przedmiotyGridPanel;
    }
}
