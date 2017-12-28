package pl.edu.us.client.symulacja.przychodykoszty;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.symulacja.przychodykoszty.ui.PrzychodyKosztyPanel;
import pl.edu.us.shared.model.old.ViPrzychod;

public class PrzychodyKosztyView extends BaseView<PrzychodyKosztyUiHandlers> implements PrzychodyKosztyPresenter.MyView {

	private final PrzychodyKosztyPanel panel;
	private final PrzychodyKosztyModel model;

	public PrzychodyKosztyView() {
		model = new PrzychodyKosztyModel();
		panel = new PrzychodyKosztyPanel(model);
	}

	@Override
	protected void bindCustomUiHandlers() {
		panel.getGridPanel().getGrid().getSelectionModel()
				.addSelectionChangedHandler(new SelectionChangedHandler<ViPrzychod>() {
					@Override
					public void onSelectionChanged(SelectionChangedEvent<ViPrzychod> event) {
						if (event.getSelection().size() > 0) {
							// panel.getWyliczoneDriver().edit(
							// panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem());
							filtrujStoreRok();
						}
					}
				});
		panel.getWykresBtn().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				if (panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem() != null)
					panel.getDialog().show();
				else
					Info.display("Info", "Proszę zaznaczyć dane do wykresu ");
			}
		});
		panel.getRokOd().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				filtrujGrida();

			}
		});
		panel.getRokDo().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				filtrujGrida();
			}
		});
		panel.getWyliczonePanel().getIlStud().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> arg0) {
				symuluj(arg0.getValue());
			}
		});

	}

	@Override
	public PrzychodyKosztyModel getModel() {
		return model;
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public void filtrujGrida() {
		Integer rokOd = panel.getRokOd().getValue() == null ? 1900 : panel.getRokOd().getValue();
		Integer rokDo = panel.getRokDo().getValue() == null ? 2100 : panel.getRokDo().getValue();
		model.getStorePrzychody().clear();

		for (ViPrzychod bean : model.getStorePrzychodyAll().getAll()) {
			if (bean.getRok() >= rokOd && bean.getRok() <= rokDo) {
				model.getStorePrzychody().add(bean);

			}
		}
		filtrujStoreRok();
	}

	@Override
	public void filtrujStoreRok() {
		ViPrzychod selected = panel.getGridPanel().getGrid().getSelectionModel().getSelectedItem();
		model.getStoreViPrzychod().clear();
		if (selected != null) {
			for (ViPrzychod bean : model.getStorePrzychodyAll().getAll()) {
				if (bean.getRok().compareTo(selected.getRok()) == 0) {
					model.getStoreViPrzychod().add(bean);
				}
			}
		}
		if (model.getStoreViPrzychod().size() == 1) {
			panel.getDialog().getBtnLiniowy().setEnabled(false);
		} else {
			panel.getDialog().getBtnLiniowy().setEnabled(true);
		}
		BigDecimal koszt = BigDecimal.ZERO;
		BigDecimal przychod = BigDecimal.ZERO;
		BigDecimal dochod = BigDecimal.ZERO;
		Integer ilStud = 0, licznik = 0;

		for (ViPrzychod bean : model.getStorePrzychody().getAll()) {
			koszt = koszt.add(bean.getKoszt());
			przychod = przychod.add(bean.getPrzychod());
			dochod = dochod.add(bean.getDochod());
			ilStud += bean.getIlStud();
			licznik++;
		}
		ilStud = ilStud == 0 ? 1 : ilStud;
		koszt = koszt.divide(BigDecimal.valueOf(ilStud.longValue()), 2, RoundingMode.HALF_UP);
		przychod = przychod.divide(BigDecimal.valueOf(ilStud.longValue()), 2, RoundingMode.HALF_UP);
		dochod = dochod.divide(BigDecimal.valueOf(ilStud.longValue()), 2, RoundingMode.HALF_UP);
		ViPrzychod sym = new ViPrzychod();
		sym.setId(999);
		sym.setIlStud(1);
		sym.setKoszt(koszt);
		sym.setPrzychod(przychod);
		sym.setDochod(dochod);
		panel.getWyliczoneDriver().edit(sym);
	}

	public void symuluj(Integer ilStud) {
		ViPrzychod sym = panel.getWyliczoneDriver().flush();
		BigDecimal koszt = sym.getKoszt().multiply(BigDecimal.valueOf(ilStud.longValue()));
		BigDecimal przychod = sym.getPrzychod().multiply(BigDecimal.valueOf(ilStud.longValue()));
		BigDecimal dochod = sym.getDochod().multiply(BigDecimal.valueOf(ilStud.longValue()));
		sym.setKoszt(koszt);
		sym.setPrzychod(przychod);
		sym.setDochod(dochod);
		panel.getWyliczoneDriver().edit(sym);
	}
}
