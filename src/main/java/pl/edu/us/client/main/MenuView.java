package pl.edu.us.client.main;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.widget.core.client.menu.MenuBar;

public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {

    private final MenuBar menu;

    @Inject
    MenuView(MenuBuilder menuBuilder) {
        menu = menuBuilder.build();//TODO budować widok w zależności od zalogowanego uzytkownika
    }

    @Override
    public Widget asWidget() {
        return getMenu();
    }

    private MenuBar getMenu() {
        return menu;
    }
}
