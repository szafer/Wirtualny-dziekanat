package pl.edu.us.client.uzytkownik.daneuzytkownika;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.dto.UserDTO;

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
    public UserDTO dajUsera() {
        UserDTO u = model.getUser();
        u.setImie(panel.getPanel().getImie().getValue());
        u.setNazwisko(panel.getPanel().getNazwisko().getValue());
        u.setPlec(panel.getPanel().getPlec().getValue());
        u.setDataUrodzenia(panel.getPanel().getDataUrodzenia().getValue());
        u.setUlica(panel.getPanel().getUlica().getValue());
        u.setNrDomu(panel.getPanel().getNrDomu().getValue());
        u.setNrMieszkania(panel.getPanel().getNrMieszkania().getValue());
        u.setMiasto(panel.getPanel().getMiasto().getValue());
        u.setKodPocztowy(panel.getPanel().getKodPocztowy().getValue());
        u.setEmail(panel.getPanel().getEmail().getValue());
//        u.setLogin(panel.getLogin().getValue());//loginu nie zmieniamy
        u.setPassword(panel.getPanel().getPassword().getValue());
//        u.setRola(panel.getRola().getValue());roli też nie
        return u;
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
