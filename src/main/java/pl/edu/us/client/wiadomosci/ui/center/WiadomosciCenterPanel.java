package pl.edu.us.client.wiadomosci.ui.center;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextArea;

import pl.edu.us.client.wiadomosci.WiadomosciModel;

public class WiadomosciCenterPanel extends ContentPanel {

    private WiadomosciModel model;
    private OdebraneGridPanel odebranePanel;
    private NadawcaGridPanel nadawcaPanel;
    ContentPanel top = new ContentPanel();

    private BorderLayoutData northData;
    private TextArea wiadomoscPanel;
    BorderLayoutContainer blc = new BorderLayoutContainer();
//    private OdebraneGridPanel gridPanel;

    @Inject
    public WiadomosciCenterPanel(WiadomosciModel model) {
        this.model = model;
        odebranePanel = new OdebraneGridPanel(model);
        nadawcaPanel = new NadawcaGridPanel(model);

        northData = new BorderLayoutData(600);
        northData.setCollapsible(true);
        northData.setMinSize(400);
        blc.setNorthWidget(odebranePanel);

        wiadomoscPanel = new TextArea();
//        wiadomoscPanel.setHeight(400);
        blc.setCenterWidget(wiadomoscPanel);
        top.setHeaderVisible(false);
        top.setHeight(600);
        top.add(odebranePanel);
//        add(blc);
        ContentPanel bottom = new ContentPanel();
        bottom.setHeaderVisible(false);
        bottom.setHeight(100);
        HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
        hlc.add(top, new HorizontalLayoutData(1, -1));
        hlc.add(bottom, new HorizontalLayoutData(1, -1));
        hlc.add(wiadomoscPanel, new HorizontalLayoutData(1, 1));
        add(hlc);
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

    public ContentPanel getCp() {
        return top;
    }

    public void ustawOdebrane() {
        top.clear();
        top.add(odebranePanel);
    }

    public void ustawWyslane() {
        top.clear();
        top.add(nadawcaPanel);
    }

}
