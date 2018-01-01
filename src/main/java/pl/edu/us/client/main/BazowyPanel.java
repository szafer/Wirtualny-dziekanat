package pl.edu.us.client.main;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class BazowyPanel extends ContentPanel {

	private BorderLayoutContainer borderLayoutContainer;
	private BorderLayoutData southData;
	
	public TextButton nowy, usun, zatwierdz, zapisz, anuluj, zamknij;
	@Inject
	public BazowyPanel() {
		super();
		setHeaderVisible(false);
		setBorders(false);
		// setFrame(true);
		// setBodyStyle("backgroundColor: #ADD8E6;");
		borderLayoutContainer = new BorderLayoutContainer();
		borderLayoutContainer.setBorders(true);
		southData = new BorderLayoutData(30);

		ContentPanel panelEnd = new ContentPanel();
		panelEnd.setButtonAlign(BoxLayoutPack.END);
		panelEnd.setHeaderVisible(false);
		nowy = new TextButton("Nowy");
		nowy.setBorders(true);
		nowy.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

			}
		});
		nowy.setWidth(80);
		panelEnd.addButton(nowy);
		usun = new TextButton("Usuń");
		usun.setBorders(true);
		usun.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

			}
		});
		usun.setWidth(80);
		panelEnd.addButton(usun);
		zatwierdz = new TextButton("Zatwierdź");
		zatwierdz.setBorders(true);
		zatwierdz.setWidth(80);
		panelEnd.addButton(zatwierdz);

		zapisz = new TextButton("Zapisz");
		zapisz.setBorders(true);
		zapisz.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

			}
		});
		zapisz.setWidth(80);
		panelEnd.addButton(zapisz);
		anuluj = new TextButton("Anuluj");
		anuluj.setBorders(true);
		anuluj.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

			}
		});
		anuluj.setWidth(80);
		panelEnd.addButton(anuluj);
		zamknij = new TextButton("Zamknij");
		zamknij.setBorders(true);
		zamknij.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

			}
		});
		zamknij.setWidth(80);
		panelEnd.addButton(zamknij);

		borderLayoutContainer.setSouthWidget(panelEnd, southData);
		this.add(borderLayoutContainer);
	}

	public BorderLayoutContainer getBorderLayoutContainer() {
		return borderLayoutContainer;
	}

	public TextButton getAnuluj() {
		return anuluj;
	}

	public TextButton getZamknij() {
		return zamknij;
	}

	public TextButton getZapisz() {
		return zapisz;
	}

	public TextButton getZatwierdz() {
		return zatwierdz;
	}
}
