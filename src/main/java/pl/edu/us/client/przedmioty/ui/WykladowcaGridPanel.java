package pl.edu.us.client.przedmioty.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import javafx.scene.control.ComboBox;
import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.client.przedmioty.PrzedmiotyModel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Semestr;

public class WykladowcaGridPanel extends ContentPanel implements IsWidget, Editor<UPrzedmiotDTO> {

    private ColumnConfig<UPrzedmiotDTO, Date> dataSemCol;
    private ColumnConfig<UPrzedmiotDTO, String> semestrCol;
    private ColumnConfig<UPrzedmiotDTO, UserDTO> userCol;
    private final Grid<UPrzedmiotDTO> grid;
    private final ListStore<UPrzedmiotDTO> store;
    private  GridEditing<UPrzedmiotDTO> editing;
    private final PrzedmiotyModel model;
    private TextButton btnDodaj = new TextButton("Dodaj wykładowcę");
    private UPrzedmiotProperties props;
    private ComboBoxCell<UserDTO> comboUzytkownik;
    private DateCell dateCell;

    public WykladowcaGridPanel(final PrzedmiotyModel model) {
        this.model = model;
        this.store = model.getStoreNauczyciele();
        this.props = model.getuPrzedmiotProp();
        setHeight(110);
setHeadingText("Wykładowca");
        SafeStyles comboStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");

        dataSemCol = new ColumnConfig<UPrzedmiotDTO, Date>(props.dataSemestru(), 130, "Data semestru");
        semestrCol = new ColumnConfig<UPrzedmiotDTO, String>(props.semestrNazwa(), 100, "Semestr");
        userCol = new ColumnConfig<UPrzedmiotDTO, UserDTO>(props.uzytkownik(), 209, "Wykładowca");

        dateCell = new DateCell();
        dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dateCell.setWidth(120);
        dataSemCol.setCell(dateCell);

        comboUzytkownik = new ComboBoxCell<UserDTO>(model.getStoreUzytkownicy(), new LabelProvider<UserDTO>() {

            @Override
            public String getLabel(UserDTO item) {
                return item.toString();
            }
        });
        comboUzytkownik.setTriggerAction(TriggerAction.ALL);
        comboUzytkownik.setForceSelection(true);
        comboUzytkownik.setWidth(200);
        userCol.setCell(comboUzytkownik);
        userCol.setColumnTextStyle(comboStyle);

        List<ColumnConfig<UPrzedmiotDTO, ?>> columns = new ArrayList<ColumnConfig<UPrzedmiotDTO, ?>>();
        columns.add(dataSemCol);
        columns.add(semestrCol);
        columns.add(userCol);

        ColumnModel<UPrzedmiotDTO> cm = new ColumnModel<UPrzedmiotDTO>(columns);
        grid = new Grid(store, cm);
        grid.setColumnReordering(true);
//        grid.getView().setAutoExpandColumn(userCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
//        grid.setHeight(30);
        // State manager, make this grid stateful
        grid.setStateful(true);

//        StringFilter<UPrzedmiotDTO> nazwiskoFilter = new StringFilter<UPrzedmiotDTO>(props.uzytkownikNazwisko());
//        nazwiskoFilter.setActive(true, false);
//
//        StringFilter<UPrzedmiotDTO> semFilter = new StringFilter<UPrzedmiotDTO>(props.semestrNazwa());
//        semFilter.setActive(true, false);

        //Tutaj filtry niepotrzebne
//        GridFilters<UPrzedmiotDTO> filters = new GridFilters<UPrzedmiotDTO>();
//        filters.initPlugin(grid);
//        filters.setLocal(true);
//        filters.addFilter(nazwiskoFilter);
//        filters.addFilter(semFilter);

        grid.setSelectionModel(new CellSelectionModel<UPrzedmiotDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);

//        editing = new GridInlineEditing<UPrzedmiotDTO>(grid);
//        editing.addEditor(dataSemCol, new DateField());
//        editing.addEditor(dataSemCol, comboUzytkownik.);


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

    public ComboBoxCell<UserDTO> getComboUzytkownik() {
        return comboUzytkownik;
    }

    public DateCell getDateCell() {
        return dateCell;
    }
}
