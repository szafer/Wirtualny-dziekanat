package pl.edu.us.server.services.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA).setParameter("login", name)
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

    @SuppressWarnings("unchecked")
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
