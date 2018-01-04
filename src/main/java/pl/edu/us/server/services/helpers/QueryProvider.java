package pl.edu.us.server.services.helpers;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public interface QueryProvider {
 
    Query provide();

    void setEm(EntityManager em);

    EntityManager getEm();
}
