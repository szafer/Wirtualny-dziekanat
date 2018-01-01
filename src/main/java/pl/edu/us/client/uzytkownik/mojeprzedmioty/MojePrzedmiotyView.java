package pl.edu.us.client.uzytkownik.mojeprzedmioty;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public class MojePrzedmiotyView extends BaseView<MojePrzedmiotyUiHandlers> implements
    MojePrzedmiotyPresenter.MyView {

    private MojePrzedmiotyMainPanel panel;
    private MojePrzedmiotyModel model;

    @Inject
    public MojePrzedmiotyView() {
        model = new MojePrzedmiotyModel();
        panel = new MojePrzedmiotyMainPanel(model);
    }

    @Override
    protected void bindCustomUiHandlers() {
        panel.getGridPanel().getGrid().getSelectionModel().addSelectionHandler(new SelectionHandler<UPrzedmiotDTO>() {

            @Override
            public void onSelection(SelectionEvent<UPrzedmiotDTO> event) {
                if (event.getSelectedItem() != null) {
                    model.setPrzedmiot(event.getSelectedItem().getPrzedmiot());
                    getUiHandlers().pobierzStudentow();
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
    public MojePrzedmiotyModel getModel() {
        return model;
    }

    @Override
    public MojePrzedmiotyMainPanel getPanel() {
        return panel;
    }

    @Override
    public UserDTO dajUsera() {
        return model.getUser();
    }
}
