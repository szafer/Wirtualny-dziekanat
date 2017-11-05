package pl.edu.us.server.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;
import pl.edu.us.shared.model.Adres;
import pl.edu.us.shared.model.Pracownik;
import pl.edu.us.shared.services.PracownikService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("pracownikService")
public class PracownikServiceImpl implements PracownikService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	public Pracownik znajdz(long employeeId) {
		return new Hibernate3DtoCopier().hibernate2dto(
				em.find(Pracownik.class, employeeId));
	}
		
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void zapisz(Pracownik pracownik) {
		pracownik.setAdresy(dajAdresy());
		em.merge(pracownik);
	}

	private List<Adres> dajAdresy() {
		Adres ad = new Adres();
//		ad.setNazwa("Miko�owska 123");
		List<Adres> l = new ArrayList<Adres>();
		l.add(ad);
		ad = new Adres();
//		ad.setNazwa("Gliwicka 344");
		l.add(ad);
		return l;
	}
}
