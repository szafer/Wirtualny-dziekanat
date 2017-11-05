package pl.edu.us.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import pl.edu.us.client.main.MainPagePresenter;

@GinModules({ ClientModule.class })
public interface AppGinjector extends Ginjector {
	PlaceManager getPlaceManager();

	EventBus getEventBus();
	AsyncProvider<MainPagePresenter> getMainPagePresenter();

}