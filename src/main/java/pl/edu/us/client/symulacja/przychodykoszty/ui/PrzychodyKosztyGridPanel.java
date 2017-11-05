package pl.edu.us.client.symulacja.przychodykoszty.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;

import pl.edu.us.client.accesproperties.ViPrzychodProperties;

public class PrzychodyKosztyGridPanel<ViPrzychod> extends ContentPanel implements IsWidget, Editor<ViPrzychod> {

	private final Grid<ViPrzychod> grid;
	ColumnConfig<ViPrzychod, Integer> rokCol;
	ColumnConfig<ViPrzychod, String> mcCol;
	ColumnConfig<ViPrzychod, Integer> ilStudCol;
	ColumnConfig<ViPrzychod, BigDecimal> przychodCol;
	ColumnConfig<ViPrzychod, BigDecimal> kosztCol;
	ColumnConfig<ViPrzychod, BigDecimal> dochodCol;
	ColumnConfig<ViPrzychod, Boolean> zalCol;
	private final ListStore<ViPrzychod> store;

	@SuppressWarnings("unchecked")
	@Inject
	public PrzychodyKosztyGridPanel(ListStore<ViPrzychod> store, ViPrzychodProperties props) {
		this.store = store;
		setHeaderVisible(false);
		final CheckBoxSelectionModel<ViPrzychod> selectionModel = new CheckBoxSelectionModel<ViPrzychod>();
		selectionModel.setSelectionMode(SelectionMode.MULTI);
		selectionModel.getColumn().setHeader("Zaznacz");
		selectionModel.getColumn().setWidth(50);
		rokCol = new ColumnConfig<ViPrzychod, Integer>((ValueProvider<? super ViPrzychod, Integer>) props.rok(), 80,
				"Rok");
		ilStudCol = new ColumnConfig<ViPrzychod, Integer>((ValueProvider<? super ViPrzychod, Integer>) props.ilStud(),
				100, "Ilość studentów");
		przychodCol = new ColumnConfig<ViPrzychod, BigDecimal>(
				(ValueProvider<? super ViPrzychod, BigDecimal>) props.przychod(), 140, "Przychód");
		kosztCol = new ColumnConfig<ViPrzychod, BigDecimal>(
				(ValueProvider<? super ViPrzychod, BigDecimal>) props.koszt(), 140, "Koszty");
		dochodCol = new ColumnConfig<ViPrzychod, BigDecimal>(
				(ValueProvider<? super ViPrzychod, BigDecimal>) props.dochod(), 140, "Dochód");
		mcCol = new ColumnConfig<ViPrzychod, String>((ValueProvider<? super ViPrzychod, String>) props.miesiac(), 100,
				"Miesiąc");

		List<ColumnConfig<ViPrzychod, ?>> columns = new ArrayList<ColumnConfig<ViPrzychod, ?>>();
		columns.add(rokCol);
		columns.add(mcCol);
		columns.add(ilStudCol);
		columns.add(przychodCol);
		columns.add(kosztCol);
		columns.add(dochodCol);
		columns.add(selectionModel.getColumn());

		ColumnModel<ViPrzychod> cm = new ColumnModel<ViPrzychod>(columns);
		grid = new Grid<ViPrzychod>(store, cm);
		grid.setColumnReordering(true);
		grid.getView().setAutoExpandColumn(rokCol);
		grid.setBorders(false);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);

		// State manager, make this grid stateful
		grid.setStateful(true);
		grid.setStateId("przychodyKosztyGrid");

		ListStore<String> typeStore = new ListStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});

		GridFilters<ViPrzychod> filters = new GridFilters<ViPrzychod>();
		filters.initPlugin(grid);
		filters.setLocal(true);

		GridFilterStateHandler<ViPrzychod> handler = new GridFilterStateHandler<ViPrzychod>(grid,
				(GridFilters<ViPrzychod>) filters);
		handler.loadState();

		setWidget(grid);
	}

	public Grid<ViPrzychod> getGrid() {
		return grid;
	}

	public ListStore<ViPrzychod> getStore() {
		return store;
	}
}