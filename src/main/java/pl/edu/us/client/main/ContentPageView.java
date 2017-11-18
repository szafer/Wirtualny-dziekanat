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
        center.setResize(false);
        center.setHeaderVisible(false);
        center.setId("Tlo_Aplikacji"); // setFrame(true);
        center.setHeight("100%");
        center.setBodyStyle("background-image: url(us.jpeg)");
        center.getBody().addClassName("bg");//wyśrodkowanie logo

        borderLayoutContainer = new BorderLayoutContainer();
        borderLayoutContainer.setBorders(true);
        borderLayoutContainer.setCenterWidget(center.asWidget(), centerData);

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
        super.removeFromSlot(slot, content);
        if (slot == ContentPagePresenter.TYPE_CONTENT) {
            borderLayoutContainer.setCenterWidget(center, centerData);
        }
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ContentPagePresenter.TYPE_MENU) {
            borderLayoutContainer.setNorthWidget(content.asWidget(), northData);
        } else if (slot == ContentPagePresenter.TYPE_CONTENT) {
            if (content == null) {
                borderLayoutContainer.setCenterWidget(center, centerData);
            } else
                borderLayoutContainer.setCenterWidget(content.asWidget(), centerData);
        }
        borderLayoutContainer.forceLayout();
    }
}
