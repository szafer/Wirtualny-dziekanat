package pl.edu.us.server.services.kierunki;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.shared.model.old.Kierunek;
import pl.edu.us.shared.services.kierunki.KierunekService;

@Service("kierunekService")
public class KierunekServiceImpl implements KierunekService {


	

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Kierunek> zapisz(List<Kierunek> doZapisu, List<Kierunek> doUsuniecia) {
	
		return getKierunki();
	}

	@Override
	public List<Kierunek> getKierunki() {
		return null;
	}

}
