package pl.edu.us.client.kartoteki.pracownik;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class PracownikView extends ViewWithUiHandlers<PracownikUiHandlers> implements PracownikPresenter.MyView {

	PracownikPanel panel;

	public PracownikView() {
		panel = new PracownikPanel();
		panel.setHeadingHtml("Kartoteka Pracowników");
		panel.setHeight("100%");
		panel.setId("pracownicy");

		// setFrame(true);
		// setHeading("aaaaaaaaa");
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
}
