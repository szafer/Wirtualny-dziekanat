package pl.edu.us.client.uzytkownik.mojewnioski;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class MojeWnioskiView extends BaseView<MojeWnioskiUiHandlers> implements MojeWnioskiPresenter.MyView {

    private MojeWnioskiMainPanel panel;
    private MojeWnioskiModel model;

    @Inject
    public MojeWnioskiView(MojeWnioskiMainPanel panel, MojeWnioskiModel model) {
        this.panel = panel;
        this.model = model;
    }

    @Override
    protected void bindCustomUiHandlers() {
        super.bindCustomUiHandlers();
        panel.getGridPanel().getGrid().getSelectionModel().addBeforeSelectionHandler(new BeforeSelectionHandler<UWniosekDTO>() {

            @Override
            public void onBeforeSelection(BeforeSelectionEvent<UWniosekDTO> event) {
                UWniosekDTO wniosek = event.getItem();
                model.setWniosek(wniosek);
                panel.getGridPanel().getComboTyp().setReadOnly(wniosek.getStatus() != StatusWniosku.OCZEKJACY);
            }
        });
        panel.getGridPanel().getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UWniosekDTO>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<UWniosekDTO> event) {
                UWniosekDTO wniosek = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
                model.setWniosek(wniosek);
                if (wniosek != null && wniosek.getId() != null /*&& wniosek.getImage() != null*/) {
//                    Image image = new Image(wniosek.getImage());
//                    int width = (int) panel.getWniosekPanel().getBody().getWidth(true);
//                    image.setPixelSize(width, image.getHeight() * width / image.getWidth());
//                    panel.getWniosekPanel().clear();
//                    panel.getWniosekPanel().add(image);
                    panel.getImie().setText(wniosek.getUzytkownik().getImie());
                    panel.getNazwisko().setText(wniosek.getUzytkownik().getNazwisko());
                    panel.getData().setValue(wniosek.getDataZlozenia());
                    panel.getDataR().setValue(wniosek.getDataRozpatrzenia());
                    panel.getKwota().setValue(wniosek.getKwota());
                    panel.getBtnDrukuj().setEnabled(true);
                } else {
                    panel.getWniosekPanel().clear();
                    panel.getImie().clear();
                    panel.getNazwisko().clear();
                    panel.getData().clear();
                    panel.getDataR().clear();
                    panel.getKwota().clear();
                    panel.getBtnDrukuj().setEnabled(false);
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
    public MojeWnioskiModel getModel() {
        return model;
    }

    @Override
    public MojeWnioskiMainPanel getPanel() {
        return panel;
    }
}
