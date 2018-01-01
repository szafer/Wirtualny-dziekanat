package pl.edu.us.client.przedmioty;

import javax.enterprise.inject.Model;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.Store.Record;
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
                final PrzedmiotDTO p = event.getItem();//cancel();
                if (model.getPrzedmiot() == null || !p.equals(model.getPrzedmiot()))
                    if (model.isDirty()) {
                        ConfirmMessageBox mBox = new ConfirmMessageBox("Zmiana", "Wykonane zmiany zostaną utracone. Czy kontynuować?");
                        DialogHideHandler hideHandler = new DialogHideHandler() {

                            @Override
                            public void onDialogHide(DialogHideEvent hevent) {
//                            String message = Format.substitute(" '{0}' ", hevent.getHideButton());
                                switch (hevent.getHideButton()) {
                                case YES:
                                    Info.display("Komunikat", "Utracono zmiany");
//                                if (event.getSelectedItem() != null) {
                                    model.setPrzedmiot(event.getItem());
                                    panel.setBtnDodajWykladowceEnabled(true);
//                                    getUiHandlers().pobierzStudentow();
//                                }
                                    break;

                                case NO:
                                    Info.display("Komunikat", "Anulowano");

                                    panel.getGridPanel().getGrid().getSelectionModel().deselect(p);
                                    panel.getGridPanel().getGrid().getSelectionModel().select(model.getPrzedmiot(), false);
//                                    panel.getGridPanel().getGrid().getSelectionModel().getSelection().clear();
//                                event.cancel();
                                    break;
                                }
                            }
                        };
                        mBox.addDialogHideHandler(hideHandler);
                        mBox.show();
                    }
            }
        });
        panel.getGridPanel().getGrid().getSelectionModel().addSelectionHandler(new SelectionHandler<PrzedmiotDTO>() {

            @Override
            public void onSelection(SelectionEvent<PrzedmiotDTO> event) {
                if (event.getSelectedItem() != null) {
                    model.setPrzedmiot(event.getSelectedItem());
                    panel.setBtnDodajWykladowceEnabled(true);
//                    getUiHandlers().pobierzStudentow();
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
