package pl.edu.us.client.uzytkownik.kartoteka;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

import pl.edu.us.client.main.BazowyPanel;

public class KartotekaUzytkownikowMainPanel extends BazowyPanel {

	private final KartotekaUzytkownikowPanel panel;
	private final KartotekaUzytkownikowModel model;
//	private final ListaOsobWidget<Student, StudentProperties> lista;
	private BorderLayoutData westData;
	@Inject
	public KartotekaUzytkownikowMainPanel(KartotekaUzytkownikowModel model) {
		this.model = model;
		panel = new KartotekaUzytkownikowPanel(model);
//		lista = new ListaOsobWidget<Student, StudentProperties>(model.getStudents(), model.getStudentProp());
		westData = new BorderLayoutData(270);
		westData.setCollapsible(true);
		getBorderLayoutContainer().setCenterWidget(panel);
//		getBorderLayoutContainer().setWestWidget(lista, westData);
		nowy.setVisible(false);
		usun.setVisible(false);
		zatwierdz.setVisible(false);
	}

	public KartotekaUzytkownikowPanel getPanel() {
		return panel;
	}

//	public ListaOsobWidget<Student, StudentProperties> getLista() {
//		return lista;
//	}
}
