package pl.edu.us.client.uzytkownik.haslozmiana;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.shared.model.User;

public class PassChangeView extends ViewWithUiHandlers<PassChangeUiHandlers>
    implements PassChangePresenter.MyView {

    private final PassChangeModel model;
    private final PassChangePanel panel;
    private BorderLayoutContainer borderLayoutContainer;
    private BorderLayoutData northData;
    private BorderLayoutData centerData;
    Viewport viewport;
    SimpleContainer top;

    @Inject
    public PassChangeView(PassChangeModel model, PassChangePanel panel) {
        this.model = model;
        this.panel = panel;
        panel.getZmiana().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().onZmianaClicked();
            }
        });
        panel.getPowrot().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().onPowrotClicked();
            }
        });
//        panel.getOldPassword().addValueChangeHandler(new ValueChangeHandler<String>() {
//
//            @Override
//            public void onValueChange(ValueChangeEvent<String> event) {
//                getUiHandlers().sprawdzCzyStareHasloJestPoprawne(event.getValue());
//            }
//        });
        panel.setResize(false);
        northData = new BorderLayoutData(50);
        northData.setCollapsible(false);

        top = new SimpleContainer();
        top.setStyleName("row");
        top.setId("top");

        borderLayoutContainer = new BorderLayoutContainer();
        borderLayoutContainer.setBorders(false);
        borderLayoutContainer.setNorthWidget(top, northData);
//        centerData = new MarginData();
        CenterLayoutContainer clc = new CenterLayoutContainer();
        panel.setResize(false);
        clc.add(panel);
        clc.setId("tlo");
        clc.setHeight(500);
        clc.setWidth(700);
        borderLayoutContainer.setCenterWidget(clc);

        SimpleContainer obrazek = new SimpleContainer();
        obrazek.setStylePrimaryName("mainbg");
        borderLayoutContainer.setSouthWidget(obrazek, new BorderLayoutData(200));
//        borderLayoutContainer.setId("tlo");
        viewport = new Viewport();
        viewport.setBorders(false);
//        viewport.add(top/*, new MarginData(new Margins(50))*/);
        Margins mg = new Margins();
        mg.setTop(50);
//        MarginData md = new MarginData(mg);
//        viewport.add(panel/*, md*/);
        viewport.add(borderLayoutContainer);
        viewport.setId("Rejestracja_View");
    }

    @Override
    public Widget asWidget() {
        return viewport;
    }

//    @Override
//    protected void bindCustomUiHandlers() {
//        panel.getZatwierdz().addSelectHandler(new SelectHandler() {
//
//            @Override
//            public void onSelect(SelectEvent event) {
//
//            }
//        });
//    }

    @Override
    public PassChangeModel getModel() {
        return model;
    }

    @Override
    public PassChangePanel getPanel() {
        return panel;
    }

//    @Override
//    public Widget asWidget() {
//        return panel;
//    }
//
//    @Override
//    public void setUiHandlers(RejestracjaUiHandlers uiHandlers) {
//        this.handlers = uiHandlers;
//    }

    @Override
    public void addToSlot(Object slot, IsWidget content) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        // TODO Auto-generated method stub

    }

    @Override
    public void niePoprawneHaslo() {
        Info.display("Stare hasło", "Hasło jest niepoprawne");
        panel.getOldPassword().clear();
    }

}
