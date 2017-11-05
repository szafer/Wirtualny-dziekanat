package pl.edu.us.server.services.kierunki;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import pl.edu.us.shared.model.Semestr;
import pl.edu.us.shared.services.kierunki.SemestrService;

@Service("semestrService")
public class SemestrServiceImpl implements SemestrService {

	// @Autowired
	// private SemestrDAO semestrDAO;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public List<Semestr> zapisz(List<Semestr> doZapisu, List<Semestr> doUsuniecia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Semestr> getKierunki() {
		// TODO Auto-generated method stub
		return null;
	}
}
