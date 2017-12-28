package pl.edu.us.server.services.przychodykoszty;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.us.server.dao.ViPrzychodDAO;
import pl.edu.us.shared.model.old.ViPrzychod;
import pl.edu.us.shared.services.przychodykoszty.PrzychodyKosztyService;

@Service("przychodyKosztyService")
public class PrzychodyKosztyServiceImpl implements PrzychodyKosztyService {

	@Autowired
	private ViPrzychodDAO przychodDAO;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}
	@Override
	public List<ViPrzychod> getPrzychody() {
		List<ViPrzychod> przychody = przychodDAO.findAll();
		int i = 0;
		for (ViPrzychod bean : przychody) {
			bean.setId(i++);
		}
		return przychody;
	}

}
