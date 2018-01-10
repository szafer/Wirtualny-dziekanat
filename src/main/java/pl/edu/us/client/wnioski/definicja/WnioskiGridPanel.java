package pl.edu.us.client.wnioski.definicja;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.WniosekProperties;
import pl.edu.us.client.main.grid.KodFilter;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.TypWniosku;

public class WnioskiGridPanel extends ContentPanel {

    private WnioskiModel model;
    private WniosekProperties props;
    private final Grid<WniosekDTO> grid;
    private final ListStore<WniosekDTO> store;
    private final GridRowEditing<WniosekDTO> editing;
    private TextButton btnDodaj = new TextButton("Dodaj wniosek");
//    private TextButton btnWczytaj = new TextButton("Wczytaj");
//    private FileUploadField fileUploadField = new FileUploadField();
//    private FormPanel fp = new FormPanel();
    private ComboBoxCell<TypWniosku> comboTypCell;
    private ColumnConfig<WniosekDTO, Integer> idCol;
    private ColumnConfig<WniosekDTO, TypWniosku> typCol;
    private ColumnConfig<WniosekDTO, String> plikCol;
    private ComboBox<TypWniosku> comboTyp;

    @Inject
    public WnioskiGridPanel(WnioskiModel model) {
        this.model = model;
        this.store = model.getStoreWnioski();
        this.props = model.getWnioskiProp();
        setHeadingHtml("Wnioski");
        idCol = new ColumnConfig<WniosekDTO, Integer>(props.id(), 30, "ID");
        typCol = new ColumnConfig<WniosekDTO, TypWniosku>(props.typ(), 200, "Typ wniosku");
        plikCol = new ColumnConfig<WniosekDTO, String>(props.nazwaObrazu(), 100, "Nazwa pliku");

        comboTypCell = new ComboBoxCell<TypWniosku>(model.getStoreTypWniosku(), new LabelProvider<TypWniosku>() {

            @Override
            public String getLabel(TypWniosku item) {
                return item.toString();
            }
        });
        comboTypCell.setTriggerAction(TriggerAction.ALL);
        comboTypCell.setForceSelection(true);
        comboTypCell.setWidth(200);
//        typCol.setCell(comboTypCell);

        comboTyp = new ComboBox<TypWniosku>(model.getStoreTypWniosku(), new LabelProvider<TypWniosku>() {

            @Override
            public String getLabel(TypWniosku item) {
                return item.toString();
            }
        });
        comboTyp.setForceSelection(true);
//      comboTyp.setReadOnly(true);
        comboTyp.setAllowBlank(false);
        comboTyp.setWidth(200);

        List<ColumnConfig<WniosekDTO, ?>> columns = new ArrayList<ColumnConfig<WniosekDTO, ?>>();
        columns.add(idCol);
        columns.add(typCol);
        columns.add(plikCol);

        ColumnModel<WniosekDTO> cm = new ColumnModel<WniosekDTO>(columns);
        grid = new Grid<WniosekDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(typCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
//        grid.setSelectionModel(new CellSelectionModel<WniosekDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        KodFilter<WniosekDTO, TypWniosku> nameFilter = new KodFilter<WniosekDTO, TypWniosku>(props.typ());

        GridFilters<WniosekDTO> filters = new GridFilters<WniosekDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(nameFilter);

        editing = new GridRowEditing<WniosekDTO>(grid);
        editing.setClicksToEdit(ClicksToEdit.TWO);
        editing.addEditor(typCol, comboTyp);

        ToolBar tb = new ToolBar();
        tb.add(btnDodaj);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);

    }

    public Grid<WniosekDTO> getGrid() {
        return grid;
    }

    public GridRowEditing<WniosekDTO> getEditing() {
        return editing;
    }

    public TextButton getBtnDodaj() {
        return btnDodaj;
    }

}
