package pl.edu.us.server.services.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.jetty.util.log.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

import pl.edu.us.server.ServerUtils;
import pl.edu.us.server.dao.UserDAO;
import pl.edu.us.server.services.Main;
import pl.edu.us.shared.commons.AppStrings;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.enums.Semestr;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.services.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

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
    public UserDTO getUser(String name, String password) throws Exception {
        UserDTO userDTO = null;
        try {
            User u = (User) userDAO.getEntityManager().createNamedQuery(User.DAJ_USERA)
                .setParameter("login", name)
                .setParameter("password", password)
                .getSingleResult();
            userDTO = new ModelMapper().map(u, UserDTO.class);
        } catch (NoResultException e) {
            LOG.info("Błąd logowania - błędny login lub hasło:" + name);
            throw new Exception("Błędny login lub hasło.");
        }
        if (userDTO != null && !userDTO.getAktywny()) {
            LOG.info("Błąd logowania - użytkownik zablokowany:" + userDTO.getLogin());
            throw new Exception("Użytkownik jest zablokowany. \nProszę skontaktować się z administratorem systemu.");
        }
        return userDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UserDTO> zapisz(List<UserDTO> doZapisu) throws Exception {
        ModelMapper mapper = new ModelMapper();
        for (UserDTO dto : doZapisu) {
            User wystepuje = null;
            if (dto.getEmail() == null) {
                LOG.info("Błąd zapisu - brak adresu email");
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
//        ModelMapper mapper = new ModelMapper();
        List<User> users = userDAO.findAll();
        List<UserDTO> wynik = new ArrayList<UserDTO>(users.size());
        for (User u : users) {
            wynik.add(new UserDTO(u));
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
            UserDTO user = new ModelMapper().map(u, UserDTO.class);
            if (user.getPrzedmiotyUzytkownika() != null && !user.getPrzedmiotyUzytkownika().isEmpty()) {
                for (UPrzedmiotDTO up : user.getPrzedmiotyUzytkownika()) {
                    up.setSemestr(up.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
                }
//                List<UPrzedmiotDTO> lista = user.getPrzedmiotyUzytkownika();
//                Collections.sort(lista, new Comparator<UPrzedmiotDTO>() {
//
//                    @Override
//                    public int compare(UPrzedmiotDTO o1, UPrzedmiotDTO o2) {
//                        return o1.getDataSemestru().before(o2.getDataSemestru()) ? 1 : 0;
//                    }
//                });
//                user.setPrzedmiotyUzytkownika(lista);
            }
            if (user.getWnioskiUzytkownika() != null && !user.getWnioskiUzytkownika().isEmpty()) {
                for (UWniosekDTO uw : user.getWnioskiUzytkownika()) {
                    if (uw.getZlozonyWniosek() != null) {
                        uw.setImage(ServerUtils.getImageData(uw.getZlozonyWniosek()));
                    }
                }
            }
            return user;
        } catch (Exception e) {
            LOG.info("Błąd logowania - błędny login:" + name);
            System.out.println(e.getCause().toString());
            return null;
        }
    }

    @Override
    public PagingLoadResult<UserDTO> getUsers(FilterPagingLoadConfig config) {
        int start = config.getOffset();
        int limit = config.getLimit();
        if (limit > 0) {
            limit = Math.min(start + limit, limit);
        }
        List<User> usersAll = userDAO.findAll();
        List<User> users = userDAO.findAll();
        List<UserDTO> wynik = new ArrayList<UserDTO>(users.size());
        if (config.getSortInfo().size() > 0) {
            SortInfo sort = config.getSortInfo().get(0);
            if (sort.getSortField() != null) {
                final String sortField = sort.getSortField();
                if (sortField != null) {
                    Collections.sort(users, sort.getSortDir().comparator(new Comparator<User>() {
                        public int compare(User p1, User p2) {
                            if (sortField.equals("id")) {
                                return p1.getId().compareTo(p2.getId());
                            } else if (sortField.equals("imie")) {
                                return p1.getImie().compareTo(p2.getImie());
                            } else if (sortField.equals("login")) {
                                return p1.getLogin().compareTo(p2.getLogin());
                            } else if (sortField.equals("nazwisko")) {
                                return p1.getNazwisko().compareTo(p2.getNazwisko());
                            } else if (sortField.equals("dataUrodzenia")) {
                                return p1.getDataUrodzenia().compareTo(p2.getDataUrodzenia());
                            } else if (sortField.equals("email")) {
                                return p1.getEmail().compareTo(p2.getEmail());
                            } else if (sortField.equals("ulica")) {
                                return p1.getUlica().compareTo(p2.getUlica());
                            } else if (sortField.equals("nrDomu")) {
                                return p1.getNrDomu().compareTo(p2.getNrDomu());
                            } else if (sortField.equals("nrMieszkania")) {
                                return p1.getNrMieszkania().compareTo(p2.getNrMieszkania());
                            } else if (sortField.equals("miasto")) {
                                return p1.getMiasto().compareTo(p2.getMiasto());
                            } else if (sortField.equals("kodPocztowy")) {
                                return p1.getKodPocztowy().compareTo(p2.getKodPocztowy());
                            } else if (sortField.equals("plec")) {
                                return p1.getPlec().compareTo(p2.getPlec());
                            } else if (sortField.equals("rola")) {
                                return p1.getRola().compareTo(p2.getRola());
                            } else if (sortField.equals("aktywny")) {
                                return p1.getAktywny().compareTo(p2.getAktywny());
                            } else if (sortField.equals("iloscLogowan")) {
                                return p1.getIloscLogowan().compareTo(p2.getIloscLogowan());
                            }
                            return 0;
                        }
                    }));
                }
            }
        }
        for (User u : users.subList(start, (start + limit > usersAll.size() ? usersAll.size() : start + limit))) {
            wynik.add(new UserDTO(u));
        }
        return new PagingLoadResultBean<UserDTO>(wynik, usersAll.size(), config.getOffset());
    }
}
