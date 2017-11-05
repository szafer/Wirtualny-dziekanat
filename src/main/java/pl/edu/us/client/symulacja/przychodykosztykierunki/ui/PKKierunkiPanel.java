package pl.edu.us.client.symulacja.przychodykosztykierunki.ui;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.SeriesHighlighter;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawFx;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.fx.client.easing.BounceOut;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.accesproperties.ViPrzychodProperties;
import pl.edu.us.client.symulacja.przychodykoszty.ui.SymulacjaRokDialog;
import pl.edu.us.client.symulacja.przychodykoszty.ui.WyliczonePanel;
import pl.edu.us.client.symulacja.przychodykosztykierunki.PKKierunkiModel;
import pl.edu.us.shared.model.ViPrzychod;

public class PKKierunkiPanel extends ContentPanel {

	private static final ViPrzychodProperties dataAccess = GWT.create(ViPrzychodProperties.class);
	private CheckBox kosztCheck;
	private CheckBox przychodCheck;
	private Boolean czyKoszt, czyPrzychod, slupkowy, liniowy;
	BorderLayoutContainer layoutComplex = new BorderLayoutContainer();
	private FramedPanel panel = new FramedPanel();
	private Chart<ViPrzychod> chart;
	private final ListStore<ViPrzychod> store;
	private LineSeries<ViPrzychod> linePrzychod;
	private LineSeries<ViPrzychod> lineKoszt;
	private BarSeries<ViPrzychod> columnPrzychod;
	private BarSeries<ViPrzychod> columnKoszt;
	private TextButton btnLiniowy, btnSlupkowy;
	// Dane stałe wykresu
	private TextSprite titleHits, titleMonthYear;

	private final PKKierunkiModel model;
	private BorderLayoutContainer borderLayoutContainer;
	private BorderLayoutData northData, centerData;
	private BorderLayoutData southData;

	private IntegerField rokOd, rokDo, rokWykres2, rokWykres3;
	private final SymulacjaRokDialog dialogRok;
	private final SymulacjaKierunkiDialog dialogKierunki;
	// @Ignore
	private TextButton wykresRokBtn, wykresKierunkiBtn;
	private ContentPanel wykresPanel;

	public interface WyliczoneDriver extends SimpleBeanEditorDriver<ViPrzychod, WyliczonePanel> {
	}

	@Inject
	public PKKierunkiPanel(PKKierunkiModel model) {
		this.model = model;
		store = model.getStoreLata();
		dialogRok = new SymulacjaRokDialog(model.getStoreRok());// troche mylące
																// ale to jest
																// store dane
																// roczne
		dialogKierunki = new SymulacjaKierunkiDialog(model.getStoreKierunek());
		setHeadingHtml("Przychody i koszty w rozbiciu na kierunki");
		createForm();
		// driver.initialize(this);
	}

	private void createForm() {
		borderLayoutContainer = new BorderLayoutContainer();
		borderLayoutContainer.setBorders(true);
		northData = new BorderLayoutData(120);
		northData.setMargins(new Margins(10, 0, 0, 0));
		centerData = new BorderLayoutData();
		southData = new BorderLayoutData(120);
		southData.setMargins(new Margins(10, 0, 0, 0));

		rokOd = new IntegerField();
		rokOd.setWidth(80);

		rokDo = new IntegerField();
		rokDo.setWidth(80);
		// panel górny
		Label przychody = new Label("Interpretacja graficzna przychodów i kosztów");
		Label okres = new Label("w rozbiciu na lata od ");
		Label doRoku = new Label(" do ");

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

		// wykres
		wykresPanel = createWykresPanel();
		borderLayoutContainer.setCenterWidget(wykresPanel, centerData);

		// opis i btn wykres 2 i wykres 3
		HorizontalPanel v = new HorizontalPanel();
		v.setSpacing(10);
		Label symulacja2 = new Label("Pokaż rok");
		rokWykres2 = new IntegerField();
		rokWykres2.setWidth(80);
		Label symulacja2a = new Label(" w rozbiciu na miesiące");
		wykresRokBtn = new TextButton("Wykres 2");
		wykresRokBtn.setSize("100", "30");
		wykresRokBtn.setEnabled(false);
		v.add(symulacja2);
		v.add(rokWykres2);
		v.add(symulacja2a);
		v.add(wykresRokBtn);
		v.setSpacing(10);

		HorizontalPanel v1 = new HorizontalPanel();
		v1.setSpacing(10);
		Label symulacja3 = new Label("Pokaż rok");
		rokWykres3 = new IntegerField();
		rokWykres3.setWidth(80);
		Label symulacja3a = new Label(" w rozbiciu na kierunki");
		wykresKierunkiBtn = new TextButton("Wykres 3");
		wykresKierunkiBtn.setSize("100", "30");
		wykresKierunkiBtn.setEnabled(false);
		v1.add(symulacja3);
		v1.add(rokWykres3);
		v1.add(symulacja3a);
		v1.add(wykresKierunkiBtn);
		v1.setSpacing(10);
		VerticalLayoutContainer slc = new VerticalLayoutContainer();
		slc.add(v);
		slc.add(v1);
		slc.setHeight(60);

		borderLayoutContainer.setSouthWidget(slc, southData);
		add(borderLayoutContainer);
	}

	private ContentPanel createWykresPanel() {
		wykresPanel = new ContentPanel();
		czyKoszt = true;
		czyPrzychod = true;
		liniowy = false;
		slupkowy = false;
		// Opisy osi chart
		titleHits = new TextSprite("Przychód / Koszt PLN");
		titleHits.setFontSize(18);
		titleHits.setFill(RGB.GRAY);
		titleMonthYear = new TextSprite("Rok");
		titleMonthYear.setFontSize(18);
		titleMonthYear.setFill(RGB.GRAY);

		wykresPanel.setWidth(900);
		wykresPanel.setHeight(600);
		wykresPanel.setHeadingText("Wykres Panel");

		btnLiniowy = new TextButton("Wykres liniowy");
		btnLiniowy.setWidth(120);
		btnLiniowy.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				onBtnLiniowyClick();
			}
		});

		btnSlupkowy = new TextButton("Wykres słupkowy");
		btnSlupkowy.setWidth(120);
		btnSlupkowy.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (store.size() == 0) {
					Info.display("Info", "Proszę wybrać lata do symulacji ");
				} else {
				slupkowy = true;
				liniowy = false;
				if (chart != null && chart.getParent() != null) {
					chart.removeFromParent();
				}
				ladujColumnChart();
				panel.add(chart);
				panel.forceLayout();
				chart.show();
				chart.redrawChart();
				}
			}
		});
		kosztCheck = new CheckBox();
		kosztCheck.setValue(czyKoszt);
		kosztCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				czyKoszt = kosztCheck.getValue();
			}
		});
		przychodCheck = new CheckBox();
		przychodCheck.setValue(czyPrzychod);
		przychodCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				czyPrzychod = przychodCheck.getValue();
			}
		});
		VBoxLayoutContainer eastPanel = new VBoxLayoutContainer(VBoxLayoutAlign.CENTER);
		eastPanel.add(btnLiniowy);
		eastPanel.add(btnSlupkowy);
		eastPanel.add(new FieldLabel(kosztCheck, "Koszt"));
		eastPanel.add(new FieldLabel(przychodCheck, "Przychód"));

		panel.setLayoutData(new MarginData(0));
		// panel.setHeadingText("Wykre symulacji ");
		panel.setHeaderVisible(false);
		panel.setPixelSize(620, 500);
		panel.setBodyBorder(false);
		// pierwsza inicjalizacja
		chart = new Chart<ViPrzychod>();
		store.addAll(model.getStoreAll().getAll());
		chart.setStore(store);
		panel.add(chart);
		layoutComplex.setEastWidget(eastPanel, new BorderLayoutData(200));
		layoutComplex.setCenterWidget(panel);
		wykresPanel.add(layoutComplex);
		return wykresPanel;
	}

	public void onBtnLiniowyClick() {
		if (store.size() == 0) {
			Info.display("Info", "Proszę wybrać lata do symulacji ");
		} else {
		liniowy = true;
		slupkowy = false;
		if (chart != null && chart.getParent() != null) {
			chart.removeFromParent();
		}
		ladujAreaChart();
		panel.add(chart);
		panel.forceLayout();
		chart.show();
		chart.redrawChart();
		}
	}

	protected void ladujAreaChart() {
		Integer max = findMax();
		PathSprite gridConfig = new PathSprite();
		gridConfig.setStroke(new RGB("#bbb"));
		gridConfig.setFill(new RGB("#ddd"));
		gridConfig.setZIndex(1);
		gridConfig.setStrokeWidth(1);

		NumericAxis<ViPrzychod> axis = new NumericAxis<ViPrzychod>();
		axis.setPosition(Position.LEFT);
		axis.addField(dataAccess.przychod());
		axis.addField(dataAccess.koszt());
		axis.setGridOddConfig(gridConfig);
		axis.setDisplayGrid(true);
		axis.setTitleConfig(titleHits);
		axis.setMinorTickSteps(1);
		axis.setMinimum(0);
		axis.setMaximum(max);

		TextSprite labelConfig = new TextSprite();
		labelConfig.setRotation(315);

		CategoryAxis<ViPrzychod, Integer> catAxis = new CategoryAxis<ViPrzychod, Integer>();
		catAxis.setPosition(Position.BOTTOM);
		catAxis.setField(dataAccess.rok());
		catAxis.setTitleConfig(titleMonthYear);
		catAxis.setDisplayGrid(true);
		catAxis.setLabelConfig(labelConfig);
		catAxis.setLabelOverlapHiding(true);
		// catAxis.setMinorTickSteps(5); ustawia podziałke pomiędzy głównymi
		// odcinkami
		catAxis.setLabelTolerance(20);

		PathSprite highlightLine = new PathSprite();
		highlightLine.setHidden(true);
		highlightLine.addCommand(new MoveTo(0, 0));
		highlightLine.setZIndex(1000);
		highlightLine.setStrokeWidth(5);
		highlightLine.setStroke(new RGB("#444"));
		highlightLine.setOpacity(0.3);

		Sprite marker = Primitives.square(0, 0, 6);
		marker.setFill(new RGB(194, 0, 36));// kwadraty

		linePrzychod = new LineSeries<ViPrzychod>();
		linePrzychod.setYAxisPosition(Position.LEFT);
		linePrzychod.setYField(dataAccess.przychod());
		linePrzychod.setStroke(new RGB(194, 0, 36));
		linePrzychod.setShowMarkers(true);
		linePrzychod.setMarkerConfig(marker);
		linePrzychod.setHighlighting(true);

		marker = Primitives.circle(0, 0, 6);
		marker.setFill(new RGB(240, 165, 10));// koła

		lineKoszt = new LineSeries<ViPrzychod>();
		lineKoszt.setYAxisPosition(Position.LEFT);
		lineKoszt.setYField(dataAccess.koszt());
		lineKoszt.setStroke(new RGB(240, 165, 10));
		lineKoszt.setShowMarkers(true);
		lineKoszt.setSmooth(true);
		lineKoszt.setMarkerConfig(marker);
		lineKoszt.setHighlighting(true);

		marker = Primitives.diamond(0, 0, 6);// wypełnienie
		marker.setFill(new RGB(32, 68, 186));

		Legend<ViPrzychod> legend = new Legend<ViPrzychod>();
		legend.setItemHighlighting(true);
		legend.setItemHiding(true);
		legend.getBorderConfig().setStrokeWidth(0);

		chart = new Chart<ViPrzychod>();
		chart.setStore(store);
		// chart.setWidth(620);
		// chart.setHeight(500);
		// Allow room for rotated labels
		chart.setDefaultInsets(2);
		chart.addAxis(axis);
		chart.addAxis(catAxis);
		if (czyPrzychod)
			chart.addSeries(linePrzychod);
		if (czyKoszt)
			chart.addSeries(lineKoszt);
		chart.setLegend(legend);
		chart.setAnimated(true);
		chart.setDefaultInsets(20);
	}

	private void ladujColumnChart() {
		Integer max = findMax();
		PathSprite grid = new PathSprite();
		grid.setStroke(RGB.GRAY);

		PathSprite white = new PathSprite();
		white.setStroke(RGB.GRAY);

		TextSprite whiteText = new TextSprite();
		whiteText.setFill(RGB.GRAY);
		whiteText.setTextBaseline(TextBaseline.MIDDLE);

		NumericAxis<ViPrzychod> axis = new NumericAxis<ViPrzychod>();
		axis.setPosition(Position.LEFT);
		axis.addField(dataAccess.przychod());
		axis.addField(dataAccess.koszt());
		axis.setGridDefaultConfig(grid);
		axis.setTitleConfig(titleHits);
		axis.setDisplayGrid(true);
		axis.setAxisConfig(white);
		axis.setLabelConfig(whiteText);
		axis.setMinimum(0);
		axis.setMaximum(max);

		whiteText = whiteText.copy();
		whiteText.setTextAnchor(TextAnchor.MIDDLE);

		CategoryAxis<ViPrzychod, Integer> catAxis = new CategoryAxis<ViPrzychod, Integer>();
		catAxis.setPosition(Position.BOTTOM);
		catAxis.setField(dataAccess.rok());
		catAxis.setTitleConfig(titleMonthYear);
		catAxis.setAxisConfig(white);
		catAxis.setLabelConfig(whiteText);

		Gradient grad1 = new Gradient(0);
		grad1.addStop(0, new RGB(212, 40, 40));
		grad1.addStop(100, new RGB(117, 14, 14));

		Gradient grad2 = new Gradient(0);
		grad2.addStop(0, new RGB(180, 216, 42));
		grad2.addStop(100, new RGB(94, 114, 13));

		final Color[] colors = { grad1, grad2 };

		TextSprite sprite = new TextSprite();
		sprite.setFill(RGB.GRAY);
		sprite.setFontSize(18);
		sprite.setTextAnchor(TextAnchor.MIDDLE);

		SeriesLabelConfig<ViPrzychod> labelConfig = new SeriesLabelConfig<ViPrzychod>();
		labelConfig.setSpriteConfig(sprite);


		columnPrzychod = new BarSeries<ViPrzychod>();
		columnPrzychod.setYAxisPosition(Position.LEFT);
		if (czyPrzychod)
			columnPrzychod.addYField(dataAccess.przychod());
		if (czyKoszt)
			columnPrzychod.addYField(dataAccess.koszt());
		columnPrzychod.setLabelConfig(labelConfig);
		// Vertical or horizontal column view
		columnPrzychod.setColumn(true);
		columnPrzychod.setHighlighting(true);
		SeriesRenderer<ViPrzychod> sr = new SeriesRenderer<ViPrzychod>() {
			@Override
			public void spriteRenderer(Sprite sprite, int index, ListStore<ViPrzychod> store) {
				if (columnPrzychod.getYFields().size() == 2) {
				sprite.setFill(colors[index % 2 == 0 ? 1 : 0 /* colors.length */]);
				sprite.redraw();
				} else {
					sprite.setFill(colors[0]);
					sprite.redraw();

				}
			}
		};
		columnPrzychod.setRenderer(sr);
		columnPrzychod.setHighlighter(new SeriesHighlighter() {
			@Override
			public void highlight(Sprite sprite) {
				sprite.setStroke(new RGB(85, 85, 204));
				DrawFx.createStrokeWidthAnimator(sprite, 3).run(250);
			}

			@Override
			public void unHighlight(Sprite sprite) {
				sprite.setStroke(Color.NONE);
				DrawFx.createStrokeWidthAnimator(sprite, 0).run(250);
			}
		});
		Legend<ViPrzychod> legend = new Legend<ViPrzychod>();
		legend.setItemHighlighting(true);
		legend.setItemHiding(true);
		legend.getBorderConfig().setStrokeWidth(0);
		legend.setMarkerRenderer(sr);

		chart = new Chart<ViPrzychod>();
		chart.setAnimationDuration(750);
		chart.setAnimationEasing(new BounceOut());
		chart.setShadowChart(false);
		chart.setStore(store);
		chart.addAxis(axis);
		chart.addAxis(catAxis);
		chart.addGradient(grad1);
		chart.addGradient(grad2);
		chart.addSeries(columnPrzychod);
		chart.setAnimated(true);
		chart.setDefaultInsets(20);
		chart.setLegend(legend);
	}

	public IntegerField getRokDo() {
		return rokDo;
	}

	public IntegerField getRokOd() {
		return rokOd;
	}

	public TextButton getWykresKierunkiBtn() {
		return wykresKierunkiBtn;
	}

	public TextButton getWykresRokBtn() {
		return wykresRokBtn;
	}

	public Chart<ViPrzychod> getChart() {
		return chart;
	}

	public IntegerField getRokWykres2() {
		return rokWykres2;
	}

	public IntegerField getRokWykres3() {
		return rokWykres3;
	}

	public void pokazSymulacjaRok() {
		dialogRok.show();
	}

	public void pokazSymulacjaKierunki() {
		dialogKierunki.show();
	}

	public TextButton getBtnLiniowy() {
		return btnLiniowy;
	}

	public TextButton getBtnSlupkowy() {
		return btnSlupkowy;
	}

	private Integer findMax() {
		BigDecimal koszt = BigDecimal.ZERO;
		BigDecimal przychod = BigDecimal.ZERO;
		for (ViPrzychod pk : store.getAll()) {
			if (pk.getKoszt().compareTo(koszt) > 0)
				koszt = pk.getKoszt();
			if (pk.getPrzychod().compareTo(przychod) > 0)
				przychod = pk.getPrzychod();

		}
		BigDecimal wynik = przychod.compareTo(koszt) > 0 ? przychod : koszt;
		return wynik.multiply(BigDecimal.valueOf(1.2d)).intValue();
	}
}
