package pl.edu.us.server.services.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.dao.UserDAO;
import pl.edu.us.server.services.Main;
import pl.edu.us.shared.dto.UserDTO;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDTO getUser(String name, String password) {
//         List<Student> hibernate2dto = (List<Student>) new
//         return hibernate2dto;
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA)
                .setParameter("login", name)
                .setParameter("password", password)
                .getSingleResult();
            // user here is a prepopulated User instance
            UserDTO userDTO = new ModelMapper().map(u, UserDTO.class);
            return userDTO;
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
        Boolean wystepuje = false;
        try {
            u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", user.getLogin())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (u != null) {
            throw new Exception("Taki login występuje już w systemie");
        }
        try {
            wystepuje = (Boolean) userDAO.getEntityManager().createNamedQuery(User.CZY_EMAIL_WYSTEPUJE)
                .setParameter("email", user.getEmail())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (wystepuje != null && wystepuje) {
            throw new Exception("Taki email występuje już w systemie");
        }
        userDAO.persist(user);

        return user;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDTO updateUser(UserDTO u) {
        User user = new ModelMapper().map(u, User.class);

        userDAO.merge(user);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User pobierzDaneUzytkownika(String name) {
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", name)
                .getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            return null;
        }
    }
}
