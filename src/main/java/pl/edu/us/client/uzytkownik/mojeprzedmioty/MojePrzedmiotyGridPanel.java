package pl.edu.us.client.uzytkownik.mojeprzedmioty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.FloatPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import pl.edu.us.client.accesproperties.UPrzedmiotProperties;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Rola;

public class MojePrzedmiotyGridPanel extends ContentPanel implements IsWidget, Editor<UPrzedmiotDTO> {

    private ColumnConfig<UPrzedmiotDTO, Date> dataSemCol;
    private ColumnConfig<UPrzedmiotDTO, Float> ocena1Col, ocena2Col;
    private ColumnConfig<UPrzedmiotDTO, String> przedmiotCol, semestrCol;
    private final Grid<UPrzedmiotDTO> grid;
    private final ListStore<UPrzedmiotDTO> store;
    private final GridInlineEditing<UPrzedmiotDTO> editing;
    private Rola rola;

    public MojePrzedmiotyGridPanel(ListStore<UPrzedmiotDTO> store, UPrzedmiotProperties props, Rola rola) {
        this.store = store;
        this.rola = rola;
        setHeadingText("Przedmioty");
//        ColumnConfig<UPrzedmiotDTO, String> nameCol = new ColumnConfig<UPrzedmiotDTO, String>(props.nazwa(), 200, "Nazwa");
        dataSemCol = new ColumnConfig<UPrzedmiotDTO, Date>(props.dataSemestru(), 100, "Data semestru");
        ocena1Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena1(), 60, "Ocena 1");
        ocena2Col = new ColumnConfig<UPrzedmiotDTO, Float>(props.ocena2(), 60, "Ocena 2");
        semestrCol = new ColumnConfig<UPrzedmiotDTO, String>(props.semestrNazwa(), 100, "Semestr");
        przedmiotCol = new ColumnConfig<UPrzedmiotDTO, String>(props.przedmiotNazwa(), 150, "Przedmiot");
        //        maxGrupaCol = new ColumnConfig<UPrzedmiotDTO, Integer>(props.maxGrupa(), 100, "Ilość studentów");

        final NumberFormat number = NumberFormat.getFormat("0.00");

        List<ColumnConfig<UPrzedmiotDTO, ?>> columns = new ArrayList<ColumnConfig<UPrzedmiotDTO, ?>>();
//        columns.add(nameCol);
        columns.add(dataSemCol);
        columns.add(semestrCol);
        columns.add(przedmiotCol);
        if (rola == Rola.STUDENT) {
            columns.add(ocena1Col);
            columns.add(ocena2Col);
        }
//        columns.add(maxGrupaCol);
        
        ColumnModel<UPrzedmiotDTO> cm = new ColumnModel<UPrzedmiotDTO>(columns);
        grid = new Grid<UPrzedmiotDTO>(store, cm);
        grid.setColumnReordering(true);
        grid.getView().setAutoExpandColumn(przedmiotCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getView().setShowDirtyCells(true);

        // State manager, make this grid stateful
        grid.setStateful(true);
        grid.setStateId("filterGridExample");

        NumericFilter<UPrzedmiotDTO, Float> ocena1Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena1(),
            new FloatPropertyEditor());
        ocena1Filter.setActive(true, false);

        NumericFilter<UPrzedmiotDTO, Float> ocena2Filter = new NumericFilter<UPrzedmiotDTO, Float>(props.ocena2(),
            new FloatPropertyEditor());
        ocena2Filter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> nameFilter = new StringFilter<UPrzedmiotDTO>(props.przedmiotNazwa());
        nameFilter.setActive(true, false);

        StringFilter<UPrzedmiotDTO> semFilter = new StringFilter<UPrzedmiotDTO>(props.semestrNazwa());
        semFilter.setActive(true, false);

        GridFilters<UPrzedmiotDTO> filters = new GridFilters<UPrzedmiotDTO>();
        filters.initPlugin(grid);
        filters.setLocal(true);
        filters.addFilter(ocena1Filter);
        filters.addFilter(ocena2Filter);
        filters.addFilter(nameFilter);
        filters.addFilter(semFilter);

        editing = new GridInlineEditing<UPrzedmiotDTO>(grid);

//        editing.addEditor(dataSemCol, new DateField());
//        editing.addEditor(ocena1Col, new FloatField());
//        editing.addEditor(ocena2Col, new FloatField());

        // column 5 is not editable

        // EDITING //
        // customizeGrid(grid);
//        grid.setSelectionModel(new CellSelectionModel<UPrzedmiotDTO>());

        grid.getColumnModel().getColumn(0).setHideable(false);
//        panel = new ContentPanel();
//        panel.setHeadingText("Kierunki");
//        // panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
//        panel.setPixelSize(700, 500);
//        panel.addStyleName("margin-10");
//        panel.setWidget(grid);
        add(grid);
    }

    public Grid<UPrzedmiotDTO> getGrid() {
        return grid;
    }

    public GridEditing<UPrzedmiotDTO> getEditing() {
        return editing;
    }
}
