package pl.edu.us.client.main;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public interface MenuUiHandlers extends UiHandlers {
    PlaceManager getPlaceManager();
}
