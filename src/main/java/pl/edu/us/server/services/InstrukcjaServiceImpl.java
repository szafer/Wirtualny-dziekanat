package pl.edu.us.server.services;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.us.server.dao.InstrukcjaDAO;
import pl.edu.us.shared.model.Instrukcja;
import pl.edu.us.shared.services.instrukcja.InstrukcjaService;

@Service("instrukcjaService")
public class InstrukcjaServiceImpl implements InstrukcjaService {
    @Autowired
    private InstrukcjaDAO instrukcjaDAO;
//    InstrukcjaServiceAsync instrService = GWT.create(InstrukcjaService.class);

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public byte[] pobierz() {
        Instrukcja instrukcja = instrukcjaDAO.findById(1l);
        if (instrukcja == null) {
            return null;
        } else {
            return instrukcja.getInstrukcja();
        }
    }

}
