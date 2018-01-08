package pl.edu.us.client.main;

import java.util.List;

import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements HasHandlers, MenuUiHandlers {

    private final PlaceManager placeManager;

    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        ToolButton getMenu();

        BorderLayoutData getEastData();
    }

    @Inject
    public MenuPresenter(final EventBus eventBus, final MyView view, PlaceManager placeManager) {
        super(eventBus, view);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    public void loadMessages(List<OdbiorcaDTO> nowe) {
        ToolButton tb = getView().getMenu();
        BorderLayoutData eastData = getView().getEastData();
        tb.setToolTip("Masz 2 nowe wiadomości");
        if (nowe == null || nowe.isEmpty()) {
//            eastData.setHidden(true);
//            eastData.setCollapsed(true);
            tb.setVisible(false);
            tb.getToolTip().disable();
        } else {
//            eastData.setHidden(false);
//            eastData.setCollapsed(false);
            tb.setVisible(true);
            tb.getToolTip().enable();
            tb.getToolTip().show();
        }
    }

    @Override
    public PlaceManager getPlaceManager() {
        return placeManager;
    }
}
