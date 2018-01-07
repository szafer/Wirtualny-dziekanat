package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.Date;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

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

    private ContentPanel createCenterPanel() {
        ContentPanel cp = new ContentPanel();

        wniosekPanel = new ContentPanel();
        ToolBar tb = new ToolBar();
        tb.add(btnDrukuj);
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(wniosekPanel, new VerticalLayoutData(1, 1));
        cp.add(vlc);
        return cp;
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
}
