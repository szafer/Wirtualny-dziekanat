package pl.edu.us.server.services.helpers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class BaseQueryProvider implements QueryProvider {
    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(BaseQueryProvider.class.getName());

    protected EntityManager em;
    protected String zapytanie;
    protected Map<Object, Object> mapaWhereParams;

    public BaseQueryProvider() {
    }

    public BaseQueryProvider(final EntityManager em) {
        this.em = em;
    }

    public Query provide() {
        final Map<Object, Object> whereParams = new HashMap<Object, Object>();
        final StringBuilder sb = new StringBuilder();
        buildQueryString(sb, whereParams);
        final Query query = buildQuery(sb.toString(), whereParams);
        return query;
    }

    protected abstract void generateQuery(StringBuilder sb, Map<Object, Object> whereParams);

    protected void buildQueryString(final StringBuilder sb, final Map<Object, Object> whereParams) {
        if (zapytanie == null || zapytanie.trim().length() == 0) {
            this.mapaWhereParams = new HashMap<Object, Object>();
            generateQuery(sb, mapaWhereParams);
            zapytanie = sb.toString();
        } else {
            sb.append(zapytanie);
        }
        whereParams.putAll(mapaWhereParams);
    }

    protected abstract Query buildQuery(String zapytanie, Map<Object, Object> whereParams);

    @Override
    public EntityManager getEm() {
        return em;
    }

    @Override
    public void setEm(final EntityManager em) {
        this.em = em;
    }

    @Override
    public String toString() {
        final Map<Object, Object> whereParams = new HashMap<Object, Object>();
        final StringBuilder sb = new StringBuilder();
        buildQueryString(sb, whereParams);
        sb.append(toStringParametry(whereParams));
        return sb.toString();
    }

    private String toStringParametry(final Map<Object, Object> whereParams) {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n PARAMETRY \n");

        for (final Map.Entry<Object, Object> entry : whereParams.entrySet()) {
            String wartosc = null;
            if (entry.getValue() == null) {
                wartosc = "";
            } else if (entry.getValue() instanceof Calendar) {
                wartosc = ((Calendar) entry.getValue()).getTime().toString();
            } else {
                wartosc = entry.getValue().toString();
            }
            sb.append(entry.getKey())
                .append("=")
                .append(wartosc)
                .append("\n");
        }

        return sb.toString();
    }

}
