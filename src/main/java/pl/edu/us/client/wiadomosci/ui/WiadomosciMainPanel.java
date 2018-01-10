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
        initialState();
        treePanel = new WiadomosciTreePanel(model);

        westData = new BorderLayoutData(500);
        westData.setCollapsible(true);

        getBorderLayoutContainer().setWestWidget(treePanel, westData);

        centerPanel = new WiadomosciCenterPanel(model);
        getBorderLayoutContainer().setCenterWidget(centerPanel);

//        gridPanel.getEditing().addStartEditHandler(new StartEditHandler<UWniosekDTO>() {
//
//            @Override
//            public void onStartEdit(StartEditEvent<UWniosekDTO> event) {
//                if (model.getWniosek() != null) {
//                    if (model.getWniosek().getStatus() != StatusWniosku.OCZEKJACY) {
//                        gridPanel.getEditing().cancelEditing();
//                    }
//                }
//                setSaveEnabled(false);
//            }
//
//        });
//        gridPanel.getEditing().addCancelEditHandler(new CancelEditHandler<UWniosekDTO>() {
//
//            @Override
//            public void onCancelEdit(CancelEditEvent<UWniosekDTO> event) {
//                setSaveEnabled(true);
//            }
//        });
//        gridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<UWniosekDTO>() {
//
//            @Override
//            public void onCompleteEdit(CompleteEditEvent<UWniosekDTO> event) {
//                setSaveEnabled(true);
//            }
//        });
//        odebranePanel.getBtnDodaj().addSelectHandler(new SelectHandler() {
//
//            @Override
//            public void onSelect(SelectEvent event) {
//                UWniosekDTO wniosek = new UWniosekDTO();
//                wniosek.setId(--AUTO_ID);
//                wniosek.setDataZlozenia(new Date());
//                wniosek.setStatus(StatusWniosku.OCZEKJACY);
//                wniosek.setUzytkownik(model.getUser());
////                model.getStoreWnioskiUzytkownika().add(0, wniosek);
////
////                gridPanel.getComboTyp().setReadOnly(false);
//            }
//        });
    }

//    private WiadomosciCenterPanel createCenterPanel() {
//         cp = new WiadomosciCenterPanel(model);
//
//        wniosekPanel = new ContentPanel();
//        ToolBar tb = new ToolBar();
//        tb.add(btnDrukuj);
//        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//        vlc.add(tb, new VerticalLayoutData(1, -1));
//        vlc.add(wniosekPanel, new VerticalLayoutData(1, 1));
//        cp.add(vlc);
//        return cp;
//    }

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
