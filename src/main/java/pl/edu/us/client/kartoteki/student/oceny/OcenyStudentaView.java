package pl.edu.us.client.kartoteki.student.oceny;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.shared.model.old.Student;

public class OcenyStudentaView extends BaseView<OcenyStudentaUiHandlers> implements
		OcenyStudentaPresenter.MyView {

	private final OcenyStudentaMainPanel panel;
	private final OcenyStudentaModel model;

	public OcenyStudentaView() {
		model = new OcenyStudentaModel();
		panel = new OcenyStudentaMainPanel(model);
	}

	@Override
	protected void bindCustomUiHandlers() {
//		panel.getLista().getGrid().getSelectionModel()
//				.addSelectionChangedHandler(new SelectionChangedHandler<Student>() {
//					@Override
//					public void onSelectionChanged(SelectionChangedEvent<Student> event) {
//						if (event.getSelection().size() > 0) {
//							model.setSelected(panel.getLista().getGrid().getSelectionModel().getSelectedItem());
//						} else {
//							model.setSelected(null);
//						}
//					}
//				});
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
}
