package pl.edu.us.client.main;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public abstract class BaseView<C extends UiHandlers> extends ViewWithUiHandlers<C> implements View {

	public void setInSlot(Object slot, com.google.gwt.user.client.ui.IsWidget content) {
	};

	@Override
	public void setUiHandlers(C uiHandlers) {
		// TODO Auto-generated method stub
		super.setUiHandlers(uiHandlers);
		bindCustomUiHandlers();
	}

	protected void bindCustomUiHandlers() {
	}
}