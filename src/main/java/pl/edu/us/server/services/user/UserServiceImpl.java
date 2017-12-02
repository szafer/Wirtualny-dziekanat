package pl.edu.us.server.services.user;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.dao.UserDAO;
import pl.edu.us.server.services.Main;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @PostConstruct
    public void init() throws Exception {
    }

    @PreDestroy
    public void destroy() {
    }

    // @Transactional(readOnly = true)
    public User getUser(String name, String password) {
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA)
                .setParameter("login", name)
                .setParameter("password", password)
                .getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<User> zapisz(List<User> doZapisu, List<User> doUsuniecia) {
        int licznik = 0;
        for (User u : doZapisu) {
            if (u.getId() == null) {
                Integer id = (Integer) userDAO.getEntityManager().createNamedQuery(User.NEXT_ID).getSingleResult();
                u.setId(id + licznik);
                licznik++;
                userDAO.persist(u);
            } else
                userDAO.merge(u);
        }
        for (User u : doUsuniecia) {
            userDAO.remove(u);
        }
        doZapisu.clear();
        return getUsers();
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User logout() {
        Main m = new Main();
        try {
            m.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPassByEmail(String email) {
        final String subject = "Przypomnienie hasła";
        try {
            String u = (String) userDAO.getEntityManager()
                .createNamedQuery(User.POBIERZ_HASLO_PO_EMAIL)
                .setParameter("email", email)
                .getSingleResult();
            new Mail().send(email, subject, "Hasło: " + u);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User zarejestruj(User user) throws Exception {
        User u = null;
        try {
            u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", user.getLogin())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (u != null) {
//            return null;
//            try {
                throw new Exception("Taki login występuje już w systemie");
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return null;
//            }
        }
//        if (!userDAO.getEntityManager().getTransaction().isActive())
//            userDAO.getEntityManager().getTransaction().begin();
        userDAO.persist(user);
//        userDAO.flush(user);

//        userDAO.getEntityManager().flush();
        return user;

    }
}
