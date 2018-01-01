package pl.edu.us.server.services.przedmioty;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import pl.edu.us.server.dao.PrzedmiotDAO;
import pl.edu.us.server.dao.UPrzedmiotDAO;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.dto.przedmioty.PrzedmiotDTO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.enums.Semestr;
import pl.edu.us.shared.model.User;
import pl.edu.us.shared.model.przedmioty.Przedmiot;
import pl.edu.us.shared.model.przedmioty.UPrzedmiot;
import pl.edu.us.shared.services.przedmioty.PrzedmiotyService;

@Service("przedmiotyService")
public class PrzedmiotyServiceImpl implements PrzedmiotyService {

    @Autowired
    private UPrzedmiotDAO uPrzedmiotDAO;

    @Autowired
    private PrzedmiotDAO przedmiotDAO;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UPrzedmiotDTO> getPrzedmiotyStudentow(Integer przedmiot) {
        List<UPrzedmiot> lista = uPrzedmiotDAO.getEntityManager()
            .createNamedQuery(UPrzedmiot.DAJ_STUDENTOW_PRZEDMIOTU)
            .setParameter("przedmiot", przedmiot)
            .getResultList();
        List<UPrzedmiotDTO> wynik = new ArrayList<UPrzedmiotDTO>();
        ModelMapper mapper = new ModelMapper();
        for (UPrzedmiot p : lista) {
            UPrzedmiotDTO pDto = mapper.map(p, UPrzedmiotDTO.class);
            pDto.setSemestr(pDto.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
            wynik.add(pDto);
        }
        return wynik;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void zapisz(List<UPrzedmiotDTO> doZapisu) {
        ModelMapper mapper = new ModelMapper();
        for (UPrzedmiotDTO dto : doZapisu) {
            UPrzedmiot p = mapper.map(dto, UPrzedmiot.class);
            if (p.getId() < 1) {
                p.setId(null);
                uPrzedmiotDAO.persist(p);
            } else {
                uPrzedmiotDAO.merge(p);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<PrzedmiotDTO> zapisz(List<PrzedmiotDTO> doZapisu, List<PrzedmiotDTO> doUsuniecia) {
        ModelMapper mapper = new ModelMapper();
        for (PrzedmiotDTO pDto : doZapisu) {
            Przedmiot p = mapper.map(pDto, Przedmiot.class);
            if (p.getId() < 1) {
                p.setId(null);
                przedmiotDAO.persist(p);
                pDto.setId(p.getId());//odswieżenie id
            } else {
                przedmiotDAO.merge(p);
            }
            //zapis wykladowcy
            if (pDto.getWykladowca() != null) {
                pDto.getWykladowca().setPrzedmiot(pDto);
                zapisz(Lists.newArrayList(pDto.getWykladowca()));
//                UPrzedmiot wykl = mapper.map(pDto.getWykladowca(), UPrzedmiot.class);
            }
            if (pDto.getStudenci() != null && !pDto.getStudenci().isEmpty()) {
                for (UPrzedmiotDTO studDto : pDto.getStudenci()) {
                    studDto.setPrzedmiot(pDto);
                }
                zapisz(pDto.getStudenci());
            }
        } //narazie nie ma usuwania
        return getKierunki();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<PrzedmiotDTO> getKierunki() {
        List<Przedmiot> lista = przedmiotDAO.findAll();
        ModelMapper mapper = new ModelMapper();
        List<PrzedmiotDTO> wynik = new ArrayList<PrzedmiotDTO>();
        for (Przedmiot p : lista) {
            PrzedmiotDTO pDto = mapper.map(p, PrzedmiotDTO.class);
            pDto.setStudenci(getPrzedmiotyStudentow(pDto.getId()));
            pDto.setWykladowca(getWykladowca(pDto.getId()));
            wynik.add(pDto);
        }
        return wynik;
    }

    @SuppressWarnings("unchecked")
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private UPrzedmiotDTO getWykladowca(Integer przedmiot) {
        List<UPrzedmiot> lista = uPrzedmiotDAO.getEntityManager()
            .createNamedQuery(UPrzedmiot.DAJ_WYKLADOWCE)
            .setParameter("przedmiot", przedmiot)
            .setMaxResults(1)
            .getResultList();
        if (!lista.isEmpty()) {
            ModelMapper mapper = new ModelMapper();
            UPrzedmiotDTO wynik = mapper.map(lista.get(0), UPrzedmiotDTO.class);
            wynik.setSemestr(wynik.getDataSemestru().getMonth() < 6 ? Semestr.LETNI : Semestr.ZIMOWY);
            return wynik;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDTO> getWykladowcy() {
        List<User> lista = uPrzedmiotDAO.getEntityManager()
            .createNamedQuery(UPrzedmiot.DAJ_WYKLADOWCOW)
            .getResultList();
        List<UserDTO> wynik = new ArrayList<UserDTO>();
        ModelMapper mapper = new ModelMapper();
        for (User p : lista) {
            UserDTO pDto = mapper.map(p, UserDTO.class);
            wynik.add(pDto);
        }
        return wynik;
    }
}
