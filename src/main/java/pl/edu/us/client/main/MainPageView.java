package pl.edu.us.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

public class MainPageView extends Composite implements View,
		MainPagePresenter.MyView, HasUiHandlers<MainPageUiHandlers> {

	private static MainPageViewUiBinder uiBinder = GWT
			.create(MainPageViewUiBinder.class);

	@UiField
	TextBox login;
	@UiField
	PasswordTextBox pass;
	@UiField Button button;
	@UiField Button button_1;

	private MainPageUiHandlers handlers;
	
	interface MainPageViewUiBinder extends UiBinder<Widget, MainPageView> {
	}

	@Inject
	public MainPageView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	
	
	@Override
	public void setUiHandlers(MainPageUiHandlers uiHandlers) {
		this.handlers = uiHandlers;
	}
	
	
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		handlers.onPowrotClicked();
	}

	@UiHandler("button_1")
	void onButton_1Click(ClickEvent event) {
		handlers.onLogujClicked();
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
		return login;
	}

	@Override
	public PasswordTextBox getPass() {
		return pass;
	}
}