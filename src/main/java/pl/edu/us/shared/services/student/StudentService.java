package pl.edu.us.shared.services.student;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.edu.us.shared.model.old.Student;

@RemoteServiceRelativePath("usosweb/studentService")
public interface StudentService extends RemoteService {

	Student getStudent(String name, String password);

	List<Student> zapisz(List<Student> doZapisu, List<Student> doUsuniecia);

	List<Student> getStudents();
}
