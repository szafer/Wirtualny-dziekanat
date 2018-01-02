package pl.edu.us.client.uzytkownik.mojeprzedmioty.nauczyciel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.InvalidEvent;
import com.sencha.gxt.widget.core.client.event.InvalidEvent.InvalidHandler;
import com.sencha.gxt.widget.core.client.form.FloatField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.FloatPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.client.main.validators.OcenaValidator;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;

public class OcenyGridPanel extends ContentPanel implements IsWidget, Editor<UPrzedmiotDTO> {

    private ColumnConfig<UPrzedmiotDTO, String> imieCol, nazwiskoCol;
    private ColumnConfig<UPrzedmiotDTO, Float> ocena1Col, ocena2Col;
    private final Grid<UPrzedmiotDTO> grid;
    private final ListStore<UPrzedmiotDTO> store;
    private final GridEditing<UPrzedmiotDTO> editing;

    public OcenyGridPanel(ListStore<UPrzedmiotDTO> store, UPrzedmiotProperties props) {
        this.store = store;
        setHeadingText("Oceny studentów");
        imieCol = new ColumnConfig<UPrzedmiotDTO, String>(props.uzytkownikImie(), 100, "Imię");
        nazwiskoCol = new ColumnConfig<UPrzedmiotDTO, String>(props.uzytkownikNazwisko(), 150, "Nazwisko");
        ocena1Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena1(), 60, "Ocena 1");
        ocena2Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena2(), 60, "Ocena 2");

        final NumberFormat number = NumberFormat.getFormat("0.00");

        List<ColumnConfig<UPrzedmiotDTO, ?>> columns = new ArrayList<ColumnConfig<UPrzedmiotDTO, ?>>();
//        columns.add(nameCol);

        columns.add(imieCol);
        columns.add(nazwiskoCol);
        columns.add(ocena1Col);
        columns.add(ocena2Col);

//        columns.add(maxGrupaCol);

        ColumnModel<UPrzedmiotDTO> cm = new ColumnModel<UPrzedmiotDTO>(columns);
        grid = new Grid(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(nazwiskoCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);
        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        StringFilter<UPrzedmiotDTO> imieFilter = new StringFilter<UPrzedmiotDTO>(props.uzytkownikImie());
        imieFilter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> nazwiskoFilter = new StringFilter<UPrzedmiotDTO>(props.uzytkownikNazwisko());
        nazwiskoFilter.setActive(true, false);

        NumericFilter<UPrzedmiotDTO, Float> ocena1Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena1(),
            new FloatPropertyEditor());
        ocena1Filter.setActive(true, false);

        NumericFilter<UPrzedmiotDTO, Float> ocena2Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena2(),
            new FloatPropertyEditor());
        ocena2Filter.setActive(true, false);

        GridFilters<UPrzedmiotDTO> filters = new GridFilters<UPrzedmiotDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(ocena1Filter);
        filters.addFilter(ocena2Filter);
        filters.addFilter(imieFilter);
        filters.addFilter(nazwiskoFilter);

        editing = new GridInlineEditing<UPrzedmiotDTO>(grid);

//        editing.addEditor(dataSemCol, new DateField());
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


        grid.setSelectionModel(new CellSelectionModel<UPrzedmiotDTO>());
        grid.getColumnModel().getColumn(0).setHideable(false);
        add(grid);
    }

    public Grid<UPrzedmiotDTO> getGrid() {
        return grid;
    }

    public GridEditing<UPrzedmiotDTO> getEditing() {
        return editing;
    }
}
