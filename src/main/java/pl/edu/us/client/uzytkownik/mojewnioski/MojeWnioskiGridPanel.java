package pl.edu.us.client.uzytkownik.mojewnioski;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.LabelProvider;
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
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.UWniosekProperties;
import pl.edu.us.client.main.grid.BeanFilter;
import pl.edu.us.client.main.grid.KodFilter;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.StatusWniosku;

public class MojeWnioskiGridPanel extends ContentPanel {

    private ColumnConfig<UWniosekDTO, Date> dataZlozCol;//, dataRozpatrzeniaCol;
//    private ColumnConfig<UWniosekDTO, UserDTO> userCol;
    private ColumnConfig<UWniosekDTO, WniosekDTO> typCol;
    private ColumnConfig<UWniosekDTO, StatusWniosku> statusCol;
//    private ColumnConfig<UWniosekDTO, BigDecimal> kwotaCol;
    private DateCell dataZlozCell, dataRCell;
    private ComboBoxCell<StatusWniosku> comboStatus;
    private ComboBoxCell<WniosekDTO> comboTypCell;
    private ComboBox<WniosekDTO> comboTyp;
    private DateField df;
    private final Grid<UWniosekDTO> grid;
    private final ListStore<UWniosekDTO> store;
    private final GridRowEditing<UWniosekDTO> editing;

    private TextButton btnDodaj = new TextButton("Dodaj wniosek");

    private MojeWnioskiModel model;
    private UWniosekProperties props;

    public MojeWnioskiGridPanel(MojeWnioskiModel model) {
        this.model = model;
        this.store = model.getStoreWnioskiUzytkownika();
        this.props = model.getWnioskiUzProp();

        setHeadingHtml("Wnioski");

        dataZlozCol = new ColumnConfig<UWniosekDTO, Date>(props.dataZlozenia(), 130, "Data wniosku");
        typCol = new ColumnConfig<UWniosekDTO, WniosekDTO>(props.wniosek(), 210, "Typ wniosku");
        statusCol = new ColumnConfig<UWniosekDTO, StatusWniosku>(props.status(), 130, "Status wniosku");

        dataZlozCell = new DateCell();
        dataZlozCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dataZlozCell.setWidth(120);
        dataZlozCell.setReadOnly(true);
        dataZlozCol.setCell(dataZlozCell);

        comboTypCell = new ComboBoxCell<WniosekDTO>(model.getStoreWzory(), new LabelProvider<WniosekDTO>() {

            @Override
            public String getLabel(WniosekDTO item) {
                return item.toString();
            }
        });
        comboTypCell.setTriggerAction(TriggerAction.ALL);
        comboTypCell.setForceSelection(true);
//        comboTyp.setReadOnly(true);
        comboTypCell.setAllowBlank(false);
        comboTypCell.setWidth(200);
//        typCol.setCell(comboTypCell);

        comboTyp = new ComboBox<WniosekDTO>(model.getStoreWzory(), new LabelProvider<WniosekDTO>() {

            @Override
            public String getLabel(WniosekDTO item) {
                return item.toString();
            }
        });
        comboTyp.setForceSelection(true);
//      comboTyp.setReadOnly(true);
        comboTyp.setAllowBlank(false);
        comboTyp.setWidth(200);

        df = new DateField(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        df.setEditable(false);

        List<ColumnConfig<UWniosekDTO, ?>> columns = new ArrayList<ColumnConfig<UWniosekDTO, ?>>();
        columns.add(dataZlozCol);
        columns.add(typCol);
        columns.add(statusCol);

        ColumnModel<UWniosekDTO> cm = new ColumnModel<UWniosekDTO>(columns);
        grid = new Grid<UWniosekDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(statusCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
//        grid.setSelectionModel(new CellSelectionModel<UWniosekDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

//        StringFilter<UWniosekDTO> nameFilter = new StringFilter<UWniosekDTO>(props.nazwa());
        DateFilter<UWniosekDTO> dataZFilter = new DateFilter<UWniosekDTO>(props.dataZlozenia());
        BeanFilter<UWniosekDTO, WniosekDTO> wniosekFilter = new BeanFilter<UWniosekDTO, WniosekDTO>(props.wniosek());
        wniosekFilter.setActive(true, false);
        KodFilter<UWniosekDTO, StatusWniosku> statusFilter = new KodFilter<UWniosekDTO, StatusWniosku>(props.status());
        GridFilters<UWniosekDTO> filters = new GridFilters<UWniosekDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(dataZFilter);
        filters.addFilter(wniosekFilter);
        filters.addFilter(statusFilter);

        editing = new GridRowEditing<UWniosekDTO>(grid);
        editing.setClicksToEdit(ClicksToEdit.TWO);
        editing.addEditor(typCol, comboTyp);
        editing.addEditor(dataZlozCol, df);

        ToolBar tb = new ToolBar();
        tb.add(btnDodaj);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);
    }

    public Grid<UWniosekDTO> getGrid() {
        return grid;
    }

    public GridRowEditing<UWniosekDTO> getEditing() {
        return editing;
    }

    public TextButton getBtnDodaj() {
        return btnDodaj;
    }

    public ComboBox<WniosekDTO> getComboTyp() {
        return comboTyp;
    }
}
