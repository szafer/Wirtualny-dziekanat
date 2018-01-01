package pl.edu.us.client.przedmioty.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.PrzedmiotProperties;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;

public class PrzedmiotyGridPanel extends ContentPanel implements IsWidget, Editor<PrzedmiotDTO> {

//    private final ContentPanel panel;
//	ColumnConfig<Kierunek, Integer> ilSemCol, maxGrupaCol;
//	ColumnConfig<Kierunek, BigDecimal> czesneKol;
    private final Grid<PrzedmiotDTO> grid;
    private final ListStore<PrzedmiotDTO> store;
    private final GridRowEditing<PrzedmiotDTO> editing;
    private TextButton btnDodaj = new TextButton("Dodaj przedmiot");
    private static int AUTO_ID = 0;

    public PrzedmiotyGridPanel(final ListStore<PrzedmiotDTO> store, PrzedmiotProperties props) {
        this.store = store;
        setHeadingHtml("Przedmioty");
        ColumnConfig<PrzedmiotDTO, String> nameCol = new ColumnConfig<PrzedmiotDTO, String>(props.nazwa(), 200, "Nazwa");
//        nameCol.seH//TODO zmienic na senchagxt 4.0.2

        List<ColumnConfig<PrzedmiotDTO, ?>> columns = new ArrayList<ColumnConfig<PrzedmiotDTO, ?>>();
        columns.add(nameCol);
//		columns.add(ilSemCol);
//		columns.add(czesneKol);
//		columns.add(maxGrupaCol);

        ColumnModel<PrzedmiotDTO> cm = new ColumnModel<PrzedmiotDTO>(columns);
        grid = new Grid(store, cm);
//        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(nameCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        StringFilter<PrzedmiotDTO> nameFilter = new StringFilter<PrzedmiotDTO>(props.nazwa());

        GridFilters<PrzedmiotDTO> filters = new GridFilters<PrzedmiotDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(nameFilter);

        editing = new GridRowEditing<PrzedmiotDTO>(grid);
        editing.setClicksToEdit(ClicksToEdit.TWO);
        editing.addEditor(nameCol, new TextField());

//        grid.setSelectionModel(new RoSM<PrzedmiotDTO>());

        grid.getColumnModel().getColumn(0).setHideable(false);

        btnDodaj.setBorders(true);
        btnDodaj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                PrzedmiotDTO przedmiot = new PrzedmiotDTO();
                przedmiot.setId(--AUTO_ID);
                editing.cancelEditing();
                store.add(0, przedmiot);

//                int row = store.indexOf(przedmiot);
//                editing.startEditing(new GridCell(row, 0));
            }
        });
        ToolBar tb = new ToolBar();
        tb.add(btnDodaj);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));
        add(vlc);
    }

    public Grid<PrzedmiotDTO> getGrid() {
        return grid;
    }

    public GridEditing<PrzedmiotDTO> getEditing() {
        return editing;
    }
}
