package pl.edu.us.client.wiadomosci.ui.center;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.form.TextArea;

import pl.edu.us.client.wiadomosci.WiadomosciModel;

public class WiadomosciCenterPanel extends ContentPanel {

    private WiadomosciModel model;
    private OdebraneGridPanel odebranePanel;
    private NadawcaGridPanel nadawcaPanel;

    private BorderLayoutData northData;
    private TextArea wiadomoscPanel;
    BorderLayoutContainer blc = new BorderLayoutContainer();
//    private OdebraneGridPanel gridPanel;

    @Inject
    public WiadomosciCenterPanel(WiadomosciModel model) {
        this.model = model;
        odebranePanel = new OdebraneGridPanel(model);
        nadawcaPanel = new NadawcaGridPanel(model);

        northData = new BorderLayoutData(500);
        northData.setCollapsible(true);

        blc.setNorthWidget(odebranePanel);

        wiadomoscPanel = new TextArea();
        blc.setCenterWidget(wiadomoscPanel);

        add(blc);
    }

    public OdebraneGridPanel getOdebranePanel() {
        return odebranePanel;
    }

    public TextArea getWiadomoscPanel() {
        return wiadomoscPanel;
    }

    public BorderLayoutContainer getBlc() {
        return blc;
    }

    public NadawcaGridPanel getNadawcaPanel() {
        return nadawcaPanel;
    }
}
