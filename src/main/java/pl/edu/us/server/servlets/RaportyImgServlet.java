package pl.edu.us.server.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.io.ByteStreams;
import com.google.inject.Singleton;

import gwtupload.server.MemoryFileItemFactory;

@Singleton
//@RemoteServiceRelativePath("usosweb/raport_img")
public class RaportyImgServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

//    @Inject
//    private AppKontekst kontekst;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RaportyImgServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        File uploadedFile;
//
//        
//        System.out.print("on server");
//    try{ 
//        
//     // String filename = 
//
//        File uploadedFile1=new File("usosweb/happybir.jpg");
//
//        
//
//
//      String kk=uploadedFile1.getAbsolutePath();
//
//      System.out.print(kk);
//
//        File f=new File(kk);
//      
//        //System.out.println("foder is " + folder);
//        response.setContentType("application/octet-stream"); 
//        response.setHeader("Content-disposition", "attachment;filename=\"" +"happybir.jpg" + "\"");
//        //res.setHeader("Content-Disposition","attachment; filename=;");
//        ServletOutputStream stream = response.getOutputStream();
//        BufferedInputStream fif = new BufferedInputStream(new FileInputStream(f));
//        int data;
//        while((data = fif.read()) != -1) {
//        stream.write(data);
//        }
//        fif.close();
//        stream.close(); 
//      
//    }catch(Exception e)  
//    {  
//    System.out.println("Exception Due to"+e);  
//    e.printStackTrace();  
//    }  

//        //your image servlet code here
//        response.setContentType("image/jpeg");
//
//        // Set content size
//        File file = new File("localhost/Usosweb/usosweb/image.jpg");
//        response.setContentLength((int)file.length());
//
//        // Open the file and output streams
//        FileInputStream in = new FileInputStream(file);
//        OutputStream out = response.getOutputStream();
//
//        // Copy the contents of the file to the output stream
//        byte[] buf = new byte[1024];
//        int count = 0;
//        while ((count = in.read(buf)) >= 0) {
//            out.write(buf, 0, count);
//        }
//        in.close();
//        out.close();

//        if (! ServletFileUpload.isMultipartContent(request)) {
//          response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//              "Not a multipart request"); 
//          return;
//        }
//
//        ServletFileUpload upload = new ServletFileUpload(); // from Commons
//
//        try {
//          FileItemIterator iter = upload.getItemIterator(request);
//
//          if (iter.hasNext()) {
//            FileItemStream fileItem = iter.next();
//
////          String name = fileItem.getFieldName(); // file name, if you need it
//
//            ServletOutputStream out = response.getOutputStream();
//            response.setBufferSize(32768);
//            int bufSize = response.getBufferSize(); 
//            byte[] buffer = new byte[bufSize];
//
//            InputStream in = fileItem.openStream();
//            BufferedInputStream bis = new BufferedInputStream(in, bufSize);
//
//            long length = 0;
//
//            int bytes; 
//            while ((bytes = bis.read(buffer, 0, bufSize)) >= 0) {
//              out.write(buffer, 0, bytes);
//              length += bytes;
//            }
//
//            response.setContentType("text/html");
//            response.setContentLength(
//                (length > 0 && length <= Integer.MAX_VALUE) ? (int) length : 0);
//
//            bis.close();
//            in.close();
//            out.flush();
//            out.close();
//          }
//        } catch(Exception caught) {
//          throw new RuntimeException(caught);
//        }

//        
//        
//        
        FileItemFactory factory = new MemoryFileItemFactory(1000000);
        ServletFileUpload upload = new ServletFileUpload(factory);
        ServletContext sc = getServletContext();
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            if (!fileItems.isEmpty()) {
                FileItem fileItem = fileItems.get(0);
//              // Get the MIME type of the image
//                String mimeType = sc.getMimeType(fileItem.getContentType());
//                if (mimeType == null) {
//                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                    return;
//                }
                //
//              // Set content type
                response.setContentType("image/jpeg");
//                response.setContentType(mimeType);

//                request.getSession().setAttribute("IMG", fileItems.get(0));
//                response.setContentType(fileItem.getContentType());

                ServletOutputStream outputStream = response.getOutputStream();
                ByteStreams.copy(new ByteArrayInputStream(fileItem.get()), outputStream);
                outputStream.flush();
                outputStream.close();

//              PrintWriter out = response.getWriter();
//              out.println(getImageData(fileItem.get(), fileItem.getContentType()));
//              out.flush();
//              out.close();
            } else {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Wirtualny dziekanat</title></head><body><h1>Nie udało się pobrać pliku</h1></body></html>");
                out.flush();
                out.close();
            }
//                ObrazDTO obraz = new ObrazDTO();
//                obraz.setSize(fileItem.getSize());
//                obraz.setBs(getImageData(fileItem.get()));
//                obraz.setNazwa(fileItem.getName());
//                obraz.setRozszerzenie(fileItem.getContentType());
//                kontekst.setObraz(obraz);
//		          raportyImg.setNazwaObrazu(fileItem.getName());
//		          raportyImg.setRozszerzenieObrazu(fileItem.getContentType());
//		          raportyImg.setObraz(fileItem.get());
//            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
//        // Get the absolute path of the image
//        try {
//            ServletContext sc = getServletContext();
//            // i want to load the image in the specified folder (outside the web container)
//            String filename = "/home/marek/Obrazy/20161230_174732.jpg";
//
//            // Get the MIME type of the image
//            String mimeType = sc.getMimeType(filename);
//            if (mimeType == null) {
//                sc.log("Could not get MIME type of " + filename);
//                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                return;
//            }
//
//            // Set content type
////            resp.setContentType("image/jpeg");
//            resp.setContentType(mimeType);
//
//            // Set content size
//            File file = new File(filename);
//            resp.setContentLength((int) file.length());
//
//            // Open the file and output streams
//            FileInputStream in = new FileInputStream(file);
//            OutputStream out;
//            out = resp.getOutputStream();
//
//            // Copy the contents of the file to the output stream
//            byte[] buf = new byte[1024];
//            int count = 0;
//            while ((count = in.read(buf)) >= 0) {
//                out.write(buf, 0, count);
//            }
//            in.close();
//            out.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

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
