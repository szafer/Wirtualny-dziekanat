package pl.edu.us.server.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.edu.us.shared.model.przedmioty.UPrzedmiot;

@Repository("uPrzedmiotDAO")
public class UPrzedmiotDAO extends BaseDAO<Integer, UPrzedmiot> {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
}
