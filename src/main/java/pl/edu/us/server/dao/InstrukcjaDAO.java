package pl.edu.us.server.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.edu.us.shared.model.Instrukcja;

@Repository("instrukcjaDAO")
public class InstrukcjaDAO extends BaseDAO<Long, Instrukcja> {

   /* @PersistenceUnit(unitName="MyPersistenceUnit")  */
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
}
