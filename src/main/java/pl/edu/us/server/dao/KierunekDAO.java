package pl.edu.us.server.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.edu.us.shared.model.Kierunek;

@Repository("kierunekDAO")
public class KierunekDAO extends BaseDAO<Integer, Kierunek> {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
}
