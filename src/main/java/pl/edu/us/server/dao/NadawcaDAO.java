package pl.edu.us.server.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.edu.us.shared.model.wiadomosci.Nadawca;

@Repository("nadawcaDAO")
public class NadawcaDAO extends BaseDAO<Integer, Nadawca> {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
}
