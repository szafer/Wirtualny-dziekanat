package pl.edu.us.client.kartoteki.student.kartoteka;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.model.Student;

public class KartotekaStudentaView extends BaseView<KartotekaStudentaUiHandlers> implements
		KartotekaStudentaPresenter.MyView {

	private final KartotekaStudentaMainPanel panel;
	private final KartotekaStudentaModel model;
	public KartotekaStudentaView() {
		model = new KartotekaStudentaModel();
		panel = new KartotekaStudentaMainPanel(model);
	}

	@Override
	protected void bindCustomUiHandlers() {
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
				;
			}
		});
		panel.getZamknij().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				getUiHandlers().wykonajZamknij();
				;
			}
		});
		panel.getLista().getGrid().getSelectionModel()
				.addSelectionChangedHandler(new SelectionChangedHandler<Student>() {
					@Override
					public void onSelectionChanged(SelectionChangedEvent<Student> event) {
						if (event.getSelection().size() > 0) {
							panel.getPanel().getDriver()
									.edit(panel.getLista().getGrid().getSelectionModel().getSelectedItem());
							// zatwierdz.setEnabled(true);
							model.setSelected(panel.getLista().getGrid().getSelectionModel().getSelectedItem());
							panel.getPanel().setSaveEnabled(true);
						} else {
							panel.getPanel().setSaveEnabled(false);
							model.setSelected(null);
						}
					}
				});
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public KartotekaStudentaModel getModel() {
		return model;
	}
}