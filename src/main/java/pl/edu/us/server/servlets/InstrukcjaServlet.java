package pl.edu.us.server.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import pl.edu.us.shared.services.instrukcja.InstrukcjaService;

@Singleton
public class InstrukcjaServlet extends HttpServlet {
    private static final long serialVersionUID = 376139408976524942L;

//  final  InstrukcjaServiceAsync instrukcjaService = GWT.create(InstrukcjaService.class);

//    @Autowired
//    private InstrukcjaDAO instrukcjaDAO;
    @Inject
    InstrukcjaService instrukcjaService;//= new InstrukcjaServiceImpl();
    private EntityManagerFactory factory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (instrukcjaService != null) {
            byte[] pdftest = instrukcjaService.pobierz();
        }

        byte[] pdf = create();
        if (pdf != null) {
            resp.setContentType("application/pdf");
            ServletOutputStream outputStream = resp.getOutputStream();
            ByteStreams.copy(new ByteArrayInputStream(pdf), outputStream);
            outputStream.flush();
            outputStream.close();
        } else {
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>Wirtualny dziekanat</title></head><body><h1>Brak instrukcji w tabeli pplc_instrukcja</h1></body></html>");
            out.flush();
            out.close();
        }
    }

    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

    //  Database credentials
    static final String USER = "PZI";
    static final String PASS = "PZI";

    private byte[] create() {
        // JDBC driver name and database URL
        byte[] id = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT instrukcja FROM Instrukcja";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                id = rs.getBytes("instrukcja");
            }
            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } //end finally try
        } //end try
        return id;
    }
//
//    private byte[] pobierz() {
//        Instrukcja instrukcja = instrukcjaDAO.findById(1l);
//        if (instrukcja == null) {
//            return null;
//        } else {
//            return instrukcja.getInstrukcja();
//        }
//    }
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
