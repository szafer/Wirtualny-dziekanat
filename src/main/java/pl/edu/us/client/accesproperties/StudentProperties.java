package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.data.shared.ModelKeyProvider;

import pl.edu.us.shared.model.Student;

public interface StudentProperties extends PersonProperties<Student> {

	@Path("id")
	ModelKeyProvider<Student> key();
}
