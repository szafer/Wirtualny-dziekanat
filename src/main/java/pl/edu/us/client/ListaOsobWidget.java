package pl.edu.us.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.PersonProperties;
import pl.edu.us.shared.model.Person;

public class ListaOsobWidget<T extends Person, U extends PersonProperties<T>> extends ContentPanel {

	// private static final PersonProperties props =
	// GWT.create(PersonProperties.class);
	private final ContentPanel panel;
	private final Grid<T> grid;
	ColumnConfig<T, String> lastCol;
	ColumnConfig<T, String> nameCol;
	private final ListStore<T> store;
	@Inject
	public ListaOsobWidget(ListStore<T> store, U props) {
		this.store = store;
		ColumnConfig<T, Integer> symbolCol = new ColumnConfig<T, Integer>(props.id(), 50, "Numer");
		lastCol = new ColumnConfig<T, String>(props.nazwisko(), 100, "Nazwisko");
		nameCol = new ColumnConfig<T, String>(props.imie(), 100, "Imie");

		final NumberFormat number = NumberFormat.getFormat("0.00");

		List<ColumnConfig<T, ?>> columns = new ArrayList<ColumnConfig<T, ?>>();
		columns.add(symbolCol);
		columns.add(nameCol);
		columns.add(lastCol);

		ColumnModel<T> cm = new ColumnModel<T>(columns);
		grid = new Grid(store, cm);
		grid.setColumnReordering(true);
		grid.getView().setAutoExpandColumn(nameCol);
		grid.setBorders(false);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);

		// State manager, make this grid stateful
		grid.setStateful(true);
		grid.setStateId("filterGridExample");

		// NumericFilter<Person, Double> lastFilter = new
		// NumericFilter<Person, Double>(props.last(), new
		// DoublePropertyEditor());
		// lastFilter.setLessThanValue(100D);
		// lastFilter.setActive(true, false);

		StringFilter<T> nameFilter = new StringFilter<T>(props.imie());
		StringFilter<T> lastFilter = new StringFilter<T>(props.nazwisko());

		// DateFilter<Person> dateFilter = new
		// DateFilter<StPersonock>(props.lastTrans());
		// dateFilter.setMinDate(new DateWrapper().addDays(-5).asDate());
		// dateFilter.setMaxDate(new DateWrapper().addMonths(2).asDate());

		// BooleanFilter<Person> booleanFilter = new
		// BooleanFilter<Person>(props.split());
		// ListFilter<Person, String> listFilter = new ListFilter<Person,
		// String>(props.industry(), typeStore);
		// listFilter.setUseStoreKeys(true);

		GridFilters<T> filters = new GridFilters<T>();
		filters.initPlugin(grid);
		filters.setLocal(true);
		filters.addFilter(lastFilter);
		filters.addFilter(nameFilter);

		// Stage manager, load the previous state
		GridFilterStateHandler<T> handler = new GridFilterStateHandler<T>(grid, (GridFilters<T>) filters);
		handler.loadState();

		panel = new ContentPanel();
		panel.setHeadingText("Lista osób");
		// panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
		panel.setPixelSize(700, 300);
		panel.addStyleName("margin-10");
		panel.setWidget(grid);
		add(panel);
	}

	public Grid<T> getGrid() {
		return grid;
	}

	public ColumnConfig<T, String> getLastCol() {
		return lastCol;
	}

	public ColumnConfig<T, String> getNameCol() {
		return nameCol;
	}

	public ListStore<T> getStore() {
		return store;
	}
}
