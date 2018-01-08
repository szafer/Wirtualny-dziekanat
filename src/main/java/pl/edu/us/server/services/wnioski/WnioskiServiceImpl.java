package pl.edu.us.server.services.wnioski;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.inject.Provider;

import pl.edu.us.server.dao.UWniosekDAO;
import pl.edu.us.server.dao.WniosekDAO;
import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
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
   
//    @Autowired
    private Provider<HttpSession> sessionProvider ;
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
            wynik.add(mapper.map(w, WniosekDTO.class));
        }
        return wynik;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<WniosekDTO> zapisz(List<WniosekDTO> doZapisu) throws Exception {
      HttpSession httpSession = sessionProvider.get();
      FileItem fileItem = (FileItem) httpSession.getAttribute("IMG");
      if(fileItem!=null){
//          raportyImg.setRozmiarObrazu(fileItem.getSize());
//          raportyImg.setNazwaObrazu(fileItem.getName());
//          raportyImg.setRozszerzenieObrazu(fileItem.getContentType());
//          raportyImg.setObraz(fileItem.get());
      }
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

    public String getImageData(Byte[] bytes) {
        byte[] b = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            b[i] = bytes[i];
        }
//        String base64 = Base64Utils.toBase64(b);
//        base64 = "data:image/png;base64," + base64;
//        return base64;
        String base64 = Base64.encodeBase64String(b);
        base64 = "data:image/png;base64," + base64;
        return base64;
    }

    @Override
    public List<UWniosekDTO> pobierzWnioskiStudentow() {
        ModelMapper mapper = new ModelMapper();
        List<UWniosek> wnioski = uWniosekDAO.findAll();
        List<UWniosekDTO> wynik = new ArrayList<UWniosekDTO>(wnioski.size());
        for (UWniosek w : wnioski) {
            WniosekDTO wniosek = mapper.map(w.getWniosek(), WniosekDTO.class);
            UWniosekDTO wDto = new UWniosekDTO(w, wniosek);
            if (wDto.getZlozonyWniosek() != null) {
                wDto.setImage(getImageData(wDto.getZlozonyWniosek()));
            }
            wynik.add(wDto);
        }
        return wynik;
    }
}
