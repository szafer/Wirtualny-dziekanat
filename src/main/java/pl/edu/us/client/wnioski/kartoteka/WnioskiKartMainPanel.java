package pl.edu.us.client.wnioski.kartoteka;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;
import pl.edu.us.shared.enums.TypWniosku;

public class WnioskiKartMainPanel extends BazowyPanel {

    private BorderLayoutData eastData;
    private WnioskiKartModel model;
    private WnioskiKartGridPanel gridPanel;
    private ContentPanel rightContainer;
    private ContentPanel wniosekPanel;
    private TextButton btnDrukuj = new TextButton("Drukuj");

    @Inject
    public WnioskiKartMainPanel(final WnioskiKartModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();

        gridPanel = new WnioskiKartGridPanel(model);

        getBorderLayoutContainer().setCenterWidget(gridPanel);

//        eastData = new BorderLayoutData(700);
//        eastData.setCollapsible(true);
//        rightContainer = createRightPanel();
//        getBorderLayoutContainer().setEastWidget(rightContainer, eastData);
        gridPanel.getEditing().addStartEditHandler(new StartEditHandler<UWniosekDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UWniosekDTO> event) {
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
        gridPanel.getComboStatus().addSelectionHandler(new SelectionHandler<StatusWniosku>() {

            @Override
            public void onSelection(SelectionEvent<StatusWniosku> event) {
                if (gridPanel.getGrid().getSelectionModel().getSelectedItem() != null) {
                    UWniosekDTO dto = gridPanel.getGrid().getSelectionModel().getSelectedItem();
                    Record r = model.getStoreWnioski().getRecord(dto);
                    r.addChange(model.getWnioskiUzProp().dataRozpatrzenia(), new Date());
                    if (dto.getWniosek().getTyp() == TypWniosku.STYPENDIUM && event.getSelectedItem() == StatusWniosku.ZAAKCEPTOWANY) {
                        gridPanel.getBdKwota().setEnabled(true);
                    } else {

                        gridPanel.getBdKwota().setEnabled(false);
                        r.addChange(model.getWnioskiUzProp().kwota(), null);
//                        gridPanel.getBdKwota().setValue(null, true);
                    }
                }
                setSaveEnabled(true);
            }
        });
        gridPanel.getDataRCell().getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {

            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                if (gridPanel.getGrid().getSelectionModel().getSelectedItem() != null) {
                    UWniosekDTO dto = gridPanel.getGrid().getSelectionModel().getSelectedItem();
//                    if (dto.getDataRozpatrzenia() != null && event.getValue() != null && dto.getDataRozpatrzenia().equals(event.getValue())) {
                    Record r = model.getStoreWnioski().getRecord(dto);
                    r.addChange(model.getWnioskiUzProp().dataRozpatrzenia(), event.getValue());
//                    }
                }
                setSaveEnabled(true);
            }
        });
        gridPanel.getBtnDrukuj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                UWniosekDTO dto = gridPanel.getGrid().getSelectionModel().getSelectedItem();
                if (dto != null)
                    Window.open(GWT.getHostPageBaseURL() + "wniosek" + "?id=" + dto.getId(), "_blank", "resizable=yes");
                else
                    Info.display("Drukuj", "Proszę wybrać wniosek");
            }
        });
        gridPanel.getBtnDrukujKart().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                Window.print();
            }
        });
//        btnDrukuj.setEnabled(false);
    }

    private String budujRequest() {
        String s = "";
        s += "id=" + model.getWniosek().getId();
        return s;
    }
//    private ContentPanel createRightPanel() {
//        ContentPanel cp = new ContentPanel();
//        wniosekPanel = new ContentPanel();
//        ToolBar tb = new ToolBar();
//        tb.add(btnDrukuj);
//        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//        vlc.add(tb, new VerticalLayoutData(1, -1));
//        vlc.add(wniosekPanel, new VerticalLayoutData(1, 1));
//        cp.add(vlc);
//        return cp;
//    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public WnioskiKartGridPanel getGridPanel() {
        return gridPanel;
    }

    public TextButton getBtnDrukuj() {
        return btnDrukuj;
    }

    public ContentPanel getWniosekPanel() {
        return wniosekPanel;
    }

    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.getStoreWnioski().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreWnioski().getModifiedRecords().size() > 0);
    }
}
