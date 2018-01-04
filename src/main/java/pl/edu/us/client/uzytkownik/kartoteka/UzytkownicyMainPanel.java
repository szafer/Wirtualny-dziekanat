package pl.edu.us.client.uzytkownik.kartoteka;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.UserDTO;

public class UzytkownicyMainPanel extends BazowyPanel {

    private final UzytkownicyGridPanel panel;
    private final UzytkownicyModel model;

    @Inject
    public UzytkownicyMainPanel(UzytkownicyModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);

        panel = new UzytkownicyGridPanel(model);

        getBorderLayoutContainer().setCenterWidget(panel);
        panel.getEditing().addStartEditHandler(new StartEditHandler<UserDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<UserDTO> event) {
                setSaveEnabled(false);
            }

        });
        panel.getEditing().addCancelEditHandler(new CancelEditHandler<UserDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<UserDTO> event) {
                setSaveEnabled(true);
            }
        });
        panel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UserDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<UserDTO> event) {
                setSaveEnabled(true);
            }
        });
    }

    public UzytkownicyGridPanel getPanel() {
        return panel;
    }

    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.getStoreUsers().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreUsers().getModifiedRecords().size() > 0);
    }

}
