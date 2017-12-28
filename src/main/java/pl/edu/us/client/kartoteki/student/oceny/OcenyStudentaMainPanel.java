package pl.edu.us.client.kartoteki.student.oceny;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.client.kartoteki.student.oceny.ui.OcenyStudentaPanel;
import pl.edu.us.client.main.BazowyPanel;

public class OcenyStudentaMainPanel extends BazowyPanel {

	private final OcenyStudentaPanel panel;
	private final OcenyStudentaModel model;
//	private final ListaOsobWidget<Student, StudentProperties> lista;
	private BorderLayoutData westData;

	@Inject
	public OcenyStudentaMainPanel(OcenyStudentaModel model) {
		this.model = model;
		panel = new OcenyStudentaPanel(model);
//		lista = new ListaOsobWidget<Student, StudentProperties>(model.getStudents(), model.getStudentProp());
		westData = new BorderLayoutData(270);
		westData.setCollapsible(true);
		getBorderLayoutContainer().setCenterWidget(panel);
//		getBorderLayoutContainer().setWestWidget(lista, westData);
	}

	public OcenyStudentaPanel getPanel() {
		return panel;
	}

//	public ListaOsobWidget<Student, StudentProperties> getLista() {
//		return lista;
//	}
}
