package pl.edu.us.client.main;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class ContentPageView extends ViewWithUiHandlers<ContentPageUiHandlers> implements ContentPagePresenter.MyView {

	ContentPanel center;

	private BorderLayoutContainer borderLayoutContainer;
	private BorderLayoutData northData;
	private MarginData centerData;
	Viewport viewport;

	public ContentPageView() {
		northData = new BorderLayoutData(30);

		centerData = new MarginData();
		center = new ContentPanel();
		center.setHeadingText("BorderLayout Example");
		center.setResize(false);
		center.setId("myDecoratedPanelStyle");

		borderLayoutContainer = new BorderLayoutContainer();
		borderLayoutContainer.setBorders(true);

		viewport = new Viewport();
		viewport.setBorders(false);
		viewport.add(borderLayoutContainer);
		viewport.setId("ViePort");
	}

	@Override
	public Widget asWidget() {
		return viewport;
	}

	@Override
	public void addToSlot(Object slot, IsWidget content) {

	}

	@Override
	public void removeFromSlot(Object slot, IsWidget content) {

	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == ContentPagePresenter.TYPE_MENU) {
			borderLayoutContainer.setNorthWidget(content.asWidget(), northData);
		} else if (slot == ContentPagePresenter.TYPE_CONTENT) {
			borderLayoutContainer.setCenterWidget(content, centerData);
		}
		borderLayoutContainer.forceLayout();
	}
}