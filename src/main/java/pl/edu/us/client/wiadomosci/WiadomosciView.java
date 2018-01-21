package pl.edu.us.client.wiadomosci;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.AppKontekst;
import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.wiadomosci.ui.WiadomosciMainPanel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.wiadomosci.BaseDto;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

public class WiadomosciView extends BaseView<WiadomosciUiHandlers> implements WiadomosciPresenter.MyView {

    private WiadomosciMainPanel panel;
    private WiadomosciModel model;
    private AppKontekst kontekst;

    @Inject
    public WiadomosciView(WiadomosciModel model, AppKontekst kontekst) {
        this.panel = new WiadomosciMainPanel(model);
        this.model = model;
        this.kontekst = kontekst;
    }

    @Override
    protected void bindCustomUiHandlers() {
        super.bindCustomUiHandlers();
        panel.getTreePanel().getTree().getSelectionModel().addSelectionHandler(new SelectionHandler<BaseDto>() {

            @Override
            public void onSelection(SelectionEvent<BaseDto> event) {
                panel.getCenterPanel().getWiadomoscPanel().clear();
                BaseDto dto = event.getSelectedItem();
                if (dto.getName() == "Wysłane") {
                    panel.getCenterPanel().ustawWyslane();
                } else {
                    panel.getCenterPanel().ustawOdebrane();
                }
                if (!dto.getOdebrane().isEmpty() || !dto.getNowe().isEmpty()) {
                    model.getStoreOdebrane().clear();
                    model.getStoreOdebrane().addAll(dto.getOdebrane());
                    model.getStoreOdebrane().addAll(dto.getNowe());
                } else if (!dto.getWyslane().isEmpty()) {
                    model.getStoreWyslane().clear();
                    model.getStoreWyslane().addAll(dto.getWyslane());
                }
            }
        });
        panel.getCenterPanel().getOdebranePanel().getGrid().getSelectionModel().addSelectionHandler(new SelectionHandler<OdbiorcaDTO>() {

            @Override
            public void onSelection(SelectionEvent<OdbiorcaDTO> event) {
                OdbiorcaDTO dto = event.getSelectedItem();
                if (dto.getNadawca() != null && dto.getNadawca().getWiadomosc() != null) {
                    panel.getCenterPanel().getWiadomoscPanel().setText(dto.getNadawca().getWiadomosc());
                    panel.getCenterPanel().getBtnOdpowiedz().setEnabled(true);
                } else {
                    panel.getCenterPanel().getWiadomoscPanel().clear();
                    panel.getCenterPanel().getBtnOdpowiedz().setEnabled(false);
                }
                if (!dto.getOdebrano()) {
                    Date dd = new Date();
                    Date d = new java.sql.Date(dd.getTime());
                    dto.setDataOdbioru(d);
                    dto.setOdebrano(true);
                    model.getStoreOdebrane().update(dto);
                    getUiHandlers().notifyOdebrano(dto);

                    model.odebrane.getNowe().remove(dto);
                    model.odebrane.getOdebrane().add(dto);
                    model.getStoreTypySkrzynek().update(model.odebrane);
                }
            }
        });
        panel.getCenterPanel().getNadawcaPanel().getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<NadawcaDTO>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<NadawcaDTO> event) {
                NadawcaDTO dto = event.getSelection().get(0);
                if (dto.getWiadomosc() != null)
                    panel.getCenterPanel().getWiadomoscPanel().setText(dto.getWiadomosc());
                else
                    panel.getCenterPanel().getWiadomoscPanel().clear();
            }
        });
        panel.getCenterPanel().getDialog().addHideHandler(new HideHandler() {

            @Override
            public void onHide(HideEvent event) {
                String temat = panel.getCenterPanel().getDialog().getTxtTemat().getValue();
                String wiadomosc = panel.getCenterPanel().getDialog().getTxtWiadomosc().getValue();
                List<UserDTO> lista = panel.getCenterPanel().getDialog().getGrid().getSelectionModel().getSelectedItems();
                if (temat != null && !temat.isEmpty() && wiadomosc != null && !wiadomosc.isEmpty()
                    && lista.size() > 0) {
                    NadawcaDTO nad = new NadawcaDTO();
                    nad.setWiadomosc(wiadomosc);
                    nad.setTemat(temat);
                    nad.setUserId(model.getUser().getId());
                    List<OdbiorcaDTO> odbiorcy = new ArrayList<OdbiorcaDTO>();
                    for (UserDTO u : lista) {
                        OdbiorcaDTO o = new OdbiorcaDTO();
                        o.setUserId(u.getId());
                        o.setEmail(u.getPowiadomic());
                        o.setNadawca(nad);
                        odbiorcy.add(o);
                    }
                    nad.setOdbiorcy(odbiorcy);
                    model.setNowaWiadomosc(nad);
                    getUiHandlers().wyslij(nad);
                } else
                    model.setNowaWiadomosc(null);
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
    public WiadomosciModel getModel() {
        return model;
    }

    @Override
    public WiadomosciMainPanel getPanel() {
        return panel;
    }

    @Override
    public void odnotujWyslanie() {
        NadawcaDTO dto = model.getNowaWiadomosc();
        Date dd = new Date();
        Date d = new java.sql.Date(dd.getTime());
        dto.setData(d);
        model.wyslane.getWyslane().add(dto);
        model.getStoreWyslane().add(dto);
        model.getStoreTypySkrzynek().update(model.wyslane);
        model.getStoreTypySkrzynek().update(model.getRoot());

    }
}
