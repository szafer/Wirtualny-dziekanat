package pl.edu.us.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;

import pl.edu.us.shared.enums.Semestr;
import pl.edu.us.shared.enums.TypWniosku;

public final class ServerUtils {

    public static final String FONT = "FreeSans.ttf";
    public static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    public static final String DB_URL = "jdbc:oracle:thin:@jsedb01.coig.com:1521:jst01";

    //  Database credentials
    public static final String USER = "PPLCADM";
    public static final String PASS = "PPLCADM";
    
    public static String getImageData(byte[] bytes) {
        String base64 = Base64.encodeBase64String(bytes);
        base64 = "data:image/png;base64," + base64;
        return base64;
    }

    public static ByteArrayOutputStream pdf(Date dataZlozenia, Date dataRozpatrzenia, String imie, String nazwisko, Integer userId, Integer typ, byte[] wzor, BigDecimal kwota, String email)
        throws DocumentException, MalformedURLException, IOException {
        TypWniosku tw = TypWniosku.values()[typ];
        if (tw == TypWniosku.KOMISJA) {
            return pdfEK(dataZlozenia, dataRozpatrzenia, imie, nazwisko, userId, tw, wzor, kwota);
        } else if (tw == TypWniosku.PRZED_SESJI) {
            return pdfPS(dataZlozenia, dataRozpatrzenia, imie, nazwisko, userId, tw, wzor, kwota);
        } else if (tw == TypWniosku.STYPENDIUM) {
            return pdfST(dataZlozenia, dataRozpatrzenia, imie, nazwisko, userId, tw, wzor, kwota, email);
        } else {
            return pdfWAR(dataZlozenia, dataRozpatrzenia, imie, nazwisko, userId, tw, wzor, kwota, email);
        }
    }

    public static ByteArrayOutputStream pdfPS(Date dataZlozenia, Date dataRozpatrzenia, String imie, String nazwisko, Integer userId, TypWniosku typ, byte[] wzor, BigDecimal kwota)
        throws DocumentException, MalformedURLException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        Font f = FontFactory.getFont("FreeSans.ttf", BaseFont.IDENTITY_H, true);
        Date d = new Date();
        Paragraph par1 = new Paragraph("Katowice, " + new java.sql.Date(d.getYear(), d.getMonth(), d.getDay()).toString());
        par1.setLeading(50f);
        par1.setAlignment(2);
        par1.setIndentationRight(40f);
        document.add(par1);
        Paragraph par2 = new Paragraph(imie + " " + nazwisko, f);
        par2.setLeading(25f);
        par2.setIndentationLeft(45f);
        document.add(par2);

        Paragraph parEm = new Paragraph("Informatyka niestacjonarne II stopnia", f);
        parEm.setLeading(30f);
        parEm.setIndentationLeft(45f);
        document.add(parEm);

        Paragraph par3 = new Paragraph("II Rok," + (new Date().getMonth() < 6 ? Semestr.ZIMOWY.toString() : Semestr.LETNI.toString()) + ", Magisterskie", f);
        par3.setLeading(25f);
        par3.setIndentationLeft(45f);
        document.add(par3);

        Paragraph forma = new Paragraph("________");
        forma.setLeading(23f);
        forma.setIndentationLeft(75f);
        document.add(forma);

        Paragraph par4 = new Paragraph("" + userId);
        par4.setLeading(20f);
        par4.setIndentationLeft(50f);
        document.add(par4);

        Paragraph par5 = new Paragraph("\n\n\n\n\n\n" + imie + " " + nazwisko);
        par5.setAlignment(2);
        par5.setSpacingBefore(285f);
        par5.setIndentationRight(50f);
        document.add(par5);

        Paragraph par6 = new Paragraph(dataZlozenia.toString());
        par6.setLeading(75f);
        par6.setAlignment(2);
        par6.setIndentationRight(50f);
        document.add(par6);

        document.addTitle(typ.getNazwa());
        document.addCreationDate();
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(wzor);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
//        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();

        // step 5
        document.close();
        return baos;
    }

    public static ByteArrayOutputStream pdfEK(Date dataZlozenia, Date dataRozpatrzenia, String imie, String nazwisko, Integer userId, TypWniosku typ, byte[] wzor, BigDecimal kwota)
        throws DocumentException, MalformedURLException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        Font f = FontFactory.getFont("FreeSans.ttf", BaseFont.IDENTITY_H, true);
        Date d = new Date();
        Paragraph par1 = new Paragraph("Katowice, " + new java.sql.Date(d.getYear(), d.getMonth(), d.getDay()).toString());
        par1.setLeading(50f);
        par1.setAlignment(2);
        par1.setIndentationRight(20f);
        document.add(par1);
        Paragraph par2 = new Paragraph(imie + " " + nazwisko, f);
        par2.setLeading(25f);
        par2.setIndentationLeft(50f);
        document.add(par2);

        Paragraph parEm = new Paragraph("Informatyka niestacjonarne II stopnia", f);
        parEm.setLeading(30f);
        parEm.setIndentationLeft(50f);
        document.add(parEm);

        Paragraph par3 = new Paragraph("II Rok," + (new Date().getMonth() < 6 ? Semestr.ZIMOWY.toString() : Semestr.LETNI.toString()) + ", Magisterskie", f);
        par3.setLeading(25f);
        par3.setIndentationLeft(50f);
        document.add(par3);

        Paragraph forma = new Paragraph("________");
        forma.setLeading(23f);
        forma.setIndentationLeft(80f);
        document.add(forma);

        Paragraph par4 = new Paragraph("" + userId);
        par4.setLeading(20f);
        par4.setIndentationLeft(80f);
        document.add(par4);

        Paragraph par5 = new Paragraph("\n\n\n\n\n\n\n\n\n\n\n" + imie + " " + nazwisko);
        par5.setAlignment(2);
        par5.setSpacingBefore(280f);
        par5.setIndentationRight(30f);
        document.add(par5);

        Paragraph par6 = new Paragraph(dataZlozenia.toString());
        par6.setLeading(40f);
        par6.setAlignment(2);
        par6.setIndentationRight(25f);
        document.add(par6);

        document.addTitle(typ.getNazwa());
        document.addCreationDate();
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(wzor);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
//        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();

        // step 5
        document.close();
        return baos;
    }
    public static ByteArrayOutputStream pdfST(Date dataZlozenia, Date dataRozpatrzenia, String imie, String nazwisko, Integer userId, TypWniosku typ, byte[] wzor, BigDecimal kwota, String email)
        throws DocumentException, MalformedURLException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        Font f = FontFactory.getFont("FreeSans.ttf", BaseFont.IDENTITY_H, true);
        Paragraph par2 = new Paragraph(imie + " " + nazwisko +
            "                                                           "
            + userId, f);
        par2.setLeading(135f);
        par2.setIndentationLeft(100f);
        document.add(par2);

        Paragraph parEm = new Paragraph(email, f);
        parEm.setLeading(20f);
        parEm.setIndentationLeft(100f);
        document.add(parEm);

        Paragraph par3 = new Paragraph("Informatyki i Nauki o Materiałach", f);
        par3.setLeading(20f);
        par3.setIndentationLeft(100f);
        document.add(par3);

        Paragraph par4 = new Paragraph("Informatyka niestacjonarne II stopnia", f);
        par4.setLeading(20f);
        par4.setIndentationLeft(100f);
        document.add(par4);

        Paragraph forma = new Paragraph("x");
        forma.setLeading(12f);
        forma.setIndentationRight(5f);
        forma.setAlignment(2);
        document.add(forma);

        Paragraph poziom = new Paragraph("x");
        poziom.setLeading(10f);
        poziom.setIndentationLeft(145f);
        poziom.setAlignment(1);
        document.add(poziom);
        Paragraph par5 = new Paragraph("Uniwersytet Śląski                 "
            + "Informatyka          "
            + "2017              "
            + "Inż. oprogr.", f);
        par5.setLeading(20f);
        par5.setIndentationLeft(100f);
        document.add(par5);

        Paragraph uk = new Paragraph("x");
        uk.setLeading(28f);
        uk.setIndentationLeft(210f);
//        uk.setAlignment(2);
        document.add(uk);

        Paragraph par6 = new Paragraph(dataZlozenia.toString() + ", " + imie + " " + nazwisko);
        par6.setLeading(240f);
        par6.setAlignment(2);
//        par6.setIndentationRight(50f);
        document.add(par6);

        document.addTitle(typ.getNazwa());
        document.addCreationDate();
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(wzor);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
//        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();

        // step 5
        document.close();
        return baos;
    }
    public static ByteArrayOutputStream pdfWAR(Date dataZlozenia, Date dataRozpatrzenia, String imie, String nazwisko, Integer userId, TypWniosku typ, byte[] wzor, BigDecimal kwota, String email)
        throws DocumentException, MalformedURLException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        Font f = FontFactory.getFont("FreeSans.ttf", BaseFont.IDENTITY_H, true);
        Date d = new Date();
        Paragraph par1 = new Paragraph("Katowice, " + new java.sql.Date(d.getYear(), d.getMonth(), d.getDay()).toString());
        par1.setLeading(35f);
        par1.setAlignment(2);
        par1.setIndentationRight(80f);
        document.add(par1);
        Paragraph par2 = new Paragraph(imie + " " + nazwisko, f);
        par2.setLeading(25f);
        par2.setIndentationLeft(15f);
        document.add(par2);

        Paragraph parEm = new Paragraph("Informatyka niestacjonarne II stopnia", f);
        parEm.setLeading(30f);
        parEm.setIndentationLeft(15f);
        document.add(parEm);

        Paragraph par3 = new Paragraph("II Rok," + (new Date().getMonth() < 6 ? Semestr.ZIMOWY.toString() : Semestr.LETNI.toString()) + ", Magisterskie", f);
        par3.setLeading(25f);
        par3.setIndentationLeft(15f);
        document.add(par3);

        Paragraph forma = new Paragraph("________");
        forma.setLeading(23f);
        forma.setIndentationLeft(45f);
        document.add(forma);

        Paragraph par4 = new Paragraph("" + userId);
        par4.setLeading(20f);
        par4.setIndentationLeft(40f);
        document.add(par4);

        Paragraph par5 = new Paragraph("\n\n\n\n\n" + imie + " " + nazwisko);
        par5.setAlignment(2);
        par5.setSpacingBefore(260f);
        par5.setIndentationRight(80f);
        document.add(par5);

        document.addTitle(typ.getNazwa());
        document.addCreationDate();
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(wzor);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
//        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();

        // step 5
        document.close();
        return baos;
    }
}
