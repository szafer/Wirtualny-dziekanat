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

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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
    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String FONT = "resources/FreeSans.ttf";

    //  Database credentials
    static final String USER = "PZI";
    static final String PASS = "PZI";

    private byte[] wzor = null;
    private Date dataZlozenia = null, dataRozpatrzenia = null;
    private String imie = "", nazwisko = "", email;
    private BigDecimal kwota = null;
    private Integer typ = null, userId = null;
    private String tab = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
    @PersistenceContext(name = "MyPersistenceUnit")
    private EntityManagerFactory factory;

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

//            // step 1
//            Document document = new Document();
//            // step 2
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter writer = PdfWriter.getInstance(document, baos);
//            // step 3
//            document.open();
//            // step 4
//            Date d = new Date();
//            Paragraph par1 = new Paragraph("Katowice, " + new java.sql.Date(d.getYear(), d.getMonth(), d.getDay()).toString());
//            par1.setLeading(50f);
//            par1.setAlignment(2);
//            par1.setIndentationRight(20f);
//            document.add(par1);
//            Font f = FontFactory.getFont("FreeSans.ttf", BaseFont.IDENTITY_H, true);
//            Paragraph par2 = new Paragraph(imie + " " + nazwisko, f);
//            par2.setLeading(25f);
//            par2.setIndentationLeft(45f);
//            document.add(par2);
//
//            Paragraph parEm = new Paragraph("Informatyka niestacjonarne II stopnia", f);
//            parEm.setLeading(30f);
//            parEm.setIndentationLeft(45f);
//            document.add(parEm);
//
//            Paragraph par3 = new Paragraph("II Rok," + (new Date().getMonth() < 6 ? Semestr.ZIMOWY.toString() : Semestr.LETNI.toString()) + ", Magisterskie", f);
//            par3.setLeading(25f);
//            par3.setIndentationLeft(45f);
//            document.add(par3);
//
//            Paragraph forma = new Paragraph("________");
//            forma.setLeading(23f);
//            forma.setIndentationLeft(75f);
//            document.add(forma);
//
//            Paragraph par4 = new Paragraph("" + userId);
//            par4.setLeading(20f);
//            par4.setIndentationLeft(50f);
//            document.add(par4);
//
//            Paragraph par5 = new Paragraph("\n\n\n\n\n" + imie + " " + nazwisko);
//            par5.setAlignment(2);
//            par5.setSpacingBefore(260f);
//            par5.setIndentationRight(50f);
//            document.add(par5);
//
////            Paragraph par6 = new Paragraph("\n\n\n" + dataZlozenia.toString());
////            par6.setLeading(50f);
////            par6.setAlignment(2);
////            par6.setIndentationRight(50f);
////            document.add(par6);
//
//            document.addTitle(TypWniosku.values()[typ].getNazwa());
//            document.addCreationDate();
//            PdfContentByte canvas = writer.getDirectContentUnder();
//            Image image = Image.getInstance(wzor);
//            image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            image.setAbsolutePosition(0, 0);
//            canvas.saveState();
//            PdfGState state = new PdfGState();
////            state.setFillOpacity(0.6f);
//            canvas.setGState(state);
//            canvas.addImage(image);
//            canvas.restoreState();
//
//            // step 5
//            document.close();

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
//        response.setContentType("application/pdf");
//        try {
//            // Get the text that will be added to the PDF
//            String text = request.getParameter("imie");
//            if (text == null || text.trim().length() == 0) {
//                text = "You didn't enter any text.";
//            }
//            // step 1
//            Document document = new Document();
//            // step 2
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter.getInstance(document, baos);
//            // step 3
//            document.open();
//            // step 4
//            document.add(new Paragraph(String.format(
//                "You have submitted the following text using the %s method:",
//                request.getMethod())));
//            document.add(new Paragraph(text));
//            // step 5
//            document.close();
//
//            // setting some response headers
//            response.setHeader("Expires", "0");
//            response.setHeader("Cache-Control",
//                "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            // setting the content type
//            response.setContentType("application/pdf");
//            // the contentlength
//            response.setContentLength(baos.size());
//            // write ByteArrayOutputStream to the ServletOutputStream
//            OutputStream os = response.getOutputStream();
//            baos.writeTo(os);
//            os.flush();
//            os.close();
//        } catch (DocumentException e) {
//            throw new IOException(e.getMessage());
//        }
    }

    private byte[] create(Integer idWniosku) {
        // JDBC driver name and database URL

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
