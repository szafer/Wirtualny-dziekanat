package pl.edu.us.client.main;

import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements HasHandlers, MenuUiHandlers {

	private final PlaceManager placeManager;
	public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {

	}

	@Inject
	public MenuPresenter(final EventBus eventBus, final MyView view, PlaceManager placeManager) {
		super(eventBus, view);
		this.placeManager = placeManager;
		getView().setUiHandlers(this);
	}
}
