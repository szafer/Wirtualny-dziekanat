package pl.edu.us.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.button.IconButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.NameTokens;

public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {

    private final MenuBar menu;
    private BorderLayoutContainer topPanel;
//    private MenuBar tb;
    private ToolBar tbr;
    ToolButton tb = new ToolButton(ToolButton.QUESTION);
    BorderLayoutData eastData = new BorderLayoutData(100);

    public interface Resources extends ClientBundle {

        public Resources INSTANCE = GWT.create(Resources.class);

        @Source("email.jpg")
        ImageResource email();

        @Source("email2.jpg")
        ImageResource email2();
    }

    @Inject
    MenuView(MenuBuilder menuBuilder) {
        menu = menuBuilder.build();
        topPanel = new BorderLayoutContainer();
        topPanel.setCenterWidget(menu);
//        eastData.setHidden(true);
//        eastData.setCollapsed(true);
        
        IconButton email = new IconButton("background-image:url(../email.jpg);");
        tbr = new ToolBar();
        Image image = new Image(Resources.INSTANCE.email2());//To dziala ale zle wyglada
        Menu mi = new Menu();
        MenuBarItem mbr = new MenuBarItem("", mi);
        mbr.setStyleName(".customImgButton");
        menu.add(mbr);
//        email.setIcon(Resources.INSTANCE.email());
//        mi.setIcon(Resources.INSTANCE.email());
        tbr.add(tb);
        CenterLayoutContainer cp = new CenterLayoutContainer();
        cp.add(tb);
        topPanel.setEastWidget(cp, eastData);
        tb.setToolTip("");
        tb.setVisible(false);
        tb.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                getUiHandlers().getPlaceManager().revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.wiadomosci).build());
            }
        });
    }

    @Override
    public Widget asWidget() {
        return topPanel;
    }

    @Override
    public ToolButton getMenu() {
        return tb;
    }

    @Override
    public BorderLayoutData getEastData() {
        return eastData;
    }
}
