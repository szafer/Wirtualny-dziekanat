package pl.edu.us.client.przedmioty.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.InvalidEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.InvalidEvent.InvalidHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.FloatField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.FloatPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.client.main.validators.OcenaValidator;
import pl.edu.us.client.przedmioty.PrzedmiotyModel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Semestr;

public class StudenciGridPanel extends ContentPanel implements IsWidget, Editor<UPrzedmiotDTO> {

    private ColumnConfig<UPrzedmiotDTO, Date> dataSemCol;
    private ColumnConfig<UPrzedmiotDTO, Float> ocena1Col, ocena2Col;
    private ColumnConfig<UPrzedmiotDTO, String> semestrCol;
    private ColumnConfig<UPrzedmiotDTO, UserDTO> userCol;
    private final Grid<UPrzedmiotDTO> grid;
    private final ListStore<UPrzedmiotDTO> store;
    private final GridEditing<UPrzedmiotDTO> editing;
    private final PrzedmiotyModel model;
    private TextButton btnDodaj = new TextButton("Dodaj studenta");
    private UPrzedmiotProperties props;
    private ComboBoxCell<UserDTO> comboUzytkownik;
    private DateCell dateCell;

    public StudenciGridPanel(final PrzedmiotyModel model) {
        this.model = model;
        this.store = model.getStoreStudenci();
        this.props = model.getuPrzedmiotProp();
        setHeadingText("Studenci");
        SafeStyles comboStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");

        dataSemCol = new ColumnConfig<UPrzedmiotDTO, Date>(props.dataSemestru(), 130, "Data semestru");
        semestrCol = new ColumnConfig<UPrzedmiotDTO, String>(props.semestrNazwa(), 100, "Semestr");
        userCol = new ColumnConfig<UPrzedmiotDTO, UserDTO>(props.uzytkownik(), 209, "Student");
        ocena1Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena1(), 60, "Ocena 1");
        ocena2Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena2(), 60, "Ocena 2");

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
        columns.add(ocena1Col);
        columns.add(ocena2Col);

        ColumnModel<UPrzedmiotDTO> cm = new ColumnModel<UPrzedmiotDTO>(columns);
        grid = new Grid(store, cm);
        grid.setColumnReordering(true);
//        grid.getView().setAutoExpandColumn(userCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
        // State manager, make this grid stateful
        grid.setStateful(true);

        NumericFilter<UPrzedmiotDTO, Float> ocena1Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena1(),
            new FloatPropertyEditor());
        ocena1Filter.setActive(true, false);

        NumericFilter<UPrzedmiotDTO, Float> ocena2Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena2(),
            new FloatPropertyEditor());
        ocena2Filter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> nazwiskoFilter = new StringFilter<UPrzedmiotDTO>(props.uzytkownikNazwisko());
        nazwiskoFilter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> semFilter = new StringFilter<UPrzedmiotDTO>(props.semestrNazwa());
        semFilter.setActive(true, false);

        GridFilters<UPrzedmiotDTO> filters = new GridFilters<UPrzedmiotDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(ocena1Filter);
        filters.addFilter(ocena2Filter);
        filters.addFilter(nazwiskoFilter);//TODO zmienic na uzytkownikfilter
        filters.addFilter(semFilter);

        grid.setSelectionModel(new CellSelectionModel<UPrzedmiotDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);

        editing = new GridInlineEditing<UPrzedmiotDTO>(grid);
        FloatField ocena1Field = new FloatField();
        ocena1Field.addValidator(new OcenaValidator());
        NumberFormat nf = NumberFormat.getDecimalFormat();
        nf.overrideFractionDigits(1);
        ocena1Field.setFormat(nf);
        ocena1Field.addInvalidHandler(new InvalidHandler() {

            @Override
            public void onInvalid(InvalidEvent event) {
                editing.cancelEditing();
            }
        });

        FloatField ocena2Field = new FloatField();
        ocena2Field.setFormat(nf);
        ocena2Field.addValidator(new OcenaValidator());
        ocena2Field.addInvalidHandler(new InvalidHandler() {

            @Override
            public void onInvalid(InvalidEvent event) {
                editing.cancelEditing();
            }
        });
        editing.addEditor(ocena1Col, ocena1Field);
        editing.addEditor(ocena2Col, ocena2Field);
        editing.addEditor(dataSemCol, new DateField());

        btnDodaj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                UPrzedmiotDTO przedmiot = new UPrzedmiotDTO();
                przedmiot.setDataSemestru(new Date());
                przedmiot.setPrzedmiot(model.getPrzedmiot());
                przedmiot.setSemestr(przedmiot.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
                model.getPrzedmiot().getStudenci().add(przedmiot);

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

    public DateCell getDateCell() {
        return dateCell;
    }

    public ComboBoxCell<UserDTO> getComboUzytkownik() {
        return comboUzytkownik;
    }
}
