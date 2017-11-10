package pl.edu.us.client.details;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.View;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class DetailView implements View, DetailPresenter.MyView {

	ContentPanel panel;

	public DetailView() {
		panel = new ContentPanel();
		panel.setHeadingHtml("ContentPanel");
		panel.setHeaderVisible(false);
		panel.setHeight("100%");
		panel.setBodyStyle("background-image: url(us.jpeg)");
		panel.getBody().addClassName("bg");//wyśrodkowanie logo
//		panel.setStylePrimaryName("bg");		
		// panel.setBodyStyle("background-color: red;");
		panel.setId("widokPoniżejMenuBara"); // setFrame(true);

	}

	@Override
	public Widget asWidget() {

		// Viewport viewport = new Viewport();
		// viewport.setBorders(false);
		// // viewport.setLayout(new FitLayout());
		// viewport.add(this);
		return panel;
	}
	
	

	@Override
	public void addToSlot(Object slot, IsWidget content) {
		
	}

	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
		panel.setWidget(null);
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == DetailPresenter.TAB_CONTENT) {
			panel.setWidget(content);
		}
	}
}