package pl.edu.us.client.przedmioty.ui;

import java.util.Date;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreAddEvent.StoreAddHandler;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent.StoreRemoveHandler;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.client.przedmioty.PrzedmiotyModel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Semestr;

public class PrzedmiotyMainPanel extends BazowyPanel {
//	private PrzedmiotyPanel panel;
    private final PrzedmiotyModel model;
    private PrzedmiotyGridPanel przedmiotyPanel;
    private StudenciGridPanel studenciPanel;
    private WykladowcaGridPanel wykladowcaPanel;
    private BorderLayoutData westData;
    private static int AUTO_ID = 0;

    @Inject
    public PrzedmiotyMainPanel(final PrzedmiotyModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        przedmiotyPanel = new PrzedmiotyGridPanel(model.getStorePrzedmioty(), model.getPrzedmiotProp());
        wykladowcaPanel = new WykladowcaGridPanel(model);
        studenciPanel = new StudenciGridPanel(model);
        przedmiotyPanel.getEditing().addStartEditHandler(new StartEditHandler<PrzedmiotDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<PrzedmiotDTO> event) {
                setSaveEnabled(false);

            }
        });
        przedmiotyPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<PrzedmiotDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<PrzedmiotDTO> event) {
                setSaveEnabled(true);
            }
        });
        przedmiotyPanel.getBtnDodaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                PrzedmiotDTO przedmiot = new PrzedmiotDTO();
                przedmiot.setId(--AUTO_ID);
                przedmiotyPanel.getEditing().cancelEditing();
                model.getStorePrzedmioty().add(0, przedmiot);
            }
        });
//        wykladowcaPanel.getEditing().addStartEditHandler(new StartEditHandler<UPrzedmiotDTO>() {
//
//            @Override
//            public void onStartEdit(StartEditEvent<UPrzedmiotDTO> event) {
//                setBtnDodajWykladowceEnabled(false);
//                setSaveEnabled(false);
//            }
//
//        });
//        wykladowcaPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UPrzedmiotDTO>() {
//
//            @Override
//            public void onCancelEdit(CancelEditEvent<UPrzedmiotDTO> event) {
//                setBtnDodajWykladowceEnabled(true);
//                setSaveEnabled(true);
//            }
//        });
//        wykladowcaPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UPrzedmiotDTO>() {
//
//            @Override
//            public void onCompleteEdit(CompleteEditEvent<UPrzedmiotDTO> event) {
//                setBtnDodajWykladowceEnabled(true);
//                setSaveEnabled(true);
//            }
//        });
        wykladowcaPanel.getComboUzytkownik().addSelectionHandler(new SelectionHandler<UserDTO>() {

            @Override
            public void onSelection(SelectionEvent<UserDTO> event) {
                CellSelectionEvent<UserDTO> sel = (CellSelectionEvent<UserDTO>) event;
                UserDTO user = event.getSelectedItem();
                Info.display("Wykładowca", "Wybrano: " + user.toString());
                zapisz.setEnabled(true);
            }
        });
        wykladowcaPanel.getDateCell().getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {

            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                UPrzedmiotDTO p = wykladowcaPanel.getGrid().getSelectionModel().getSelectedItem();
                p.setSemestr(event.getValue().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
//                model.getPrzedmiot().setWykladowca(p);
                setSaveEnabled(true);
                zapisz.setEnabled(true);
            }
        });
        wykladowcaPanel.getBtnDodaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                UPrzedmiotDTO przedmiot = new UPrzedmiotDTO();
                przedmiot.setDataSemestru(new Date());
                przedmiot.setPrzedmiot(model.getPrzedmiot());
                przedmiot.setSemestr(przedmiot.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
                model.getPrzedmiot().setWykladowca(przedmiot);

//                wykladowcaPanel.getEditing().cancelEditing();
                model.getStoreNauczyciele().add(0, przedmiot);

                int row = model.getStoreNauczyciele().indexOf(przedmiot);
//                wykladowcaPanel.getEditing().startEditing(new GridCell(row, 0));
            }
        });
        studenciPanel.getEditing().addStartEditHandler(new StartEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajStudentaEnabled(false);
                setSaveEnabled(false);
            }

        });
        studenciPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajStudentaEnabled(true);
                setSaveEnabled(true);
            }
        });
        studenciPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UPrzedmiotDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<UPrzedmiotDTO> event) {
                setBtnDodajStudentaEnabled(true);
                setSaveEnabled(true);
            }
        });
        studenciPanel.getComboUzytkownik().addSelectionHandler(new SelectionHandler<UserDTO>() {

            @Override
            public void onSelection(SelectionEvent<UserDTO> event) {
                CellSelectionEvent<UserDTO> sel = (CellSelectionEvent<UserDTO>) event;
                UserDTO user = event.getSelectedItem();
                Info.display("Studenci", "Wybrano: " + user.toString());
                zapisz.setEnabled(true);
            }
        });
        studenciPanel.getDateCell().getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {

            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                UPrzedmiotDTO p = studenciPanel.getGrid().getSelectionModel().getSelectedItem();
                p.setSemestr(event.getValue().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
                setSaveEnabled(true);
                zapisz.setEnabled(true);                
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
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        getBorderLayoutContainer().setWestWidget(przedmiotyPanel, westData);
        vlc.add(wykladowcaPanel, new VerticalLayoutData(1, -1));
        vlc.add(studenciPanel, new VerticalLayoutData(1, 1));
        getBorderLayoutContainer().setCenterWidget(vlc);
    }

    public void setBtnDodajStudentaEnabled(boolean b) {
        studenciPanel.getBtnDodaj().setEnabled(b);
    }

    public void setBtnDodajWykladowceEnabled(boolean b) {
        wykladowcaPanel.getBtnDodaj().setEnabled(b && model.getStoreNauczyciele().size() == 0);
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.isDirty());
        anuluj.setEnabled(model.isDirty());
    }

    public PrzedmiotyGridPanel getGridPanel() {
        return przedmiotyPanel;
    }
}
