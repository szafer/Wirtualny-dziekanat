package pl.edu.us.client.kierunki.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.KierunekProperties;
import pl.edu.us.shared.model.old.Kierunek;

public class KierunkiGridPanel extends ContentPanel implements IsWidget, Editor<Kierunek> {

	private final ContentPanel panel;
	ColumnConfig<Kierunek, Integer> ilSemCol, maxGrupaCol;
	ColumnConfig<Kierunek, BigDecimal> czesneKol;
	private final Grid<Kierunek> grid;
	private final ListStore<Kierunek> store;
	private final GridEditing<Kierunek> editing;
	public KierunkiGridPanel(ListStore<Kierunek> store, KierunekProperties props) {
		this.store = store;
		ColumnConfig<Kierunek, String> nameCol = new ColumnConfig<Kierunek, String>(props.nazwa(), 200, "Nazwa");
		ilSemCol = new ColumnConfig<Kierunek, Integer>(props.iloscSemestrow(), 100, "Ilość semestrów");
		czesneKol = new ColumnConfig<Kierunek, BigDecimal>(props.czesne(), 100, "Czesne");
		maxGrupaCol = new ColumnConfig<Kierunek, Integer>(props.maxGrupa(), 100, "Ilość studentów");

		final NumberFormat number = NumberFormat.getFormat("0.00");

		List<ColumnConfig<Kierunek, ?>> columns = new ArrayList<ColumnConfig<Kierunek, ?>>();
		columns.add(nameCol);
		columns.add(ilSemCol);
		columns.add(czesneKol);
		columns.add(maxGrupaCol);

		ColumnModel<Kierunek> cm = new ColumnModel<Kierunek>(columns);
		grid = new Grid(store, cm);
		grid.setColumnReordering(true);
		grid.getView().setAutoExpandColumn(nameCol);
		grid.setBorders(false);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setShowDirtyCells(true);
		// State manager, make this grid stateful
		grid.setStateful(true);
		grid.setStateId("filterGridExample");

		StringFilter<Kierunek> nameFilter = new StringFilter<Kierunek>(props.nazwa());
		// nameFilter.setValue(null);
		NumericFilter<Kierunek, Integer> ilSemFilter = new NumericFilter<Kierunek, Integer>(props.iloscSemestrow(),
				new IntegerPropertyEditor());
		ilSemFilter.setLessThanValue(12);
		ilSemFilter.setActive(true, false);

		NumericFilter<Kierunek, BigDecimal> czesneFilter = new NumericFilter<Kierunek, BigDecimal>(props.czesne(),
				new BigDecimalPropertyEditor());
		czesneFilter.setLessThanValue(BigDecimal.valueOf(1000.00D));
		czesneFilter.setActive(true, false);

		NumericFilter<Kierunek, Integer> maxGrupaFilter = new NumericFilter<Kierunek, Integer>(props.iloscSemestrow(),
				new IntegerPropertyEditor());
		maxGrupaFilter.setLessThanValue(60);
		maxGrupaFilter.setActive(true, false);

		GridFilters<Kierunek> filters = new GridFilters<Kierunek>();
		filters.initPlugin(grid);
		filters.setLocal(true);
		filters.addFilter(nameFilter);
		filters.addFilter(ilSemFilter);
		filters.addFilter(czesneFilter);
		filters.addFilter(maxGrupaFilter);

		editing = new GridInlineEditing<Kierunek>(grid);
		editing.addEditor(nameCol, new TextField());
		editing.addEditor(ilSemCol, new IntegerField());
		editing.addEditor(czesneKol, new BigDecimalField());
		editing.addEditor(maxGrupaCol, new IntegerField());
		// column 5 is not editable

		// EDITING //
		// customizeGrid(grid);
		grid.setSelectionModel(new CellSelectionModel<Kierunek>());

		grid.getColumnModel().getColumn(0).setHideable(false);
		panel = new ContentPanel();
		panel.setHeadingText("Kierunki");
		// panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
		panel.setPixelSize(700, 500);
		panel.addStyleName("margin-10");
		panel.setWidget(grid);
		add(panel);
	}

	public Grid<Kierunek> getGrid() {
		return grid;
	}

	public GridEditing<Kierunek> getEditing() {
		return editing;
	}
}
