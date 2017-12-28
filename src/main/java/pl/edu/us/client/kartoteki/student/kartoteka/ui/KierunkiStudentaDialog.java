package pl.edu.us.client.kartoteki.student.kartoteka.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent.BeforeSelectHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.accesproperties.KierunekProperties;
import pl.edu.us.client.uzytkownik.kartoteka.KartotekaUzytkownikowModel;
import pl.edu.us.shared.enums.TypSemestru;

public class KierunkiStudentaDialog<Kierunek> extends Dialog implements IsWidget, Editor<Kierunek> {
	private final KartotekaUzytkownikowModel model;
	private final Grid<Kierunek> grid;
	ColumnConfig<Kierunek, String> nameCol;
	ColumnConfig<Kierunek, Integer> rokOdCol;
	ColumnConfig<Kierunek, TypSemestru> semCol;
	private final ListStore<Kierunek> store;
	GridInlineEditing editing;
	IntegerField rokField;
	TextButton zatwierdz, anuluj;
	SimpleComboBox<TypSemestru> semCombo;
	final GridRowEditing<Kierunek> rowEditing;

	@SuppressWarnings("unchecked")
	@Inject
	public KierunkiStudentaDialog(ListStore<Kierunek> store, KierunekProperties props,
			final KartotekaUzytkownikowModel model) {
		this.store = store;
		this.model = model;
		setHeaderVisible(false);
		setWidth(500);
		setHeight(500);
		// IdentityValueProvider<Kierunek> identity = new
		// IdentityValueProvider<Kierunek>();
		// final CheckBoxSelectionModel<Kierunek> selectionModel = new
		// CheckBoxSelectionModel<Kierunek>(identity);
		// selectionModel.setSelectionMode(SelectionMode.SIMPLE);
		// selectionModel.getColumn().setHeader("Z");
		// selectionModel.getColumn().setWidth(50);
		nameCol = new ColumnConfig<Kierunek, String>((ValueProvider<? super Kierunek, String>) props.nazwa(), 250,
				"Kierunek studiów");
		rokOdCol = new ColumnConfig<Kierunek, Integer>((ValueProvider<? super Kierunek, Integer>) props.rokOd(), 80,
				"Rok");
		semCol = new ColumnConfig<Kierunek, TypSemestru>(
				(ValueProvider<? super Kierunek, TypSemestru>) props.typSemestru(), 100, "Semestr");
		List<ColumnConfig<Kierunek, ?>> columns = new ArrayList<ColumnConfig<Kierunek, ?>>();
		// columns.add(selectionModel.getColumn());
		semCombo = new SimpleComboBox<TypSemestru>(new StringLabelProvider<TypSemestru>());
		semCombo.setClearValueOnParseError(false);
		semCombo.setTriggerAction(TriggerAction.ALL);
		semCombo.add(TypSemestru.LETNI);
		semCombo.add(TypSemestru.ZIMOWY);
		semCombo.setAllowBlank(false);

		rokField = new IntegerField();
		rokField.setAllowBlank(false);
		rokField.setAllowNegative(false);
		rokField.setFormat(NumberFormat.getFormat("####"));
		// rokOdCol.setCell(new Cell<Integer>());
		rokOdCol.setCell(new SimpleSafeHtmlCell<Integer>(new AbstractSafeHtmlRenderer<Integer>() {
			@Override
			public SafeHtml render(Integer object) {
				return SafeHtmlUtils.fromString(NumberFormat.getFormat("####").format(object));
			}
		}));
		rokOdCol.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		semCol.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		columns.add(nameCol);
		columns.add(rokOdCol);
		columns.add(semCol);

		ColumnModel<Kierunek> cm = new ColumnModel<Kierunek>(columns);
		grid = new Grid<Kierunek>(store, cm);
		grid.setColumnReordering(true);
		grid.getView().setAutoExpandColumn(nameCol);
		grid.setBorders(true);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);

		// State manager, make this grid stateful
		grid.setStateful(true);
		grid.setStateId("kierunkiStudentaGrid");

		GridFilters<Kierunek> filters = new GridFilters<Kierunek>();
		filters.initPlugin(grid);
		filters.setLocal(true);

		setWidget(grid);
		zatwierdz = new TextButton("Zatwierdź");
		zatwierdz.setWidth(80);
		getButtonBar().add(zatwierdz);
		zatwierdz.addBeforeSelectHandler(new BeforeSelectHandler() {

			@Override
			public void onBeforeSelect(BeforeSelectEvent event) {
				if (rowEditing.isEditing()) {
					event.setCancelled(true);
					Info.display("Błąd", "Proszę zakończyć edycję");
				}
			}
		});
		zatwierdz.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				model.getKierunki().commitChanges();
			}
		});
		rowEditing = new GridRowEditing<Kierunek>(grid);
		rowEditing.addEditor(rokOdCol, rokField);
		rowEditing.addEditor(semCol, semCombo);
		rowEditing.getButtonBar().setStyleName("button-align:center");
		rowEditing.getSaveButton().addBeforeSelectHandler(new BeforeSelectHandler() {

			@Override
			public void onBeforeSelect(BeforeSelectEvent event) {
				if (!rokField.isValid() || !semCombo.isValid() || rokField.getValue() != null
						&& (rokField.getValue() < 2000 || rokField.getValue() > 2020)) {
					System.out.print("zle wartosci");
					event.setCancelled(true);
				}
			}
		});

	}

	@Override
	protected void onButtonPressed(TextButton textButton) {
		if (!rowEditing.isEditing()) {
			hide();
			model.towrzOkresy();
			model.czyscKierunki();
		} else {
			Info.display("Błąd", "Proszę zakończyć edycję");
		}
	}

	public Grid<Kierunek> getGrid() {
		return grid;
	}

	public ListStore<Kierunek> getStore() {
		return store;
	}
}