package pl.edu.us.client;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.TokenFormatter;

public class AppPlaceManager extends PlaceManagerImpl {

	private final PlaceRequest defaultPlaceRequest;

	@Inject
	public AppPlaceManager(final EventBus eventBus,
			final TokenFormatter tokenFormatter,
			@DefaultPlace String defaultPlaceNameToken) {
		super(eventBus, tokenFormatter);

		this.defaultPlaceRequest = new PlaceRequest(defaultPlaceNameToken);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}
}