package pl.edu.us.server.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Singleton;
import com.itextpdf.text.DocumentException;

import pl.edu.us.server.ServerUtils;

@Singleton
public class DrukujWniosekServlet extends HttpServlet {

    private static final long serialVersionUID = -5891629596643232401L;

    private byte[] wzor = null;
    private Date dataZlozenia = null, dataRozpatrzenia = null;
    private String imie = "", nazwisko = "", email;
    private BigDecimal kwota = null;
    private Integer typ = null, userId = null;


    @PersistenceUnit(name = "MyPersistenceUnit")
    private EntityManager factory;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrukujWniosekServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        try {
            // Get the text that will be added to the PDF
            String text = request.getParameter("id");
            create(Integer.parseInt(text));

            ByteArrayOutputStream baos = ServerUtils.pdf(dataZlozenia, dataRozpatrzenia, imie, nazwisko, userId, typ, wzor, kwota, email);
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private byte[] create(Integer idWniosku) {
        // JDBC driver name and database URL

        Statement stmt = null;
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName(ServerUtils.JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(ServerUtils.DB_URL, ServerUtils.USER, ServerUtils.PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = ""
                + "select uw.data_zlozenia, "
                + " u.imie, u.nazwisko, "
                + " uw.data_rozpatrzenia , uw.kwota, w.typ, "
                + " w.wzor, u.id, u.email "
                + " from u_wniosek uw "
                + " JOIN uzytkownik u on u.id = uw.uzytkownik_id "
                + " JOIN WNIOSEK w on w.id = uw.wniosek_id "
                + " WHERE uw.id =  " + idWniosku;
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                dataZlozenia = rs.getDate("data_zlozenia");
                imie = rs.getString("imie");
                nazwisko = rs.getString("nazwisko");
                dataRozpatrzenia = rs.getDate("data_rozpatrzenia");
                kwota = rs.getBigDecimal("kwota");
                typ = rs.getInt("typ");
                wzor = rs.getBytes("wzor");
                userId = rs.getInt("id");
                email = rs.getString("email");
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
        return wzor;
    }

    public Byte[] getImageData(byte[] bytes) {
        Byte[] b = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            b[i] = bytes[i];
        }
        return b;
    }

    public String getImageData(byte[] bytes, String string) {
        String base64 = Base64.encodeBase64String(bytes);
        base64 = "data:image/" + string + ";base64," + base64;
        return base64;
    }
}
