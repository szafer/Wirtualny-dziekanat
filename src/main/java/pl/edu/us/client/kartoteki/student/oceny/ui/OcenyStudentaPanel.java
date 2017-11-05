package pl.edu.us.client.kartoteki.student.oceny.ui;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.FramedPanel;

import pl.edu.us.client.kartoteki.student.oceny.OcenyStudentaModel;

public class OcenyStudentaPanel extends FramedPanel {

	private final OcenyStudentaModel model;

	@Inject
	public OcenyStudentaPanel(OcenyStudentaModel model) {
		this.model = model;
	}
}
