package pl.edu.us.client.kierunki;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;

public class KierunkiView extends BaseView<KierunkiUiHandlers> implements KierunkiPresenter.MyView {

    private final KierunkiPanel panel;
    private final KierunkiModel model;

    @Inject
    public KierunkiView(KierunkiPanel panel, KierunkiModel model) {
        this.model = model;
        this.panel = panel;
        // panel.setHeadingHtml("AdminPanel");
        // panel.setHeight("100%");
        // panel.setId("adminPanel");

        // setFrame(true);
        // setHeading("aaaaaaaaa");
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
    public KierunkiModel getModel() {
        return model;
    }

    @Override
    public KierunkiPanel getPanel() {
        return panel;
    }
}
