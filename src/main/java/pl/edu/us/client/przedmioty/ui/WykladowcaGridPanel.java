package pl.edu.us.client.przedmioty.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.client.przedmioty.PrzedmiotyModel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public class WykladowcaGridPanel extends ContentPanel implements IsWidget, Editor<UPrzedmiotDTO> {

    private ColumnConfig<UPrzedmiotDTO, Date> dataSemCol;
    private ColumnConfig<UPrzedmiotDTO, String> semestrCol;
    private ColumnConfig<UPrzedmiotDTO, UserDTO> userCol;
    private final Grid<UPrzedmiotDTO> grid;
    private final ListStore<UPrzedmiotDTO> store;
    private final GridEditing<UPrzedmiotDTO> editing;
    private final PrzedmiotyModel model;
    private TextButton btnDodaj = new TextButton("Dodaj wykładowcę");
    private UPrzedmiotProperties props;

    public WykladowcaGridPanel(final PrzedmiotyModel model) {
        this.model = model;
        this.store = model.getStoreNauczyciele();
        this.props = model.getuPrzedmiotProp();

        dataSemCol = new ColumnConfig<UPrzedmiotDTO, Date>(props.dataSemestru(), 100, "Data semestru");
        semestrCol = new ColumnConfig<UPrzedmiotDTO, String>(props.semestrNazwa(), 100, "Semestr");
        userCol = new ColumnConfig<UPrzedmiotDTO, UserDTO>(props.uzytkownik(), 200, "Wykładowca");

        ComboBoxCell<UserDTO> comboUzytkownik = new ComboBoxCell<UserDTO>(model.getStoreUzytkownicy(), new LabelProvider<UserDTO>() {

            @Override
            public String getLabel(UserDTO item) {
                return item.toString();
            }
        });
        comboUzytkownik.addSelectionHandler(new SelectionHandler<UserDTO>() {

            @Override
            public void onSelection(SelectionEvent<UserDTO> event) {
                CellSelectionEvent<UserDTO> sel = (CellSelectionEvent<UserDTO>) event;
                UserDTO user = model.getStoreUzytkownicy().get(sel.getContext().getIndex());
                Info.display("Wykładowca", "Wybrano: " + user.toString());
            }
        });
        comboUzytkownik.setTriggerAction(TriggerAction.ALL);
        comboUzytkownik.setForceSelection(true);
//        comboUzytkownik.setWidth(200);
        userCol.setCell(comboUzytkownik);

        List<ColumnConfig<UPrzedmiotDTO, ?>> columns = new ArrayList<ColumnConfig<UPrzedmiotDTO, ?>>();
        columns.add(dataSemCol);
        columns.add(semestrCol);
        columns.add(userCol);

        ColumnModel<UPrzedmiotDTO> cm = new ColumnModel<UPrzedmiotDTO>(columns);
        grid = new Grid(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(userCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        StringFilter<UPrzedmiotDTO> nazwiskoFilter = new StringFilter<UPrzedmiotDTO>(props.uzytkownikNazwisko());
        nazwiskoFilter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> semFilter = new StringFilter<UPrzedmiotDTO>(props.semestrNazwa());
        semFilter.setActive(true, false);

        GridFilters<UPrzedmiotDTO> filters = new GridFilters<UPrzedmiotDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(nazwiskoFilter);
        filters.addFilter(semFilter);

        grid.setSelectionModel(new CellSelectionModel<UPrzedmiotDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);

        editing = new GridInlineEditing<UPrzedmiotDTO>(grid);
        editing.addEditor(dataSemCol, new DateField());
//        editing.addEditor(ocena1Col, new FloatField());
//        editing.addEditor(ocena2Col, new FloatField());
        btnDodaj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                UPrzedmiotDTO przedmiot = new UPrzedmiotDTO();
                przedmiot.setDataSemestru(new Date());
                przedmiot.setPrzedmiot(model.getPrzedmiot());

                editing.cancelEditing();
                store.add(0, przedmiot);

                int row = store.indexOf(przedmiot);
                editing.startEditing(new GridCell(row, 0));
            }
        });

        ToolBar tb = new ToolBar();
        tb.add(btnDodaj);
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(grid, new VerticalLayoutData(1, 1));

        add(vlc);
    }

    public Grid<UPrzedmiotDTO> getGrid() {
        return grid;
    }

    public GridEditing<UPrzedmiotDTO> getEditing() {
        return editing;
    }

    public TextButton getBtnDodaj() {
        return btnDodaj;
    }

}
