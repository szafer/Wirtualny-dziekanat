package pl.edu.us.client.passremind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

public class PassRemindView extends Composite implements View,
    PassRemindPresenter.MyView, HasUiHandlers<PassRemindUiHandlers> {

    private static PassRemindViewUiBinder uiBinder = GWT
        .create(PassRemindViewUiBinder.class);

    @UiField
    TextBox email;

    @UiField
    Button buttonPowrot;
    @UiField
    Button buttonWyslij;

    private PassRemindUiHandlers handlers;

    interface PassRemindViewUiBinder extends UiBinder<Widget, PassRemindView> {
    }

    @Inject
    public PassRemindView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setUiHandlers(PassRemindUiHandlers uiHandlers) {
        this.handlers = uiHandlers;
    }

    @UiHandler("buttonPowrot")
    void onButtonClick(ClickEvent event) {
        handlers.onPowrotClicked();
    }

    @UiHandler("buttonWyslij")
    void onButton_1Click(ClickEvent event) {
        handlers.onWyslijClicked();
    }

    @Override
    public void addToSlot(Object slot, IsWidget content) {
    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {

    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {

    }

    @Override
    public TextBox getLogin() {
        return email;
    }

}
