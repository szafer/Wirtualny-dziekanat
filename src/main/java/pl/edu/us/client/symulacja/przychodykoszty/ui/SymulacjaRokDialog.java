package pl.edu.us.client.symulacja.przychodykoszty.ui;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.fx.client.easing.BounceOut;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import pl.edu.us.client.accesproperties.ViPrzychodProperties;
import pl.edu.us.shared.model.old.ViPrzychod;

public class SymulacjaRokDialog extends Dialog {


	private static final ViPrzychodProperties dataAccess = GWT.create(ViPrzychodProperties.class);
	private CheckBox kosztCheck;
	private CheckBox przychodCheck;
	private Boolean czyKoszt, czyPrzychod;
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

	public SymulacjaRokDialog(ListStore<ViPrzychod> store) {
		this.store = store;
		czyKoszt = true;
		czyPrzychod = true;
		// Opisy osi chart
		titleHits = new TextSprite("Przychód / Koszt PLN");
		titleHits.setFontSize(18);
		titleHits.setFill(RGB.GRAY);
		titleMonthYear = new TextSprite("Miesiąc w roku");
		titleMonthYear.setFontSize(18);
		titleMonthYear.setFill(RGB.GRAY);

		setWidth(900);
		setHeight(600);
		setHeadingText("Symulacja dialog");
		setModal(true);

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
				onBtnSlupkowyClick();
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
		panel.add(chart);
		layoutComplex.setEastWidget(eastPanel, new BorderLayoutData(200));
		layoutComplex.setCenterWidget(panel);
		add(layoutComplex);

	}

	public void onBtnLiniowyClick() {
		if (chart != null && chart.getParent() != null) {
			chart.removeFromParent();
		}
		ladujAreaChart();
		panel.add(chart);
		panel.forceLayout();
		chart.show();
		chart.redrawChart();
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

		CategoryAxis<ViPrzychod, String> catAxis = new CategoryAxis<ViPrzychod, String>();
		catAxis.setPosition(Position.BOTTOM);
		catAxis.setField(dataAccess.miesiac());
		catAxis.setTitleConfig(titleMonthYear);
		catAxis.setDisplayGrid(true);
		catAxis.setLabelConfig(labelConfig);
		catAxis.setLabelPadding(-10);
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

	@Override
	protected void onButtonPressed(TextButton textButton) {
		hide();
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
		// whiteText.setTextAnchor(TextAnchor.MIDDLE);
		whiteText.setRotation(315);
		CategoryAxis<ViPrzychod, String> catAxis = new CategoryAxis<ViPrzychod, String>();
		catAxis.setPosition(Position.BOTTOM);
		catAxis.setField(dataAccess.miesiac());
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

	public TextButton getBtnLiniowy() {
		return btnLiniowy;
	}

	public TextButton getBtnSlupkowy() {
		return btnSlupkowy;
	}

	@Override
	protected void onShow() {
		super.onShow();
		chart.clearSurface();
		if (store.size() == 1) {
			btnLiniowy.setEnabled(false);
			onBtnSlupkowyClick();
		} else {
			btnLiniowy.setEnabled(true);
			onBtnLiniowyClick();
		}
	}

	private void onBtnSlupkowyClick() {
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
