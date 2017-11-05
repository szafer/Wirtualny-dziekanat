package pl.edu.us.client.symulacja.przychodykosztyroczne.ui;

import com.sencha.gxt.widget.core.client.ContentPanel;

import pl.edu.us.client.symulacja.przychodykosztyroczne.PKRoczneModel;

public class PKRocznePanel extends ContentPanel {

	private final PKRoczneModel model;

	public PKRocznePanel(PKRoczneModel model) {
		this.model = model;
		setHeadingHtml("Przychody i koszty roczne");
	}

}