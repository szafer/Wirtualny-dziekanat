package pl.edu.us.client.uzytkownik.daneuzytkownika;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;

public class DaneUzytkownikaView extends BaseView<DaneUzytkownkaUiHandlers> implements
    DaneUzytkownikaPresenter.MyView {

    private final DaneUzytkownikaMainPanel panel;
    private final DaneUzytkownikaModel model;

    public DaneUzytkownikaView() {
        model = new DaneUzytkownikaModel();
        panel = new DaneUzytkownikaMainPanel(model);
    }

    @Override
    protected void bindCustomUiHandlers() {
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
    public DaneUzytkownikaModel getModel() {
        return model;
    }

    @Override
    public DaneUzytkownikaPanel getPanel() {
        return panel.getPanel();
    }
}
