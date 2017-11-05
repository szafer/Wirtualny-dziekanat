package pl.edu.us.client.symulacja.przychodykosztykierunki;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.symulacja.przychodykosztykierunki.ui.PKKierunkiPanel;
import pl.edu.us.shared.model.ViPrzychod;

public class PKKierunkiView extends BaseView<PKKierunkiUiHandlers> implements PKKierunkiPresenter.MyView {

	private final PKKierunkiPanel panel;
	private final PKKierunkiModel model;

	public PKKierunkiView() {
		model = new PKKierunkiModel();
		panel = new PKKierunkiPanel(model);
	}

	@Override
	protected void bindCustomUiHandlers() {
		panel.getRokOd().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				filtrujChart();

			}
		});
		panel.getRokDo().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				filtrujChart();
			}
		});
		panel.getRokWykres2().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				if (panel.getRokWykres2().getValue() == null) {
					panel.getWykresRokBtn().setEnabled(false);
				} else {
					filtrujStoreRok();
					panel.getWykresRokBtn().setEnabled(true);
				}

			}
		});
		panel.getRokWykres3().addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				if (panel.getRokWykres3().getValue() == null) {
					panel.getWykresKierunkiBtn().setEnabled(false);
				} else {
					filtrujStoreKierunki();
					panel.getWykresKierunkiBtn().setEnabled(true);
				}
			}
		});
		panel.getWykresRokBtn().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				panel.pokazSymulacjaRok();

			}
		});
		panel.getWykresKierunkiBtn().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				panel.pokazSymulacjaKierunki();

			}
		});
	}

	@Override
	public PKKierunkiModel getModel() {
		return model;
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public void filtrujChart() {

		Integer rokMin = 2100;
		Integer rokMax = 1900;
		for (ViPrzychod bean : model.getStoreAll().getAll()) {
			if (bean.getRok() < rokMin)
				rokMin = bean.getRok();
			if (bean.getRok() > rokMax)
				rokMax = bean.getRok();
		}
		Integer rokOd = panel.getRokOd().getValue() == null ? rokMin : panel.getRokOd().getValue();
		Integer rokDo = panel.getRokDo().getValue() == null ? rokMax : panel.getRokDo().getValue();
		model.getStoreLata().clear();
		for (int i = rokOd; i < rokDo + 1; i++) {
			BigDecimal koszt = BigDecimal.ZERO;
			BigDecimal przychod = BigDecimal.ZERO;
			BigDecimal dochod = BigDecimal.ZERO;
			for (ViPrzychod bean : model.getStoreAll().getAll()) {
				if (bean.getRok().compareTo(i) == 0) {
					koszt = koszt.add(bean.getKoszt());
					przychod = przychod.add(bean.getPrzychod());
					dochod = dochod.add(bean.getDochod());
				}
			}
			ViPrzychod sym = new ViPrzychod();
			sym.setId(i);
			sym.setRok(i);
			sym.setKoszt(koszt);
			sym.setPrzychod(przychod);
			sym.setDochod(dochod);
			model.getStoreLata().add(sym);
		}
		// for (ViPrzychod bean : model.getStoreRokAll().getAll()) {
		// if (bean.getRok() >= rokOd && bean.getRok() <= rokDo) {
		// model.getStoreRok().add(bean);
		// }
		// }
		if (model.getStoreLata().size() == 1) {
			panel.getBtnLiniowy().setEnabled(false);
		} else {
			panel.getBtnLiniowy().setEnabled(true);

		}
		panel.onBtnLiniowyClick();
	}

	@Override
	public void filtrujStoreRok() {
		Integer rok = panel.getRokWykres2().getValue();
		model.getStoreRok().clear();
		model.getStoreAll().addSortInfo(new StoreSortInfo(new Porownaj(), SortDir.ASC));
		List<String> miesiace = new ArrayList<String>();
		for (ViPrzychod bean : model.getStoreAll().getAll()) {
			if (!miesiace.contains(bean.getMiesiac()))
				miesiace.add(bean.getMiesiac());
		}
		for (int i = 0; i < miesiace.size(); i++) {
			BigDecimal koszt = BigDecimal.ZERO;
			BigDecimal przychod = BigDecimal.ZERO;
			BigDecimal dochod = BigDecimal.ZERO;
			for (ViPrzychod bean : model.getStoreAll().getAll()) {
				if (bean.getRok().compareTo(rok) == 0 && bean.getMc() == i + 1) {
					koszt = koszt.add(bean.getKoszt());
					przychod = przychod.add(bean.getPrzychod());
					dochod = dochod.add(bean.getDochod());

				}
			}
			ViPrzychod sym = new ViPrzychod();
			sym.setId(i);
			sym.setRok(rok);
			sym.setMc(i + 1);
			sym.setMiesiac(miesiace.get(i));
			sym.setKoszt(koszt);
			sym.setPrzychod(przychod);
			sym.setDochod(dochod);
			model.getStoreRok().add(sym);
		}
	}

	@Override
	public void filtrujStoreKierunki() {
		Integer rok = panel.getRokWykres3().getValue();
		model.getStoreKierunek().clear();
		List<String> kierunki = new ArrayList<String>();
		for (ViPrzychod bean : model.getStoreAll().getAll()) {
			if (!kierunki.contains(bean.getKierunek()))
				kierunki.add(bean.getKierunek());
		}
		for (int i = 0; i < kierunki.size(); i++) {
			String kierunek = kierunki.get(i);
			BigDecimal koszt = BigDecimal.ZERO;
			BigDecimal przychod = BigDecimal.ZERO;
			BigDecimal dochod = BigDecimal.ZERO;
			for (ViPrzychod bean : model.getStoreAll().getAll()) {
				if (bean.getRok().compareTo(rok) == 0 && kierunek.compareTo(bean.getKierunek()) == 0) {
					koszt = koszt.add(bean.getKoszt());
					przychod = przychod.add(bean.getPrzychod());
					dochod = dochod.add(bean.getDochod());
				}
			}
			ViPrzychod sym = new ViPrzychod();
			sym.setId(i);
			sym.setRok(rok);
			sym.setKierunek(kierunki.get(i));
			sym.setKoszt(koszt);
			sym.setPrzychod(przychod);
			sym.setDochod(dochod);
			model.getStoreKierunek().add(sym);
		}
	}

	private class Porownaj implements Comparator<ViPrzychod> {

		@Override
		public int compare(ViPrzychod o1, ViPrzychod o2) {
			return o1.getMc().compareTo(o2.getMc());
		}

	};

}