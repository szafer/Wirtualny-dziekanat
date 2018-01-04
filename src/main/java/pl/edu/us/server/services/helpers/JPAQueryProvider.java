package pl.edu.us.server.services.helpers;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class JPAQueryProvider extends BaseQueryProvider {
    public JPAQueryProvider() {
        super();
    }

    public JPAQueryProvider(final EntityManager em) {
        super(em);
    }

    @Override
    protected final Query buildQuery(final String zapytanie,
        final Map<Object, Object> whereParams) {
        final Query query = em.createQuery(zapytanie);
        for (final Entry<Object, Object> entry : whereParams.entrySet()) {
            query.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return query;
    }
}
