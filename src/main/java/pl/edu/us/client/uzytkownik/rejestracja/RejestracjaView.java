package pl.edu.us.client.uzytkownik.rejestracja;

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

import pl.edu.us.shared.dto.UserDTO;

public class RejestracjaView extends ViewWithUiHandlers<RejestracjaUiHandlers>
    implements RejestracjaPresenter.MyView {

    private final RejestracjaModel model;
    private final RejestracjaPanel panel;
    private BorderLayoutContainer borderLayoutContainer;
    private BorderLayoutData northData;
    private BorderLayoutData centerData;
    Viewport viewport;
    SimpleContainer top;

    @Inject
    public RejestracjaView(RejestracjaModel model, RejestracjaPanel panel) {
        this.model = model;
        this.panel = panel;
        panel.getZarejestruj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
//                User u = new User();
//                u.setId(panel.getImie().getValue());
                ustawUsera();
                getUiHandlers().onZarejestrujClicked();
            }
        });
        panel.getPowrot().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().onPowrotClicked();
            }
        });
        panel.getLogin().addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                getUiHandlers().sprawdzCzyLoginWBazie(event.getValue());
            }
        });
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

    protected void ustawUsera() {
        UserDTO u = new UserDTO();
        u.setImie(panel.getImie().getValue());
        u.setNazwisko(panel.getNazwisko().getValue());
        u.setPlec(panel.getPlec().getValue());
        u.setDataUrodzenia(panel.getDataUrodzenia().getValue());
        u.setUlica(panel.getUlica().getValue());
        u.setNrDomu(panel.getNrDomu().getValue());
        u.setNrMieszkania(panel.getNrMieszkania().getValue());
        u.setMiasto(panel.getMiasto().getValue());
        u.setKodPocztowy(panel.getKodPocztowy().getValue());
        u.setEmail(panel.getEmail().getValue());
        u.setLogin(panel.getLogin().getValue());
        u.setPassword(panel.getPassword().getValue());
        u.setRola(panel.getRola().getValue());
        u.setIloscLogowan(1);
        u.setAktywny(true);
        model.setUser(u);
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
    public RejestracjaModel getModel() {
        return model;
    }

    @Override
    public RejestracjaPanel getPanel() {
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

}
