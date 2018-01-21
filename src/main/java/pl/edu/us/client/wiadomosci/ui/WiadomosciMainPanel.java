package pl.edu.us.client.wiadomosci.ui;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.client.wiadomosci.ui.center.WiadomosciCenterPanel;

public class WiadomosciMainPanel extends BazowyPanel {

    private BorderLayoutData westData;
//    private WiadomosciModel model;

    private WiadomosciCenterPanel centerPanel;
    private WiadomosciTreePanel treePanel;

    private static int AUTO_ID = 0;

    @Inject
    public WiadomosciMainPanel(WiadomosciModel model) {
//        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        zapisz.setVisible(false);
        anuluj.setVisible(false);
        initialState();
        treePanel = new WiadomosciTreePanel(model);

        westData = new BorderLayoutData(500);
        westData.setCollapsible(true);

        getBorderLayoutContainer().setWestWidget(treePanel, westData);

        centerPanel = new WiadomosciCenterPanel(model);
        getBorderLayoutContainer().setCenterWidget(centerPanel);
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public void setSaveEnabled(boolean enabled) {
//        zapisz.setEnabled(enabled && model.getStoreWnioskiUzytkownika().getModifiedRecords().size() > 0);
//        anuluj.setEnabled(model.getStoreWnioskiUzytkownika().getModifiedRecords().size() > 0);
    }

    public WiadomosciTreePanel getTreePanel() {
        return treePanel;
    }

    public WiadomosciCenterPanel getCenterPanel() {
        return centerPanel;
    }
}
