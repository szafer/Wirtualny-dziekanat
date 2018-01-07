package pl.edu.us.server.services.user;

import java.util.Map;

import pl.edu.us.server.services.helpers.JPAQueryProvider;

public class UsersJPA extends JPAQueryProvider {

    private final boolean count;

    public UsersJPA( boolean count) {
        super();
        this.count = count;
    }

    @Override
    protected void generateQuery(StringBuilder sb, Map<Object, Object> whereParams) {
        sb.append("SELECT " + select() + " "
            + "      FROM User a "
            + "     WHERE 1=1  ");

    }

    private String select() {
        if (count) {
            return " COUNT(*) ";
        } else {
            return " a ";
        }
    }
}
