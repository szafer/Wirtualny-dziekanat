package pl.edu.us.client.kierunki;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;

import pl.edu.us.client.kierunki.ui.KierunkiGridPanel;
import pl.edu.us.client.kierunki.ui.przedmioty.PrzedmiotyPanel;
import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.model.Kierunek;

public class KierunkiPanel extends BazowyPanel {
	private PrzedmiotyPanel panel;
	private final KierunkiModel model;
	private KierunkiGridPanel gridPanel;
	private BorderLayoutData westData;

	interface KierunkiDriver extends SimpleBeanEditorDriver<Kierunek, KierunkiGridPanel> {

	}

	private KierunkiDriver driver = GWT.create(KierunkiDriver.class);

	@Inject
	public KierunkiPanel(final KierunkiModel model) {
		this.model = model;
		gridPanel = new KierunkiGridPanel(model.getStoreKierunki(), model.getKierProp());
		gridPanel.getEditing().addStartEditHandler(new StartEditHandler<Kierunek>() {

			@Override
			public void onStartEdit(StartEditEvent<Kierunek> event) {
				setSaveEnabled(false);

			}
		});
		gridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<Kierunek>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<Kierunek> event) {
				// setSaveEnabled(true);

			}
		});
		westData = new BorderLayoutData(470);
		westData.setCollapsible(true);
		driver.initialize(gridPanel);
		createCenterPanel();
		getBorderLayoutContainer().setCenterWidget(panel);
		getBorderLayoutContainer().setWestWidget(gridPanel, westData);
		// nowy.setVisible(false);
		// usun.setVisible(false);
		nowy.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				Kierunek s1 = new Kierunek();
				int size = 0;
				for (Kierunek s : model.getStoreKierunki().getAll()) {
					if (s.getId() != null && s.getId() > 0)
						size = s.getId();
				}
				size++;
				s1.setId(size);
				gridPanel.getEditing().cancelEditing();
				model.getStoreKierunki().add(0, s1);
				int row = model.getStoreKierunki().indexOf(s1);
				gridPanel.getEditing().startEditing(new GridCell(row, 0));
			}
		});
		usun.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				try {
					Kierunek edited = driver.flush();
					if (edited == null)
						return;
					model.getStoreKierunki().remove(edited);
					model.getDoUsuniecia().add(edited);

				} catch (IllegalStateException e) {
					return;
				}
			}
		});
		zatwierdz.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				model.getStoreKierunki().commitChanges();
				setSaveEnabled(true);
			}
		});

	}

	public void setSaveEnabled(boolean enabled) {
		nowy.setEnabled(enabled);
		usun.setEnabled(enabled);
		zapisz.setEnabled(enabled);
		// zatwierdz.setEnabled(enabled);

		if (!enabled) {

		}
	}

	private void createCenterPanel() {
		panel = new PrzedmiotyPanel(model);

	}

	public KierunkiGridPanel getGridPanel() {
		return gridPanel;
	}
}
