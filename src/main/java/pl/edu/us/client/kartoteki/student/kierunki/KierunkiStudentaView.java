package pl.edu.us.client.kartoteki.student.kierunki;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class KierunkiStudentaView extends ViewWithUiHandlers<KierunkiStudentaUiHandlers> implements
		KierunkiStudentaPresenter.MyView {

	KierunkiStudentaPanel panel;

	public KierunkiStudentaView() {
		panel = new KierunkiStudentaPanel();
		// panel.setHeadingHtml("AdminPanel");
		// panel.setHeight("100%");
		// panel.setId("adminPanel");

		// setFrame(true);
		// setHeading("aaaaaaaaa");
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
}

