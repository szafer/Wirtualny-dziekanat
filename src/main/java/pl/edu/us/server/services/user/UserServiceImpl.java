package pl.edu.us.server.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;

import org.eclipse.jetty.util.log.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

import pl.edu.us.server.dao.UserDAO;
import pl.edu.us.server.services.Main;
import pl.edu.us.shared.commons.AppStrings;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

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
    public List<UserDTO> zapisz(List<UserDTO> doZapisu) throws Exception {
        ModelMapper mapper = new ModelMapper();
        for (UserDTO dto : doZapisu) {
            User wystepuje = null;
            if (dto.getEmail() == null) {
                Log.info("Błąd zapisu - brak adresu email");
                throw new Exception("Błąd zapisu - brak adresu email");
            }
            try {
                wystepuje = (User) userDAO.getEntityManager().createNamedQuery(User.CZY_EMAIL_WYSTEPUJE)
                    .setParameter("email", dto.getEmail())
                    .getSingleResult();
            } catch (Exception e) {
            }
            if (wystepuje != null && wystepuje.getId() != dto.getId()) {//zarejestrowano email dla innego usera
                LOG.info("Błąd zapisu - email:" + dto.getEmail());
                throw new Exception("Taki email występuje już w systemie");
            }
            User u = mapper.map(dto, User.class);
            userDAO.merge(u);
            if (dto.getPowiadomic()) {
                try {
                    new Mail().send(dto.getEmail(), AppStrings.odblokowanie_konta_subj, AppStrings.odblokowanie_konta);
                } catch (Exception e) {
                    LOG.info("Nie udało się powiadomić użytkownika: " + dto.toString());
                }
            }
        }
        return new ArrayList<UserDTO>();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UserDTO> getUsers() {
        ModelMapper mapper = new ModelMapper();
        List<User> users = userDAO.findAll();
        List<UserDTO> wynik = new ArrayList<UserDTO>(users.size());
        for (User u : users) {
            wynik.add(mapper.map(u, UserDTO.class));
        }
        return wynik;
    }

    /**
     * nieuzywane
     */
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
            LOG.info("Nie udało się wysłać hasła na email: " + email);
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDTO zarejestruj(UserDTO user) throws Exception {
        User u = null;
        try {
            u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", user.getLogin())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (u != null) {
            LOG.info("Błąd rejestrcji - login:" + user.getLogin());
            throw new Exception("Taki login występuje już w systemie");
        }
        User wystepuje = null;
        if (user.getEmail() == null) {
            Log.info("Błąd zapisu - brak adresu email");
            throw new Exception("Błąd zapisu - brak adresu email");
        }
        try {
            wystepuje = (User) userDAO.getEntityManager().createNamedQuery(User.CZY_EMAIL_WYSTEPUJE)
                .setParameter("email", user.getEmail())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (wystepuje != null) {
            LOG.info("Błąd zapisu - email:" + user.getEmail());
            throw new Exception("Taki email występuje już w systemie");
        }
        u = new ModelMapper().map(user, User.class);
        userDAO.persist(u);
        user.setId(u.getId());
        return user;

    }

    private void walidujAdresEmail(UserDTO user) throws Exception {
        Boolean wystepuje = false;
        if (user.getEmail() == null) {
            Log.info("Błąd zapisu - brak adresu email");
            throw new Exception("Błąd zapisu - brak adresu email");
        }
        try {
            wystepuje = (Boolean) userDAO.getEntityManager().createNamedQuery(User.CZY_EMAIL_WYSTEPUJE)
                .setParameter("email", user.getEmail())
                .getSingleResult();
        } catch (Exception e) {
        }
        if (wystepuje != null && wystepuje) {
            LOG.info("Błąd zapisu - email:" + user.getEmail());
            throw new Exception("Taki email występuje już w systemie");
        }
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
    public UserDTO pobierzDaneUzytkownika(String name) {
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", name)
                .getSingleResult();
            return new ModelMapper().map(u, UserDTO.class);
        } catch (Exception e) {
            LOG.info("Błąd logowania - błędny login:" + name);
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Override
    public PagingLoadResult<UserDTO> getUsers(PagingLoadConfig config) {

        int start = config.getOffset();
        int limit = config.getLimit();
        if (limit > 0) {
            limit = Math.min(start + limit, limit);
        }
//        List<User> agents = userDAO.findAll();//Entities(limit, start);
        ModelMapper mapper = new ModelMapper();
        List<User> usersAll = userDAO.findAll();
        List<User> users = userDAO.findAll().subList(start, (start + limit > usersAll.size() ? usersAll.size() : start + limit));
        List<UserDTO> wynik = new ArrayList<UserDTO>(users.size());
        for (User u : users) {
//            wynik.add(mapper.map(u, UserDTO.class));
            wynik.add(new UserDTO(u));
        }
//        userDAO.getEntityManager().cre
//        if (config.getSortInfo().getSortField() != null) {
//            final String sortField = config.getSortInfo().getSortField();
//            if (sortField != null) {
//                Collections.sort(d.getData(), config.getSortInfo().getSortDir().comparator(new Comparator<ModelData>() {
//
//                    public int compare(User o1, ModelData o2) {
//                        Object v1 = (Object) o1.get(sortField);
//                        Object v2 = (Object) o2.get(sortField);
//
//                        if (getComparator() != null) {
//                            return getComparator().compare(v1, v2);
//                        } else {
//                            return PlComparator.INSTANCE.compare(v1, v2);
//                        }
//
//                    }
//                }));
//            }
//
//        }
        return new PagingLoadResultBean<UserDTO>(wynik, usersAll.size(), config.getOffset());
//        config.
//        return null;
    }

}
//if (config.getSortInfo().size() > 0) { 
//    SortInfo sort = config.getSortInfo().get(0); 
//    if (sort.getSortField() != null) { 
//      final String sortField = sort.getSortField(); 
//      if (sortField != null) { 
//        Collections.sort(posts, sort.getSortDir().comparator(new Comparator<Post>() { 
//          public int compare(Post p1, Post p2) { 
//            if (sortField.equals("forum")) { 
//              return p1.getForum().compareTo(p2.getForum()); 
//            } else if (sortField.equals("username")) { 
//              return p1.getUsername().compareTo(p2.getUsername()); 
//            } else if (sortField.equals("subject")) { 
//              return p1.getSubject().compareTo(p2.getSubject()); 
//            } else if (sortField.equals("date")) { 
//              return p1.getDate().compareTo(p2.getDate()); 
//            } 
//            return 0; 
//          } 
//        })); 
//      } 
//    } 
//  } 
