package pl.edu.us.client.symulacja.przychodykosztyroczne;

import com.google.gwt.user.client.ui.Widget;

import pl.edu.us.client.main.BaseView;
import pl.edu.us.client.symulacja.przychodykosztyroczne.ui.PKRocznePanel;

public class PKRoczneView extends BaseView<PKRoczneUiHandlers> implements PKRocznePresenter.MyView {

	private final PKRocznePanel panel;
	private final PKRoczneModel model;

	public PKRoczneView() {
		model = new PKRoczneModel();
		panel = new PKRocznePanel(model);
	}

	@Override
	public PKRoczneModel getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
}