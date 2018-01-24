package pl.edu.us.client.wiadomosci.ui.center;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.client.wiadomosci.ui.wiadomosc.WiadomoscDialog;
import pl.edu.us.shared.dto.UserDTO;

public class WiadomosciCenterPanel extends ContentPanel {

    private WiadomosciModel model;
    private OdebraneGridPanel odebranePanel;
    private NadawcaGridPanel nadawcaPanel;
    ContentPanel top = new ContentPanel();
    private TextButton btnOdpowiedz = new TextButton("Odpowiedz");
    private TextButton btnNowa = new TextButton("Nowa wiadomość");
    private BorderLayoutData northData;
    private TextArea wiadomoscPanel;
    BorderLayoutContainer blc = new BorderLayoutContainer();
//    private OdebraneGridPanel gridPanel;
    private WiadomoscDialog dialog;

    @Inject
    public WiadomosciCenterPanel(final WiadomosciModel model) {
        this.model = model;
        setHeadingText("Odebrane");
        dialog = new WiadomoscDialog(model);
        nadawcaPanel = new NadawcaGridPanel(model);
        odebranePanel = new OdebraneGridPanel(model);

        northData = new BorderLayoutData(600);
        northData.setCollapsible(true);
        northData.setMinSize(400);
        blc.setNorthWidget(odebranePanel);

        wiadomoscPanel = new TextArea();
        wiadomoscPanel.setReadOnly(true);
        wiadomoscPanel.setHeight(600);
        blc.setCenterWidget(wiadomoscPanel);
        top.setHeaderVisible(false);
        top.setHeight(600);
        top.add(odebranePanel);
//        add(blc);
        ContentPanel bottom = new ContentPanel();
        bottom.setHeaderVisible(false);
        bottom.setHeight(100);

        ToolBar tb = new ToolBar();
        tb.add(btnNowa);
        tb.add(btnOdpowiedz);

        ToolBar bb = new ToolBar();
        bb.add(new FieldLabel(null, "Treść"));
        VerticalLayoutContainer hlc = new VerticalLayoutContainer();
        hlc.add(tb, new VerticalLayoutData(1, -1));
        hlc.add(top, new VerticalLayoutData(1, -1));
        hlc.add(bb, new VerticalLayoutData(1, -1));
        hlc.add(wiadomoscPanel, new VerticalLayoutData(1, 1));

        add(hlc);

        btnNowa.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                dialog.initialState();
                dialog.show();
                dialog.center();
            }
        });
        btnOdpowiedz.setEnabled(false);
        btnOdpowiedz.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                dialog.initialState();
                dialog.show();
                dialog.center();
                if (odebranePanel.getGrid().getSelectionModel().getSelectedItem() != null) {
                    Integer userId = odebranePanel.getGrid().getSelectionModel().getSelectedItem().getNadawca().getUserId();
                    UserDTO userDto = null;
                    for (UserDTO uDto : model.getStoreOdbiorcy().getAll()) {
                        if (uDto.getId() == userId) {
                            userDto = uDto;
                            break;
                        }
                    }
                    if (userDto != null) {
                        dialog.getGrid().getSelectionModel().select(false, userDto);
                    }
                    dialog.getTxtTemat().setText("Re:" + odebranePanel.getGrid().getSelectionModel().getSelectedItem().getNadawca().getTemat());
                    dialog.getTxtWiadomosc().setText("\nRe:" + odebranePanel.getGrid().getSelectionModel().getSelectedItem().getNadawca().getWiadomosc());
                }
            }
        });
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
        setHeadingText("Odebrane");
        btnOdpowiedz.setEnabled(odebranePanel.getGrid().getSelectionModel().getSelectedItem() != null);
    }

    public void ustawWyslane() {
        top.clear();
        top.add(nadawcaPanel);
        setHeadingText("Wysłane");
        btnOdpowiedz.setEnabled(false);
    }

    public WiadomoscDialog getDialog() {
        return dialog;
    }

    public TextButton getBtnOdpowiedz() {
        return btnOdpowiedz;
    }
}
