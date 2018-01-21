package pl.edu.us.client.wiadomosci.ui.wiadomosc;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.UserProperties;
import pl.edu.us.client.main.grid.KodFilter;
import pl.edu.us.client.main.handlers.FieldHandler;
import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;
import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Rola;

public class WiadomoscDialog extends Dialog implements FieldListener {

    private TextArea txtTemat;
    private TextArea txtWiadomosc;
    private Grid<UserDTO> grid;

    private WiadomosciModel model;
    private ListStore<UserDTO> store;
    private ColumnConfig<UserDTO, String> imieCol, nazwiskoCol, emailCol, rolaKodCol;
    private ColumnConfig<UserDTO, Rola> rolaCol;
    private ColumnConfig<UserDTO, Boolean> powiadomicCol;
    private CheckBoxCell checkAktywny, checkPowiadomic;
    private FieldHandler<String> listener = new FieldHandler<String>(this);

    private UserProperties props;
    private GridFilters<UserDTO> filters;
    private CheckBoxSelectionModel<UserDTO> selectionModel;
    private TextButton btnOk;

    public WiadomoscDialog(WiadomosciModel model) {
        this.model = model;
        this.props = model.getuProp();
        this.store = model.getStoreOdbiorcy();

        setHeadingText("Nowa wiadomość");
        IdentityValueProvider<UserDTO> identity = new IdentityValueProvider<UserDTO>();
        selectionModel = new CheckBoxSelectionModel<UserDTO>(identity);
        SafeStyles cbStyle = SafeStylesUtils.fromTrustedString("padding: 2px 12px;");

        imieCol = new ColumnConfig<UserDTO, String>(props.imie(), 80, "Imię");
        nazwiskoCol = new ColumnConfig<UserDTO, String>(props.nazwisko(), 100, "Nazwisko");
        emailCol = new ColumnConfig<UserDTO, String>(props.email(), 100, "E-mail");
//        rolaKodCol = new ColumnConfig<UserDTO, String>(props.rolaKod(), 30, "Rola");

        rolaCol = new ColumnConfig<UserDTO, Rola>(props.rola(), 90, "Rola");
        powiadomicCol = new ColumnConfig<UserDTO, Boolean>(props.powiadomic(), 55, "Powiadomić");

        checkPowiadomic = new CheckBoxCell();
        powiadomicCol.setCell(checkPowiadomic);
        powiadomicCol.setColumnStyle(cbStyle);
        List<ColumnConfig<UserDTO, ?>> columns = new ArrayList<ColumnConfig<UserDTO, ?>>();
        columns.add(selectionModel.getColumn());
        columns.add(imieCol);
        columns.add(nazwiskoCol);
        columns.add(emailCol);
        columns.add(rolaCol);
        columns.add(powiadomicCol);

        ColumnModel<UserDTO> cm = new ColumnModel<UserDTO>(columns);
        grid = new Grid<UserDTO>(store, cm);
        grid.setSelectionModel(selectionModel);
        grid.getView().setAutoExpandColumn(emailCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(false);

        grid.setSize("600", "500");
        KodFilter<UserDTO, Rola> rolaFilter = new KodFilter<UserDTO, Rola>(props.rola());
        filters = new GridFilters<UserDTO>();

        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(createFilter(props.imie()));
        filters.addFilter(createFilter(props.nazwisko()));
        filters.addFilter(createFilter(props.email()));
        filters.addFilter(rolaFilter);

        selectionModel.addSelectionHandler(new SelectionHandler<UserDTO>() {

            @Override
            public void onSelection(SelectionEvent<UserDTO> event) {
                onChangeFieldValue();
            }
        });
        txtTemat = new TextArea();
        txtTemat.setAllowBlank(false);
//        txtTemat.setWidth(350);
        txtWiadomosc = new TextArea();
        txtWiadomosc.setAllowBlank(false);
        txtTemat.addValueChangeHandler(listener);
        txtWiadomosc.addValueChangeHandler(listener);
//        txtWiadomosc.setWidth(350);
        ContentPanel cpr = new ContentPanel();
        cpr.setHeadingText("Odbiorcy");

        BorderLayoutContainer blc = new BorderLayoutContainer();
        BorderLayoutData eastData = new BorderLayoutData();
        eastData.setSize(500);
        eastData.setCollapsible(true);
//        eastData.setMargins(new Margins(1, 0, 1, 0));
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(null, "Temat"), new VerticalLayoutData(-1, -1));
        vlc.add(txtTemat, new VerticalLayoutData(1, -1));
        vlc.add(new FieldLabel(null, "Wiadomość"), new VerticalLayoutData(-1, -1));
        vlc.add(txtWiadomosc, new VerticalLayoutData(1, 1));

        cpr.add(grid);
        blc.setEastWidget(cpr, eastData);
        blc.setCenterWidget(vlc);

//        HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
//        hlc.add(vlc);
//        hlc.add(grid);

        add(blc);

        setSize("1200", "600");
        setModal(true);
        setHideOnButtonClick(true);
        btnOk = (TextButton) getButtonBar().getItemByItemId("OK");
        btnOk.setText("Wyślij");
        btnOk.setEnabled(false);
//        setClosable(false);

    }

    private StringFilter<UserDTO> createFilter(ValueProvider<UserDTO, String> provider) {
        StringFilter<UserDTO> filter = new StringFilter<UserDTO>(provider);
        filter.setActive(true, false);
        return filter;
    }

    public void initialState() {
        txtWiadomosc.clear();
        txtTemat.clear();
        selectionModel.deselectAll();
        filters.clearFilters();
    }

    @Override
    public void onChangeFieldValue() {
        btnOk.setEnabled(fieldIsValid(txtTemat)
            && fieldIsValid(txtWiadomosc) && selectionModel.getSelectedItems().size() > 0);
    }

    private boolean fieldIsValid(Field f) {
        return f.getValue() != null && f.isValid();
    }

    public TextArea getTxtTemat() {
        return txtTemat;
    }

    public TextArea getTxtWiadomosc() {
        return txtWiadomosc;
    }

    public Grid<UserDTO> getGrid() {
        return grid;
    }
}
