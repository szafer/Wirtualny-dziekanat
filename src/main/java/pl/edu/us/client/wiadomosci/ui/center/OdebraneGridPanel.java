package pl.edu.us.client.wiadomosci.ui.center;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.OdbiorcaProperties;
import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;

public class OdebraneGridPanel extends ContentPanel {

    private DateField df;

    private ColumnConfig<OdbiorcaDTO, String> nadImieCol, nadNazCol, nadTematCol;
    private ColumnConfig<OdbiorcaDTO, Date> dataOdbCol;;

    private Grid<OdbiorcaDTO> grid;
    private ListStore<OdbiorcaDTO> store;
    private DateCell dataZlozCell;

    private TextButton btnOdpowiedz = new TextButton("Odpowiedz");

    private WiadomosciModel model;
    private OdbiorcaProperties props;

    public OdebraneGridPanel(WiadomosciModel model) {
        this.model = model;
        this.store = model.getStoreOdebrane();
        this.props = model.getOdbProp();

        setHeadingHtml("Odebrane");

        dataOdbCol = new ColumnConfig<OdbiorcaDTO, Date>(props.dataOdbioru(), 130, "Data odbioru");
        nadImieCol = new ColumnConfig<OdbiorcaDTO, String>(props.nadawcaImie(), 100, "Imię");
        nadNazCol = new ColumnConfig<OdbiorcaDTO, String>(props.nadawcaNazwisko(), 150, "Nazwisko");
        nadTematCol = new ColumnConfig<OdbiorcaDTO, String>(props.nadawcaTemat(), 500, "Temat");

        dataZlozCell = new DateCell();
        dataZlozCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dataZlozCell.setWidth(120);
        dataZlozCell.setReadOnly(true);
        dataOdbCol.setCell(dataZlozCell);

        List<ColumnConfig<OdbiorcaDTO, ?>> columns = new ArrayList<ColumnConfig<OdbiorcaDTO, ?>>();
        columns.add(nadTematCol);
        columns.add(dataOdbCol);
        columns.add(nadImieCol);
        columns.add(nadNazCol);

        ColumnModel<OdbiorcaDTO> cm = new ColumnModel<OdbiorcaDTO>(columns);
        grid = new Grid<OdbiorcaDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(nadTematCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);

        grid.getColumnModel().getColumn(0).setHideable(false);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // State manager, make this grid stateful
        grid.setStateful(true);
//        grid.setStateId("filterGridExample");

        DateFilter<OdbiorcaDTO> dataZFilter = new DateFilter<OdbiorcaDTO>(props.dataOdbioru());

        GridFilters<OdbiorcaDTO> filters = new GridFilters<OdbiorcaDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(dataZFilter);
        filters.addFilter(createFilter(props.nadawcaImie()));
        filters.addFilter(createFilter(props.nadawcaNazwisko()));
        filters.addFilter(createFilter(props.nadawcaTemat()));

        ToolBar tb = new ToolBar();
        tb.add(btnOdpowiedz);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);
//        setHeight(500);
    }

    public Grid<OdbiorcaDTO> getGrid() {
        return grid;
    }

    public TextButton getBtnDodaj() {
        return btnOdpowiedz;
    }

    private StringFilter<OdbiorcaDTO> createFilter(ValueProvider<OdbiorcaDTO, String> provider) {
        StringFilter<OdbiorcaDTO> filter = new StringFilter<OdbiorcaDTO>(provider);
        filter.setActive(true, false);
        return filter;
    }
}
