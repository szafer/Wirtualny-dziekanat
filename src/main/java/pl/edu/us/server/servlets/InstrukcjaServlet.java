package pl.edu.us.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class InstrukcjaServlet extends HttpServlet {
	private static final long serialVersionUID = 376139408976524942L;

//    @Inject
//    InstrukcjaService instrukcjaService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        byte[] pdf = instrukcjaService.pobierz();
//        if (pdf != null) {
//	        resp.setContentType("application/pdf");
//	        ServletOutputStream outputStream = resp.getOutputStream();
//	        ByteStreams.copy(new ByteArrayInputStream(instrukcjaService.pobierz()), outputStream);
//	        outputStream.flush();
//	        outputStream.close();
//        } else {
//        	resp.setContentType("text/html;charset=utf-8");
//        	PrintWriter out = resp.getWriter();
//            out.println("<html><head><title>PPLC</title></head><body><h1>Brak instrukcji w tabeli pplc_instrukcja</h1></body></html>");
//            out.flush();
//            out.close();
//        }
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
//@NamedQueries({ @NamedQuery(name = Instrukcja.INSTRUKCJA, query = "SELECT i.instrukcja FROM Instrukcja i") })
//@Entity
//@Table(name = "PPLC_INSTRUKCJA")
//public class Instrukcja {
//
//    public static final String INSTRUKCJA = "Instrukcja.INSTRUKCJA";
//
//    @Id
//    private Long id;
//    @Lob
//    private byte[] instrukcja;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public byte[] getInstrukcja() {
//        return instrukcja;
//    }
//
//    public void setInstrukcja(byte[] instrukcja) {
//        this.instrukcja = instrukcja;
//    }
