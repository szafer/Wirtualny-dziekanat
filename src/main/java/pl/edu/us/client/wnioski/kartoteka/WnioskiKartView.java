package pl.edu.us.client.wnioski.kartoteka;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;

public class WnioskiKartView extends BaseView<WnioskiKartUiHandlers> implements WnioskiKartPresenter.MyView {

    private WnioskiKartMainPanel panel;
    private WnioskiKartModel model;

    @Inject
    public WnioskiKartView(WnioskiKartMainPanel panel, WnioskiKartModel model) {
        this.panel = panel;
        this.model = model;
    }

    @Override
    protected void bindCustomUiHandlers() {
        super.bindCustomUiHandlers();
        panel.getGridPanel().getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UWniosekDTO>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<UWniosekDTO> event) {
                UWniosekDTO wniosek = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
                model.setWniosek(wniosek);
                if (wniosek != null && wniosek.getId() != null && wniosek.getImage() != null) {
                    Image image = new Image(wniosek.getImage());
                   int width = (int) panel.getWniosekPanel().getBody().getWidth(true);
                    image.setPixelSize(width, image.getHeight() * width / image.getWidth());
                    panel.getWniosekPanel().clear();
                    panel.getWniosekPanel().add(image);
                } else{
                    panel.getWniosekPanel().clear();                    
                    getUiHandlers().pobierzPodgladWniosku(wniosek.getZlozonyWniosek());
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

//    public String getImageData(Byte[] bytes) {
//        byte[] b = new byte[bytes.length];
//        for (int i = 0; i < bytes.length; i++) {
//            b[i] = bytes[i];
//        }
//        String base64 = Base64Utils.toBase64(b); 
//        base64 = "data:image/png;base64,"+base64;
//        return base64;
//        String base64 = Base64.encodeBase64String(b);
//        base64 = "data:image/png;base64," + base64;
//        return base64;
//    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public WnioskiKartModel getModel() {
        return model;
    }

    @Override
    public WnioskiKartMainPanel getPanel() {
        return panel;
    }

}
