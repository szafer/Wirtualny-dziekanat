package pl.edu.us.client.uzytkownik.kartoteka;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import pl.edu.us.client.main.BaseView;

public class UzytkownicyView extends BaseView<UzytkownicyUiHandlers> implements
		UzytkownicyPresenter.MyView {

	private final UzytkownicyMainPanel panel;
	private final UzytkownicyModel model;
	@Inject
	public UzytkownicyView() {
		model = new UzytkownicyModel();
		panel = new UzytkownicyMainPanel(model);
	}

	@Override
	protected void bindCustomUiHandlers() {
	    panel.getPanel().setUHandlers(getUiHandlers());
		panel.getZapisz().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				getUiHandlers().wykonajZapisz();
			}
		});
		panel.getAnuluj().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				getUiHandlers().wykonajAnuluj();
			}
		});
		panel.getZamknij().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				getUiHandlers().wykonajZamknij();
			}
		});
//        panel.getPanel().getGrid().getSelectionModel().addSelectionHandler(new SelectionHandler<UserDTO>() {
//
//            @Override
//            public void onSelection(SelectionEvent<UserDTO> event) {
//                if (event.getSelectedItem() != null) {
//                   model.setSelected(event.getSelectedItem());
//                }
//                panel.setButtonState();
//            }
//        });
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public UzytkownicyModel getModel() {
		return model;
	}
	@Override
	public UzytkownicyMainPanel getPanel(){
	    return panel;
	}
}