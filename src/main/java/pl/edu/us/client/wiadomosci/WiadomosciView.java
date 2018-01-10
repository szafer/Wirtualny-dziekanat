package pl.edu.us.client.wiadomosci;

import java.util.Date;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.AppKontekst;
import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.wiadomosci.ui.WiadomosciMainPanel;
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
                BaseDto dto = event.getSelectedItem();
//                model.wyczysc();
                if (dto.getName() == "Wysłane") {
//                    panel.getCenterPanel().getCp().clear();
//                    panel.getCenterPanel().getCp().clear();(panel.getCenterPanel().getNadawcaPanel(), new BorderLayoutData(500));
//
//                    panel.getCenterPanel().getBlc().setNorthWidget(panel.getCenterPanel().getNadawcaPanel(), new BorderLayoutData(500));
                    panel.getCenterPanel().ustawWyslane();
                } else {
                    panel.getCenterPanel().ustawOdebrane();
//                    panel.getCenterPanel().getBlc().setNorthWidget(panel.getCenterPanel().getOdebranePanel(), new BorderLayoutData(500));
                }
                if (!dto.getOdebrane().isEmpty()) {
                    model.getStoreOdebrane().clear();
                    model.getStoreOdebrane().addAll(dto.getOdebrane());
                } else if (!dto.getWyslane().isEmpty()) {
                    model.getStoreWyslane().clear();
                    model.getStoreWyslane().addAll(dto.getWyslane());
                } else if (!dto.getNowe().isEmpty()) {
                    model.getStoreNowe().clear();
                    model.getStoreNowe().addAll(dto.getNowe());
                }
            }
        });
        panel.getCenterPanel().getOdebranePanel().getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<OdbiorcaDTO>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<OdbiorcaDTO> event) {
                OdbiorcaDTO dto = event.getSelection().get(0);
                if (dto.getNadawca() != null && dto.getNadawca().getWiadomosc() != null)
                    panel.getCenterPanel().getWiadomoscPanel().setText(dto.getNadawca().getWiadomosc());
                else
                    panel.getCenterPanel().getWiadomoscPanel().clear();
                if (!dto.getOdebrano()) {

                    dto.setDataOdbioru(new Date());
                    dto.setOdebrano(true);
                    getUiHandlers().notifyOdebrano(dto);

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
//        panel.getGridPanel().getGrid().getSelectionModel().addBeforeSelectionHandler(new BeforeSelectionHandler<UWniosekDTO>() {
//
//            @Override
//            public void onBeforeSelection(BeforeSelectionEvent<UWniosekDTO> event) {
//                UWniosekDTO wniosek = event.getItem();
//                model.setWniosek(wniosek);
//                panel.getGridPanel().getComboTyp().setReadOnly(wniosek.getStatus() != StatusWniosku.OCZEKJACY);
//            }
//        });
//        panel.getGridPanel().getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UWniosekDTO>() {
//
//            @Override
//            public void onSelectionChanged(SelectionChangedEvent<UWniosekDTO> event) {
//                UWniosekDTO wniosek = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
//                model.setWniosek(wniosek);
//                if (wniosek != null && wniosek.getId() != null && wniosek.getImage() != null) {
//                    Image image = new Image(wniosek.getImage());
//                    int width = (int) panel.getWniosekPanel().getBody().getWidth(true);
//                    image.setPixelSize(width, image.getHeight() * width / image.getWidth());
//                    panel.getWniosekPanel().clear();
//                    panel.getWniosekPanel().add(image);
//                } else {
//                    panel.getWniosekPanel().clear();
//                }
//            }
//        });
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
}
