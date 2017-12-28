package pl.edu.us.client.kartoteki.student.oceny;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.TypOceny;
import pl.edu.us.shared.model.old.Ocena;
import pl.edu.us.shared.model.old.Student;

public class OcenyStudentaModel {
	private ListStore<Student> students;
	private ListStore<Ocena> oceny;
	private ListStore<TypOceny> typOcenyStore;
	private Student selected;

	interface TypProperties extends PropertyAccess<TypOceny> {
		ModelKeyProvider<TypOceny> kod();

//		LabelProvider<TypOceny> nazwa();

//		ValueProvider<TypOceny, BigDecimal> ocena();
	}

	interface OcenaProperties extends PropertyAccess<Ocena> {
		ModelKeyProvider<Ocena> id();

		LabelProvider<Ocena> typ();

	}
//	StudentProperties studentProp = GWT.create(StudentProperties.class);
	TypProperties typOcenyProps = GWT.create(TypProperties.class);
	OcenaProperties ocenaProps = GWT.create(OcenaProperties.class);
	@Inject
	public OcenyStudentaModel() {
//		students = new ListStore<Student>(studentProp.key());
		oceny = new ListStore<Ocena>(ocenaProps.id());
//		typOcenyStore = new ListStore<TypOceny>(typOcenyProps.kod());
	}

	public void wyczysc() {
		students.clear();
	}

	public ListStore<Student> getStudents() {
		return students;
	}

//	public StudentProperties getStudentProp() {
//		return studentProp;
//	}

	public void setSelected(Student selected) {
		this.selected = selected;
		if (selected != null) {
//			oceny.addAll(selected.getOceny());
		}
	}

	public Student getSelected() {
		return selected;
	}

	public ListStore<Ocena> getOceny() {
		return oceny;
	}

	public ListStore<TypOceny> getTypOcenyStore() {
		return typOcenyStore;
	}
}
