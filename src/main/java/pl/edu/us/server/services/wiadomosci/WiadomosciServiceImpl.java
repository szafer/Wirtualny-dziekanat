package pl.edu.us.server.services.wiadomosci;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.dao.NadawcaDAO;
import pl.edu.us.server.dao.OdbiorcaDAO;
import pl.edu.us.server.dao.UserDAO;
import pl.edu.us.server.services.user.Mail;
import pl.edu.us.shared.dto.wiadomosci.NadawcaDTO;
import pl.edu.us.shared.dto.wiadomosci.OdbiorcaDTO;
import pl.edu.us.shared.dto.wiadomosci.UserMessagesDTO;
import pl.edu.us.shared.model.wiadomosci.Nadawca;
import pl.edu.us.shared.model.wiadomosci.Odbiorca;
import pl.edu.us.shared.services.wiadomosci.WiadomosciService;

@Service("wiadomosciService")
public class WiadomosciServiceImpl implements WiadomosciService {

    @Autowired
    private NadawcaDAO nadawcaDAO;

    @Autowired
    private OdbiorcaDAO odbiorcaDAO;
    @Autowired
    private UserDAO userDAO;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserMessagesDTO getWiadomosci(Integer userId) {
        ModelMapper mapper = new ModelMapper();

        UserMessagesDTO wiadomosci = new UserMessagesDTO();
        List<Nadawca> nad = nadawcaDAO.getEntityManager()
            .createNamedQuery(Nadawca.MOJE_WIADOMOSCI)
            .setParameter("userId", userId).getResultList();
        List<Odbiorca> od = nadawcaDAO.getEntityManager()
            .createNamedQuery(Odbiorca.MOJE_WIADOMOSCI)
            .setParameter("userId", userId)
            .setParameter("typ", Boolean.TRUE)
            .getResultList();
        List<Odbiorca> wiad = nadawcaDAO.getEntityManager()
            .createNamedQuery(Odbiorca.NOWE_WIADOMOSCI)
            .setParameter("userId", userId)
            .getResultList();
        List<OdbiorcaDTO> nowe = new ArrayList<OdbiorcaDTO>(wiad.size());
        List<NadawcaDTO> nadane = new ArrayList<NadawcaDTO>(nad.size());
        List<OdbiorcaDTO> odebrane = new ArrayList<OdbiorcaDTO>(od.size());
        for (Nadawca n : nad) {
            NadawcaDTO nDto = mapper.map(n, NadawcaDTO.class);
            nDto.setUserId(n.getUser().getId());
            nDto.setImie(n.getUser().getImie());
            nDto.setNazwisko(n.getUser().getNazwisko());
            nadane.add(nDto);
        }
        for (Odbiorca o : od) {
            OdbiorcaDTO nDto = mapper.map(o, OdbiorcaDTO.class);
            nDto.setUserId(o.getUser().getId());
            nDto.setImie(o.getUser().getImie());
            nDto.setNazwisko(o.getUser().getNazwisko());
            odebrane.add(nDto);
        }
        for (Odbiorca o : wiad) {
            OdbiorcaDTO nDto = mapper.map(o, OdbiorcaDTO.class);
            nDto.setUserId(o.getUser().getId());
            nDto.setImie(o.getUser().getImie());
            nDto.setNazwisko(o.getUser().getNazwisko());
            nowe.add(nDto);

        }
        wiadomosci.setNadane(nadane);
        wiadomosci.setOdebrane(odebrane);
        wiadomosci.setNowe(nowe);
        return wiadomosci;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OdbiorcaDTO> getNoweWiadomosci(Integer userId) {
        ModelMapper mapper = new ModelMapper();

        List<Odbiorca> wiad = nadawcaDAO.getEntityManager()
            .createNamedQuery(Odbiorca.NOWE_WIADOMOSCI)
            .setParameter("userId", userId)
            .setMaxResults(1)
            .getResultList();
        List<OdbiorcaDTO> nowe = new ArrayList<OdbiorcaDTO>(wiad.size());
        for (Odbiorca o : wiad) {
            OdbiorcaDTO nDto = mapper.map(o, OdbiorcaDTO.class);
            nDto.setUserId(o.getUser().getId());
            nDto.setImie(o.getUser().getImie());
            nDto.setNazwisko(o.getUser().getNazwisko());
            nowe.add(nDto);

        }
        return nowe;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMessage(OdbiorcaDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Odbiorca odb = mapper.map(dto, Odbiorca.class);
        odbiorcaDAO.merge(odb);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void wyslij(NadawcaDTO dto) throws Exception {
        Nadawca n = new Nadawca(dto);
        n.setUser(userDAO.findById(dto.getUserId()));
        n.setData(new Date());
        List<Odbiorca> odbiorcy = new ArrayList<Odbiorca>();
        for (OdbiorcaDTO odb : dto.getOdbiorcy()) {
            Odbiorca o = new Odbiorca(odb, n);
            o.setUser(userDAO.findById(odb.getUserId()));
            o.setOdebrano(false);

            if (odb.getEmail()) {
                try {
                    new Mail().send(o.getUser().getEmail(), n.getTemat(), n.getWiadomosc());
                } catch (Exception e) {
                    throw new Exception("Nie udało się wysłać wiadomości do użytkownika"
                        + ": " + o.getUser().toString() + " na adres: " + o.getUser().getEmail());
                }
            }
            odbiorcy.add(o);
        }
        n.setOdbiorcy(odbiorcy);
        nadawcaDAO.persist(n);

    }

}
