package pl.edu.us.client.wiadomosci.ui.center;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.NadawcaProperties;
import pl.edu.us.client.main.grid.BeanFilter;
import pl.edu.us.client.main.grid.KodFilter;
import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class NadawcaGridPanel extends ContentPanel {

    private DateCell dataZlozCell, dataRCell;
    private ComboBoxCell<WniosekDTO> comboTypCell;
    private ComboBox<WniosekDTO> comboTyp;
    private DateField df;

    private ColumnConfig<NadawcaDTO, String> temat;
    private ColumnConfig<NadawcaDTO, Date> dataNadCol;//, dataRozpatrzeniaCol;

    private Grid<NadawcaDTO> grid;
    private ListStore<NadawcaDTO> store;

    private TextButton btnNowa = new TextButton("Nowa wiadomość");

    private WiadomosciModel model;
    private NadawcaProperties props;

    public NadawcaGridPanel(WiadomosciModel model) {
        this.model = model;
        this.store = model.getStoreWyslane();
        this.props = model.getNadProp();

        setHeadingHtml("Wysłane");

        dataNadCol = new ColumnConfig<NadawcaDTO, Date>(props.data(), 130, "Data nadania");
        temat = new ColumnConfig<NadawcaDTO, String>(props.temat(), 500, "Temat");

        dataZlozCell = new DateCell();
        dataZlozCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dataZlozCell.setWidth(120);
        dataZlozCell.setReadOnly(true);
        dataNadCol.setCell(dataZlozCell);

        List<ColumnConfig<NadawcaDTO, ?>> columns = new ArrayList<ColumnConfig<NadawcaDTO, ?>>();
        columns.add(dataNadCol);
        columns.add(temat);

        ColumnModel<NadawcaDTO> cm = new ColumnModel<NadawcaDTO>(columns);
        grid = new Grid<NadawcaDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(temat);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
//        grid.setSelectionModel(new CellSelectionModel<NadawcaDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        StringFilter<NadawcaDTO> nameFilter = new StringFilter<NadawcaDTO>(props.temat());
        DateFilter<NadawcaDTO> dataZFilter = new DateFilter<NadawcaDTO>(props.data());

        GridFilters<NadawcaDTO> filters = new GridFilters<NadawcaDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(dataZFilter);
        filters.addFilter(nameFilter);

        ToolBar tb = new ToolBar();
        tb.add(btnNowa);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);
//        setHeight(500);
    }

    public Grid<NadawcaDTO> getGrid() {
        return grid;
    }

    public TextButton getBtnDodaj() {
        return btnNowa;
    }

    public ComboBox<WniosekDTO> getComboTyp() {
        return comboTyp;
    }
}
