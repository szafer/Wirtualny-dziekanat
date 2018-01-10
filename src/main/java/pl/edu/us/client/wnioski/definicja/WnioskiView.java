package pl.edu.us.client.wnioski.definicja;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent.StoreDataChangeHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;

public class WnioskiView extends BaseView<WnioskiUiHandlers> implements WnioskiPresenter.MyView {

    private WnioskiMainPanel panel;
    private WnioskiModel model;

    @Inject
    public WnioskiView(WnioskiMainPanel panel, WnioskiModel model) {
        this.panel = panel;
        this.model = model;
    }

    @Override
    protected void bindCustomUiHandlers() {
        super.bindCustomUiHandlers();
        panel.getGridPanel().getGrid().getSelectionModel().addSelectionHandler(new SelectionHandler<WniosekDTO>() {

            @Override
            public void onSelection(SelectionEvent<WniosekDTO> event) {
                WniosekDTO wniosek = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
                model.setWniosek(wniosek);
                if (wniosek.getObraz() != null) {
                    panel.ustawObraz(wniosek.getObraz());
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
    public WnioskiMainPanel getPanel() {
        return panel;
    }

    @Override
    public WnioskiModel getModel() {
        return model;
    }
}
