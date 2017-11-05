package pl.edu.us.shared.services.student;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.model.Student;

public interface StudentServiceAsync {

	void getStudent(String name, String password, AsyncCallback<Student> callback);

	void zapisz(List<Student> doZapisu, List<Student> doUsuniecia, AsyncCallback<List<Student>> callback);

	void getStudents(AsyncCallback<List<Student>> callback);
}
