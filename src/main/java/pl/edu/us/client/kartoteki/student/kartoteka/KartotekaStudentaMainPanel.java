package pl.edu.us.client.kartoteki.student.kartoteka;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.client.ListaOsobWidget;
import pl.edu.us.client.accesproperties.StudentProperties;
import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.model.Student;

public class KartotekaStudentaMainPanel extends BazowyPanel {

	private final KartotekaStudentaPanel panel;
	private final KartotekaStudentaModel model;
	private final ListaOsobWidget<Student, StudentProperties> lista;
	private BorderLayoutData westData;
	@Inject
	public KartotekaStudentaMainPanel(KartotekaStudentaModel model) {
		this.model = model;
		panel = new KartotekaStudentaPanel(model);
		lista = new ListaOsobWidget<Student, StudentProperties>(model.getStudents(), model.getStudentProp());
		westData = new BorderLayoutData(270);
		westData.setCollapsible(true);
		getBorderLayoutContainer().setCenterWidget(panel);
		getBorderLayoutContainer().setWestWidget(lista, westData);
		nowy.setVisible(false);
		usun.setVisible(false);
		zatwierdz.setVisible(false);
	}

	public KartotekaStudentaPanel getPanel() {
		return panel;
	}

	public ListaOsobWidget<Student, StudentProperties> getLista() {
		return lista;
	}
}
