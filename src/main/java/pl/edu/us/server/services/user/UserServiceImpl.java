package pl.edu.us.server.services.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

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
    public List<UserDTO> zapisz(List<UserDTO> doZapisu) {
        ModelMapper mapper = new ModelMapper();
        for (UserDTO dto : doZapisu) {
            User u = mapper.map(dto, User.class);
            if (u.getId() == null) {
                userDAO.persist(u);
            } else if (u.getId() < 1) {
                u.setId(null);
                userDAO.persist(u);
            } else
                userDAO.merge(u);
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
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDTO zarejestruj(UserDTO user) throws Exception {
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
        u = new ModelMapper().map(user, User.class);
        userDAO.persist(u);
        user.setId(u.getId());
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
    public UserDTO pobierzDaneUzytkownika(String name) {
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA_PO_LOGINIE)
                .setParameter("login", name)
                .getSingleResult();
            return new ModelMapper().map(u, UserDTO.class);
        } catch (Exception e) {
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
        List<User> users = userDAO.findAll().subList(start, limit);
        List<UserDTO> wynik = new ArrayList<UserDTO>(users.size());
        for (User u : users) {
//            wynik.add(mapper.map(u, UserDTO.class));
            wynik.add(new UserDTO(u));
        }
        
//
//        if (config.getSortInfo().getSortField() != null) {
//            final String sortField = config.getSortInfo().getSortField();
//            if (sortField != null) {
//                Collections.sort(agents, config.getSortInfo().getSortDir().comparator(new Comparator() {
//                    public int compare(Object ag1, Object ag2) {
//                        User agent1 = (User) ag1;
//                        User agent2 = (User) ag2;
//                        if (sortField.equals("imie")) {
//                            return agent1.getImie().compareTo(agent2.getImie());
//                        } else if (sortField.equals("nazwisko")) {
//                            return agent1.getNazwisko().compareTo(agent2.getNazwisko());
//                        }
//                        return 0;
//                    }
//                }));
//            }
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