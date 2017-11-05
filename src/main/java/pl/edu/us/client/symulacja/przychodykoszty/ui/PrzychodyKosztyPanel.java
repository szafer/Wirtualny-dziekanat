package pl.edu.us.client.symulacja.przychodykoszty.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.IntegerField;

import pl.edu.us.client.symulacja.przychodykoszty.PrzychodyKosztyModel;
import pl.edu.us.shared.model.ViPrzychod;

public class PrzychodyKosztyPanel extends ContentPanel {

	private final PrzychodyKosztyModel model;
	private final SymulacjaRokDialog dialog;

	private BorderLayoutContainer borderLayoutContainer;
	private BorderLayoutData northData, centerData, eastData;
	private BorderLayoutData southData;
	private WyliczonePanel wyliczonePanel;
	private IntegerField rokOd, rokDo;
	private TextButton wykresBtn;
	private PrzychodyKosztyGridPanel<ViPrzychod> gridPanel;
	private final ListStore<ViPrzychod> store;

	public interface WyliczoneDriver extends SimpleBeanEditorDriver<ViPrzychod, WyliczonePanel> {
	}
	private WyliczoneDriver wyliczoneDriver = GWT.create(WyliczoneDriver.class);

	@Inject
	public PrzychodyKosztyPanel(PrzychodyKosztyModel model) {
		this.model = model;
		store = model.getStoreViPrzychod();
		dialog = new SymulacjaRokDialog(store);
		setHeadingHtml("Przychody i koszty");
		createForm();
		wyliczoneDriver.initialize(wyliczonePanel);
	}

	private void createForm() {
		borderLayoutContainer = new BorderLayoutContainer();
		borderLayoutContainer.setBorders(true);
		northData = new BorderLayoutData(120);
		northData.setMargins(new Margins(10, 0, 0, 0));
		centerData = new BorderLayoutData();
		southData = new BorderLayoutData(120);
		southData.setMargins(new Margins(10, 0, 0, 0));

		eastData = new BorderLayoutData(500);
		eastData.setMargins(new Margins(10, 0, 0, 0));

		rokOd = new IntegerField();
		rokOd.setWidth(80);

		rokDo = new IntegerField();
		rokDo.setWidth(80);

		Label przychody = new Label("Przychody i koszty w rozbiciu na lata");
		Label okres = new Label("Wybór okresu do analizy od roku ");
		Label doRoku = new Label(" do roku ");

		HorizontalPanel rokPanel = new HorizontalPanel();
		rokPanel.add(okres);
		rokPanel.add(rokOd);
		rokPanel.add(doRoku);
		rokPanel.add(rokDo);
		rokPanel.setSpacing(10);
		VerticalLayoutContainer nlc = new VerticalLayoutContainer();
		nlc.add(przychody, new VerticalLayoutData(500, 30, new Margins(10, 0, 10, 10)));
		nlc.add(rokPanel);

		borderLayoutContainer.setNorthWidget(nlc, centerData);

		gridPanel = new PrzychodyKosztyGridPanel<ViPrzychod>(model.getStorePrzychody(),
				model.getProps());
		gridPanel.setWidth(750);
		borderLayoutContainer.setCenterWidget(gridPanel, centerData);
		Label symulacja = new Label("Symulacja dochodu w zależności od ilości studentów:");
		wyliczonePanel = new WyliczonePanel();
		VerticalLayoutContainer slc = new VerticalLayoutContainer();
		slc.add(symulacja, new VerticalLayoutData(500, 30, new Margins(10)));
		slc.add(new Label(), new VerticalLayoutData(500, 30, new Margins(10)));
		slc.add(wyliczonePanel);
		slc.setHeight(120);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		wykresBtn = new TextButton("Wykres 2");
		wykresBtn.setSize("100", "30");
		vlc.add(wykresBtn);
		borderLayoutContainer.setSouthWidget(slc, southData);
		borderLayoutContainer.setEastWidget(vlc, eastData);
		add(borderLayoutContainer);
	}

	public PrzychodyKosztyGridPanel<ViPrzychod> getGridPanel() {
		return gridPanel;
	}

	public WyliczoneDriver getWyliczoneDriver() {
		return wyliczoneDriver;
	}

	public WyliczonePanel getWyliczonePanel() {
		return wyliczonePanel;
	}

	public TextButton getWykresBtn() {
		return wykresBtn;
	}

	public SymulacjaRokDialog getDialog() {
		return dialog;
	}

	public IntegerField getRokDo() {
		return rokDo;
	}

	public IntegerField getRokOd() {
		return rokOd;
	}

}
