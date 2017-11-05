package pl.edu.us.client.admin;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class AdminView extends ViewWithUiHandlers<AdminUiHandlers> implements AdminPresenter.MyView {

	AdminPanel panel;

	public AdminView() {
		panel = new AdminPanel();
		panel.setHeadingHtml("AdminPanel");
		panel.setHeight("100%");
		panel.setId("adminPanel");

		// setFrame(true);
		// setHeading("aaaaaaaaa");
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
}
