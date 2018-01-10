package pl.edu.us.server.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import pl.edu.us.server.dao.InstrukcjaDAO;
import pl.edu.us.shared.model.Instrukcja;
import pl.edu.us.shared.services.instrukcja.InstrukcjaService;

@Singleton
public class InstrukcjaServlet extends HttpServlet {
    private static final long serialVersionUID = 376139408976524942L;

//  final  InstrukcjaServiceAsync instrukcjaService = GWT.create(InstrukcjaService.class);

    @Autowired
    private InstrukcjaDAO instrukcjaDAO;
//    @Inject
//    InstrukcjaService instrukcjaService ;//= new InstrukcjaServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        byte[] pdf = pobierz();//instrukcjaService.pobierz();
        if (pdf != null) {
            resp.setContentType("application/pdf");
            ServletOutputStream outputStream = resp.getOutputStream();
//            ByteStreams.copy(new ByteArrayInputStream(instrukcjaService.pobierz()), outputStream);
            outputStream.flush();
            outputStream.close();
        } else {
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>PPLC</title></head><body><h1>Brak instrukcji w tabeli pplc_instrukcja</h1></body></html>");
            out.flush();
            out.close();
        }
    }
    private  byte[] pobierz() {
        Instrukcja instrukcja = instrukcjaDAO.findById(1l);
        if (instrukcja == null) {
            return null;
        } else {
            return instrukcja.getInstrukcja();
        }
    }
}
//@EJB
//InstrukcjaRepository instrukcjaRepository;
//
//@Override
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//public byte[] pobierz() {
//    Instrukcja instrukcja = instrukcjaRepository.find(1l);
//    if (instrukcja == null) {
//        return null;
//    } else {
//        return instrukcja.getInstrukcja();
//    }
//}
