package pl.edu.us.server.services.student;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.dao.OkresStudiowDAO;
import pl.edu.us.server.dao.StudentDAO;
import pl.edu.us.shared.model.old.OkresStudiow;
import pl.edu.us.shared.model.old.Student;
import pl.edu.us.shared.services.student.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private OkresStudiowDAO okresStudiowDAO;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public Student getStudent(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Student> zapisz(List<Student> doZapisu, List<Student> doUsuniecia) {
//		for (Student u : doUsuniecia) {
//			if (u.getId() != null) {
//				Student s = studentDAO.findById(u.getId());
//				if (s != null)
//				studentDAO.remove(s);
//			}
//		}
//		for (Student u : doZapisu) {
//			Integer licznik = (Integer) okresStudiowDAO.findMaxId();
//			for (OkresStudiow ok : u.getOkresy()) {
//				if (ok.getId() == null) {
//					licznik++;
//					ok.setId(licznik);
//				}
//			}
//			Student s = studentDAO.findById(u.getId());
//			if (s == null) {
//				studentDAO.persist(u);
//			} else
//				studentDAO.merge(u);
//		}
		return getStudents();
	}

	@Override
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// Exception.class)
	public List<Student> getStudents() {
		// List<Student> hibernate2dto = (List<Student>) new
		// Hibernate3DtoCopier().hibernate2dto(studentDAO.findAll());
		// return hibernate2dto;

		return studentDAO.findAll();
	}

}
