package pl.edu.us.server.services.wnioski;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.us.server.ServerUtils;
import pl.edu.us.server.dao.UWniosekDAO;
import pl.edu.us.server.dao.WniosekDAO;
import pl.edu.us.shared.dto.przedmioty.UPrzedmiotDTO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.model.przedmioty.UPrzedmiot;
import pl.edu.us.shared.model.wnioski.UWniosek;
import pl.edu.us.shared.model.wnioski.Wniosek;
import pl.edu.us.shared.services.wnioski.WnioskiService;

@Service("wnioskiService")
public class WnioskiServiceImpl implements WnioskiService {

    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(WnioskiServiceImpl.class.getName());

    @Autowired
    private WniosekDAO wniosekDAO;

    @Autowired
    private UWniosekDAO uWniosekDAO;

    @PostConstruct
    public void init() throws Exception {

    }

    @PreDestroy
    public void destroy() {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<WniosekDTO> getWnioski() {
        ModelMapper mapper = new ModelMapper();
        List<Wniosek> wnioski = wniosekDAO.findAll();
        List<WniosekDTO> wynik = new ArrayList<WniosekDTO>();
        for (Wniosek w : wnioski) {
            WniosekDTO ww = mapper.map(w, WniosekDTO.class);
            ww.setObraz(ServerUtils.getImageData(ww.getWzor()));
            wynik.add(ww);
        }
        return wynik;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<WniosekDTO> zapisz(List<WniosekDTO> doZapisu) throws Exception {
        ModelMapper mapper = new ModelMapper();
        for (WniosekDTO dto : doZapisu) {
            Wniosek p = mapper.map(dto, Wniosek.class);
            if (p.getId() == null)
                wniosekDAO.persist(p);
            else if (p.getId() < 1) {
                p.setId(null);
                wniosekDAO.persist(p);
            } else {
                wniosekDAO.merge(p);
            }
        }
        return new ArrayList<WniosekDTO>();
    }

    @Override
    public List<UWniosekDTO> pobierzWnioskiStudentow() {
//        ModelMapper mapper = new ModelMapper();
        List<UWniosek> wnioski = uWniosekDAO.findAll();
        List<UWniosekDTO> wynik = new ArrayList<UWniosekDTO>(wnioski.size());
        for (UWniosek w : wnioski) {
            WniosekDTO wniosek = new WniosekDTO(w.getWniosek());
            UWniosekDTO wDto = new UWniosekDTO(w, wniosek);
            if (wDto.getZlozonyWniosek() != null) {
//                wDto.setImage(ServerUtils.getImageData(wDto.getZlozonyWniosek()));
            }
            wynik.add(wDto);
        }
        return wynik;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UWniosekDTO> zapiszWnoiski(List<UWniosekDTO> doZapisu) {
        ModelMapper mapper = new ModelMapper();
        for (UWniosekDTO dto : doZapisu) {
            UWniosek p = mapper.map(dto, UWniosek.class);
            if (p.getId() == null)
                uWniosekDAO.persist(p);
            else if (p.getId() < 1) {
                p.setId(null);
                uWniosekDAO.persist(p);
            } else {
                uWniosekDAO.merge(p);
            }
        }
        return pobierzWnioskiStudentow();
    }
}
