package pl.edu.us.client.wnioski.definicja;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.WniosekProperties;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.TypWniosku;

public class WnioskiGridPanel extends ContentPanel {

    private final Grid<WniosekDTO> grid;
    private final ListStore<WniosekDTO> store;
    private final GridInlineEditing<WniosekDTO> editing;
    private TextButton btnDodaj = new TextButton("Dodaj wniosek");
    private TextButton btnWczytaj = new TextButton("Wczytaj");
    private FileUploadField fileUploadField = new FileUploadField();

    public WnioskiGridPanel(ListStore<WniosekDTO> store, WniosekProperties props) {
        this.store = store;
        setHeadingHtml("Wnioski");

        ColumnConfig<WniosekDTO, TypWniosku> nameCol = new ColumnConfig<WniosekDTO, TypWniosku>(props.typ(), 200, "Typ wniosku");

        List<ColumnConfig<WniosekDTO, ?>> columns = new ArrayList<ColumnConfig<WniosekDTO, ?>>();
        columns.add(nameCol);

        ColumnModel<WniosekDTO> cm = new ColumnModel<WniosekDTO>(columns);
        grid = new Grid<WniosekDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(nameCol);
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

//        StringFilter<WniosekDTO> nameFilter = new StringFilter<WniosekDTO>(props.nazwa());

        GridFilters<WniosekDTO> filters = new GridFilters<WniosekDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
//        filters.addFilter(nameFilter);

        editing = new GridInlineEditing<WniosekDTO>(grid);
        editing.setClicksToEdit(ClicksToEdit.TWO);
//        editing.addEditor(nameCol, new TextField());
//        editing.getButtonBar().setStyleName("button-align:center");
//        grid.setSelectionModel(new RoSM<WniosekDTO>());

//        btnDodaj.setBorders(true);
        ToolBar tb = new ToolBar();
        tb.add(btnDodaj);
        tb.add(btnWczytaj);
        tb.add(fileUploadField);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);
    }

    public Grid<WniosekDTO> getGrid() {
        return grid;
    }

    public GridInlineEditing<WniosekDTO> getEditing() {
        return editing;
    }

    public TextButton getBtnDodaj() {
        return btnDodaj;
    }

    public TextButton getBtnWczytaj() {
        return btnWczytaj;
    }

    public FileUploadField getFileUploadField() {
        return fileUploadField;
    }
}
