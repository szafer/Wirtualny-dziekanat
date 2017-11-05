package pl.edu.us.client.admin;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.client.main.BazowyPanel;

public class AdminPanel extends BazowyPanel {

	BorderLayoutData westData;
	@Inject
	public AdminPanel() {
		westData = new BorderLayoutData(270);
		westData.setCollapsible(true);
		// ListaOsobWidget lista = new ListaOsobWidget();TODO odkomentowac po
		// skonczeniu listaosob
		// super.getBorderLayoutContainer().setWestWidget(lista, westData);
		// super.getBorderLayoutContainer()
	}
}
