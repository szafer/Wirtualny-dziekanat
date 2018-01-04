package pl.edu.us.client.uzytkownik.kartoteka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import pl.edu.us.client.accesproperties.UserProperties;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;
import pl.edu.us.shared.services.user.UserService;
import pl.edu.us.shared.services.user.UserServiceAsync;

public class UzytkownicyGridPanel extends ContentPanel {

    private ColumnConfig<UserDTO, String> loginCol, imieCol, nazwiskoCol,
        emailCol, ulicaCol, nrDomuCol, nrMieszkaniaCol, miastoCol, kodPocztowyCol;
//    private ColumnConfig<UserDTO, Float> ocena1Col, ocena2Col;
    private ColumnConfig<UserDTO, Plec> plecCol;
    private ColumnConfig<UserDTO, Rola> rolaCol;
    private ColumnConfig<UserDTO, Boolean> aktywnyCol;
    private ColumnConfig<UserDTO, Integer> idCol, iloscLogowanCol;
    private ColumnConfig<UserDTO, Date> dataUrCol;
    private final Grid<UserDTO> grid;
    private final ListStore<UserDTO> store;
    private final GridInlineEditing<UserDTO> editing;
    private DateCell dateCell;
    private ComboBoxCell<Plec> comboPlec;
    private ComboBoxCell<Rola> comboRola;
    private CheckBoxCell checkAktywny;
    private UserProperties props;

    public UzytkownicyGridPanel(UzytkownicyModel model) {
        this.store = model.getStoreUsers();
        this.props = model.getUserProp();
        final UserServiceAsync service = GWT.create(UserService.class);

        ListStore<UserDTO> store = new ListStore<UserDTO>(props.key());

        RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UserDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UserDTO>>() {
            @Override
            public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UserDTO>> callback) {
                service.getUsers(loadConfig, callback);
            }
        };
        final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<UserDTO>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<UserDTO>>(
            proxy) {
            @Override
            protected FilterPagingLoadConfig newLoadConfig() {
                return new FilterPagingLoadConfigBean();
            }
        };
        remoteLoader.setRemoteSort(true);
        remoteLoader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, UserDTO, PagingLoadResult<UserDTO>>(store));

        setHeadingText("Użytkownicy");
        SafeStyles dataUrStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");
        SafeStyles cbStyle = SafeStylesUtils.fromTrustedString("padding: 2px 12px;");
        idCol = new ColumnConfig<>(props.id(), 30, "ID");
        loginCol = new ColumnConfig<UserDTO, String>(props.login(), 100, "Login");
        imieCol = new ColumnConfig<UserDTO, String>(props.imie(), 100, "Imię");
        nazwiskoCol = new ColumnConfig<UserDTO, String>(props.nazwisko(), 150, "Nazwisko");
        dataUrCol = new ColumnConfig<UserDTO, Date>(props.dataUrodzenia(), 130, "Data urodzenia");
        emailCol = new ColumnConfig<UserDTO, String>(props.email(), 100, "E-mail");
        ulicaCol = new ColumnConfig<UserDTO, String>(props.ulica(), 100, "Ulica");
        nrDomuCol = new ColumnConfig<UserDTO, String>(props.nrDomu(), 100, "Nr domu");
        nrMieszkaniaCol = new ColumnConfig<UserDTO, String>(props.nrMieszkania(), 100, "Nr mieszkania");
        miastoCol = new ColumnConfig<UserDTO, String>(props.miasto(), 100, "Miasto");
        kodPocztowyCol = new ColumnConfig<UserDTO, String>(props.kodPocztowy(), 100, "Kod pocztowy");
        plecCol = new ColumnConfig<UserDTO, Plec>(props.plec(), 140, "Płeć");
        rolaCol = new ColumnConfig<UserDTO, Rola>(props.rola(), 140, "Rola");
        aktywnyCol = new ColumnConfig<UserDTO, Boolean>(props.aktywny(), 50, "Aktywny");
        iloscLogowanCol = new ColumnConfig<>(props.iloscLogowan(), 50, "Pozostało </br>logowań");

        dateCell = new DateCell();
        dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
        dateCell.setWidth(120);
        dataUrCol.setCell(dateCell);
        dataUrCol.setColumnTextStyle(dataUrStyle);

        comboPlec = new ComboBoxCell<Plec>(model.getStorePlec(), new LabelProvider<Plec>() {

            @Override
            public String getLabel(Plec item) {
                return item.toString();
            }
        });
        comboPlec.setTriggerAction(TriggerAction.ALL);
        comboPlec.setForceSelection(true);
        comboPlec.setWidth(120);
        plecCol.setCell(comboPlec);
        plecCol.setColumnStyle(dataUrStyle);

        comboRola = new ComboBoxCell<Rola>(model.getStoreRola(), new LabelProvider<Rola>() {

            @Override
            public String getLabel(Rola item) {
                return item.toString();
            }
        });
        comboRola.setTriggerAction(TriggerAction.ALL);
        comboRola.setForceSelection(true);
        comboRola.setWidth(120);
        rolaCol.setCell(comboRola);
        rolaCol.setColumnStyle(dataUrStyle);

        checkAktywny = new CheckBoxCell();

        aktywnyCol.setCell(checkAktywny);
//        aktywnyCol.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        aktywnyCol.setColumnStyle(cbStyle);
//        VerticalAlignmentConstant align = VerticalAlignmentConstant.
        List<ColumnConfig<UserDTO, ?>> columns = new ArrayList<ColumnConfig<UserDTO, ?>>();
        columns.add(loginCol);
        columns.add(imieCol);
        columns.add(nazwiskoCol);
        columns.add(dataUrCol);
        columns.add(emailCol);
        columns.add(ulicaCol);
        columns.add(nrDomuCol);
        columns.add(nrMieszkaniaCol);
        columns.add(miastoCol);
        columns.add(kodPocztowyCol);
        columns.add(plecCol);
        columns.add(rolaCol);
        columns.add(aktywnyCol);
        columns.add(iloscLogowanCol);
        ustawPoziom(columns);

        ColumnModel<UserDTO> cm = new ColumnModel<UserDTO>(columns) {

        };
        grid = new Grid<UserDTO>(store, cm) {
            @Override
            protected void onAfterFirstAttach() {
                super.onAfterFirstAttach();
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    @Override
                    public void execute() {
                        remoteLoader.load();
                    }
                });
            }
        };
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(emailCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
//        grid.getView().setForceFit(true);
        // State manager, make this grid stateful
        grid.setStateful(true);

        NumericFilter<UserDTO, Integer> iloscLogowanFilter = new NumericFilter<UserDTO, Integer>(props.iloscLogowan(),
            new IntegerPropertyEditor());
        iloscLogowanFilter.setActive(true, false);

        DateFilter<UserDTO> dataUrFilter = new DateFilter<UserDTO>(props.dataUrodzenia());
        dataUrFilter.setActive(true, false);
//
        NumericFilter<UserDTO, Integer> idFilter = new NumericFilter<UserDTO, Integer>(props.id(),
            new IntegerPropertyEditor());
        idFilter.setActive(true, false);
//
        BooleanFilter<UserDTO> aktywnyFilter = new BooleanFilter<UserDTO>(props.aktywny());
        aktywnyFilter.setActive(true, false);

        GridFilters<UserDTO> filters = new GridFilters<UserDTO>(remoteLoader);
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(idFilter);
        filters.addFilter(createFilter(props.login()));
        filters.addFilter(createFilter(props.imie()));
        filters.addFilter(createFilter(props.nazwisko()));
        filters.addFilter(createFilter(props.email()));
        filters.addFilter(createFilter(props.ulica()));
        filters.addFilter(createFilter(props.nrDomu()));
        filters.addFilter(createFilter(props.nrMieszkania()));
        filters.addFilter(createFilter(props.miasto()));
        filters.addFilter(createFilter(props.kodPocztowy()));
        filters.addFilter(dataUrFilter);
        filters.addFilter(createFilter(props.rolaKod()));
        filters.addFilter(createFilter(props.plecKod()));
        filters.addFilter(iloscLogowanFilter);
        filters.addFilter(aktywnyFilter);

        editing = new GridInlineEditing<UserDTO>(grid);
        editing.addEditor(loginCol, new TextField());
        editing.addEditor(imieCol, new TextField());
        editing.addEditor(nazwiskoCol, new TextField());
        editing.addEditor(emailCol, new TextField());//parser
        editing.addEditor(ulicaCol, new TextField());
        editing.addEditor(nrDomuCol, new TextField());
        editing.addEditor(nrMieszkaniaCol, new TextField());
        editing.addEditor(miastoCol, new TextField());
        editing.addEditor(kodPocztowyCol, new TextField());
//        editing.addEditor(aktywnyCol, new CheckBox());
        editing.addEditor(iloscLogowanCol, new IntegerField());

//        grid.setSelectionModel(new CellSelectionModel<UserDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
//        add(grid);
        VerticalLayoutContainer con = new VerticalLayoutContainer();
        
        final PagingToolBar toolBar = new PagingToolBar(25);
        toolBar.bind(remoteLoader);
        con.add(grid, new VerticalLayoutData(1, 1));
        con.add(toolBar, new VerticalLayoutData(1, -1));
        
       add(con);
    }

    private void ustawPoziom(List<ColumnConfig<UserDTO, ?>> columns) {
        for (ColumnConfig<UserDTO, ?> col : columns) {
            col.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        }
    }

    private StringFilter<UserDTO> createFilter(ValueProvider<UserDTO, String> provider) {
        StringFilter<UserDTO> filter = new StringFilter<UserDTO>(provider);
        filter.setActive(true, false);
        return filter;
    }

    public Grid<UserDTO> getGrid() {
        return grid;
    }

    public GridInlineEditing<UserDTO> getEditing() {
        return editing;
    }
}
