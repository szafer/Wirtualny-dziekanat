package pl.edu.us.client.wnioski.kartoteka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.UWniosekProperties;
import pl.edu.us.client.main.grid.BeanFilter;
import pl.edu.us.client.main.grid.KodFilter;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class WnioskiKartGridPanel extends ContentPanel {

    private ColumnConfig<UWniosekDTO, Integer> idCol;
    private ColumnConfig<UWniosekDTO, Date> dataZlozCol, dataRozpCol;
//    private ColumnConfig<UWniosekDTO, UserDTO> userCol;
    private ColumnConfig<UWniosekDTO, WniosekDTO> typCol;
    private ColumnConfig<UWniosekDTO, StatusWniosku> statusCol;
    private ColumnConfig<UWniosekDTO, BigDecimal> kwotaCol;
    private ColumnConfig<UWniosekDTO, String> imieCol, nazwiskoCol;

    private final Grid<UWniosekDTO> grid;
    private final ListStore<UWniosekDTO> store;
    private final GridInlineEditing<UWniosekDTO> editing;
    private DateCell dataZlozCell, dataRCell;
    private ComboBoxCell<StatusWniosku> comboStatus;
    private WnioskiKartModel model;
    private UWniosekProperties props;
    BigDecimalField bdKwota = new BigDecimalField();

    public WnioskiKartGridPanel(WnioskiKartModel model) {
        this.model = model;
        this.store = model.getStoreWnioski();
        this.props = model.getWnioskiUzProp();
        setHeadingHtml("Wnioski użytkowników");
        SafeStyles comboStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");

        idCol = new ColumnConfig<>(props.id(), 30, "ID");
        dataZlozCol = new ColumnConfig<UWniosekDTO, Date>(props.dataZlozenia(), 130, "Data wniosku");
        imieCol = new ColumnConfig<UWniosekDTO, String>(props.uzytkownikImie(), 100, "Imię");
        nazwiskoCol = new ColumnConfig<UWniosekDTO, String>(props.uzytkownikNazwisko(), 150, "Nazwisko");
//userCol = new ColumnConfig<UWniosekDTO, UserDTO>(props.uzytkownik(), 209, "Student");
        typCol = new ColumnConfig<UWniosekDTO, WniosekDTO>(props.wniosek(), 130, "Typ wniosku");
        statusCol = new ColumnConfig<UWniosekDTO, StatusWniosku>(props.status(), 220, "Status wniosku");
        dataRozpCol = new ColumnConfig<UWniosekDTO, Date>(props.dataRozpatrzenia(), 130, "Data rozpatrzenia");
        dataZlozCol = new ColumnConfig<UWniosekDTO, Date>(props.dataZlozenia(), 130, "Data wniosku");
        kwotaCol = new ColumnConfig<UWniosekDTO, BigDecimal>(props.kwota(), 100, "Kwota");

        dataZlozCell = new DateCell();
        dataZlozCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dataZlozCell.setWidth(120);
        dataZlozCell.setReadOnly(true);
        dataZlozCol.setCell(dataZlozCell);

        dataRCell = new DateCell();
        dataRCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dataRCell.setWidth(120);
        dataRozpCol.setCell(dataRCell);

        comboStatus = new ComboBoxCell<StatusWniosku>(model.getStoreStatusWniosku(), new LabelProvider<StatusWniosku>() {

            @Override
            public String getLabel(StatusWniosku item) {
                return item.toString();
            }
        });
        comboStatus.setTriggerAction(TriggerAction.ALL);
        comboStatus.setForceSelection(true);
        comboStatus.setWidth(200);
        statusCol.setCell(comboStatus);

        List<ColumnConfig<UWniosekDTO, ?>> columns = new ArrayList<ColumnConfig<UWniosekDTO, ?>>();
        columns.add(idCol);
        columns.add(dataZlozCol);
        columns.add(imieCol);
        columns.add(nazwiskoCol);
        columns.add(typCol);
        columns.add(statusCol);
        columns.add(dataRozpCol);
        columns.add(kwotaCol);
        ColumnModel<UWniosekDTO> cm = new ColumnModel<UWniosekDTO>(columns);

        grid = new Grid<UWniosekDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(nazwiskoCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
        grid.setSelectionModel(new CellSelectionModel<UWniosekDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
        grid.getColumnModel().getColumn(1).setHideable(false);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        DateFilter<UWniosekDTO> dataZFilter = new DateFilter<UWniosekDTO>(props.dataZlozenia());
        DateFilter<UWniosekDTO> dataRFilter = new DateFilter<UWniosekDTO>(props.dataRozpatrzenia());

        BeanFilter<UWniosekDTO, WniosekDTO> wniosekFilter = new BeanFilter<UWniosekDTO, WniosekDTO>(props.wniosek());
        wniosekFilter.setActive(true, false);

        KodFilter<UWniosekDTO, StatusWniosku> statusFilter = new KodFilter<UWniosekDTO, StatusWniosku>(props.status());

        NumericFilter<UWniosekDTO, BigDecimal> kwotaFilter = new NumericFilter<UWniosekDTO, BigDecimal>(props.kwota(),
            new BigDecimalPropertyEditor());
        kwotaFilter.setActive(true, false);

        GridFilters<UWniosekDTO> filters = new GridFilters<UWniosekDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(dataZFilter);
        filters.addFilter(createFilter(props.uzytkownikImie()));
        filters.addFilter(createFilter(props.uzytkownikNazwisko()));
        filters.addFilter(dataRFilter);
        filters.addFilter(wniosekFilter);
        filters.addFilter(statusFilter);
        filters.addFilter(kwotaFilter);

        editing = new GridInlineEditing<UWniosekDTO>(grid);
        editing.setClicksToEdit(ClicksToEdit.TWO);
        editing.addEditor(kwotaCol, bdKwota);
        
//        editing.addEditor(statusCol, new ComboBox<StatusWniosku>(comboStatus));
        add(grid);
    }

    private StringFilter<UWniosekDTO> createFilter(ValueProvider<UWniosekDTO, String> provider) {
        StringFilter<UWniosekDTO> filter = new StringFilter<UWniosekDTO>(provider);
        filter.setActive(true, false);
        return filter;
    }

    public Grid<UWniosekDTO> getGrid() {
        return grid;
    }

    public GridInlineEditing<UWniosekDTO> getEditing() {
        return editing;
    }

    public ColumnConfig<UWniosekDTO, BigDecimal> getKwotaCol() {
        return kwotaCol;
    }

    public ComboBoxCell<StatusWniosku> getComboStatus() {
        return comboStatus;
    }

    public BigDecimalField getBdKwota() {
        return bdKwota;
    }

    public DateCell getDataRCell() {
        return dataRCell;
    }
}
