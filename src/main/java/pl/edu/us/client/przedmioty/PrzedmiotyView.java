package pl.edu.us.client.przedmioty;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.przedmioty.ui.PrzedmiotyMainPanel;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;

public class PrzedmiotyView extends BaseView<PrzedmiotyUiHandlers> implements PrzedmiotyPresenter.MyView {

    private final PrzedmiotyMainPanel panel;
    private final PrzedmiotyModel model;

    @Inject
    public PrzedmiotyView(PrzedmiotyMainPanel panel, PrzedmiotyModel model) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    protected void bindCustomUiHandlers() {
        panel.getGridPanel().getGrid().getSelectionModel().addBeforeSelectionHandler(new BeforeSelectionHandler<PrzedmiotDTO>() {

            @Override
            public void onBeforeSelection(final BeforeSelectionEvent<PrzedmiotDTO> event) {
                final PrzedmiotDTO akt = event.getItem();//cancel();
                final PrzedmiotDTO prev = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
                Boolean dirty = model.getStoreNauczyciele().getModifiedRecords().size() > 0;
                if (prev != null && !akt.equals(prev) && dirty) {
                    panel.getGridPanel().getGrid().getSelectionModel().setLocked(true);
                    ConfirmMessageBox mBox = new ConfirmMessageBox("Zmiana", "Wykonane zmiany zostaną utracone. Czy kontynuować?");
                    DialogHideHandler hideHandler = new DialogHideHandler() {

                        @Override
                        public void onDialogHide(DialogHideEvent hevent) {
                            switch (hevent.getHideButton()) {
                            case YES:
                                Info.display("Komunikat", "Utracono zmiany");
                                model.setPrzedmiot(akt);
                                panel.setBtnDodajWykladowceEnabled(true);
                                panel.getGridPanel().getGrid().getSelectionModel().setLocked(false);
                                panel.getGridPanel().getGrid().getSelectionModel().deselect(prev);
                                break;

                            case NO:
                                Info.display("Komunikat", "Anulowano");
                                panel.getGridPanel().getGrid().getSelectionModel().setLocked(false);
                                panel.getGridPanel().getGrid().getSelectionModel().deselect(akt);
                                panel.getGridPanel().getGrid().getSelectionModel().select(prev, false);
                                panel.setBtnDodajWykladowceEnabled(true);
                                break;
                            }
                        }
                    };
                    mBox.addDialogHideHandler(hideHandler);
                    mBox.show();
                } else {
                    model.setPrzedmiot(akt);
                    panel.setBtnDodajWykladowceEnabled(true);
                }
            }
        });
        panel.getZapisz().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().wykonajZapisz();
            }
        });
        panel.getAnuluj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().wykonajAnuluj();

            }
        });
        panel.getZamknij().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().wykonajZamknij();
            }
        });
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public PrzedmiotyModel getModel() {
        return model;
    }

    @Override
    public PrzedmiotyMainPanel getPanel() {
        return panel;
    }
}
