package pl.edu.us.client.kierunki.ui.przedmioty;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.FramedPanel;

import pl.edu.us.client.kierunki.KierunkiModel;

public class PrzedmiotyPanel extends FramedPanel {

	private final KierunkiModel model;

	@Inject
	public PrzedmiotyPanel(KierunkiModel model) {
		this.model = model;

	}
}
