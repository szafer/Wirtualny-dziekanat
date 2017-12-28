package pl.edu.us.server.services.kierunki;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.dao.KierunekDAO;
import pl.edu.us.server.dao.SemestrDAO;
import pl.edu.us.shared.model.old.Kierunek;
import pl.edu.us.shared.model.old.OkresStudiow;
import pl.edu.us.shared.model.old.Semestr;
import pl.edu.us.shared.services.kierunki.KierunekService;

@Service("kierunekService")
public class KierunekServiceImpl implements KierunekService {
	@Autowired
	private KierunekDAO kierunekDAO;

	@Autowired
	private SemestrDAO semestrDAO;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Kierunek> zapisz(List<Kierunek> doZapisu, List<Kierunek> doUsuniecia) {
		for (Kierunek u : doUsuniecia) {
			if (u.getId() != null) {
				Kierunek s = kierunekDAO.findById(u.getId());
				if (s != null)
					kierunekDAO.remove(s);
			}
		}
		for (Kierunek u : doZapisu) {
			List<Semestr> semestry = null;
			if (u.getSemestry() == null) {
				semestry = new ArrayList<Semestr>();
				Integer licznik = (Integer) semestrDAO.findMaxId();
				for (int i = 0; i < u.getIloscSemestrow(); i++) {
					licznik++;
					Semestr sem = new Semestr();
					sem.setKierunek(u);
					sem.setNumer(i + 1);
					sem.setId(licznik);
					semestry.add(sem);
				}
				u.setSemestry(semestry);
			} else {
				Integer licznik = (Integer) semestrDAO.findMaxId();
				List<Semestr> noweSemestry = new ArrayList<Semestr>();
				semestry = u.getSemestry();
				int size = semestry.size();
				if (size != u.getIloscSemestrow()) {
					for (int i = 0; i < u.getIloscSemestrow(); i++) {
						if (semestry.isEmpty() || semestry.get(i) == null) {
							licznik++;
							Semestr sem = new Semestr();
							sem.setKierunek(u);
							sem.setNumer(i + 1);
							sem.setId(licznik);
							noweSemestry.add(sem);
						} else {
							noweSemestry.add(semestry.get(i));
						}
					}
					u.setSemestry(noweSemestry);
				}
			}
			u.setOkresy(new ArrayList<OkresStudiow>());
			Kierunek s = kierunekDAO.findById(u.getId());
			if (s == null) {
				kierunekDAO.persist(u);
			} else
				kierunekDAO.merge(u);
		}
		return getKierunki();
	}

	@Override
	public List<Kierunek> getKierunki() {
		// TODO Auto-generated method stub
		return kierunekDAO.findAll();
	}

}
