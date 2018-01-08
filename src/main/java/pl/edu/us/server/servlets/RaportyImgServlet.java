package pl.edu.us.server.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import gwtupload.server.MemoryFileItemFactory;
import pl.edu.us.shared.services.wnioski.WnioskiService;

@Singleton
@RemoteServiceRelativePath("usosweb/raport_img")
public class RaportyImgServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RaportyImgServlet.class.getName());

    @Inject
    private WnioskiService definicjaRaportowService;

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
        FileItemFactory factory = new MemoryFileItemFactory(1000000);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
			List<FileItem> fileItems = upload.parseRequest(request);
			if(!fileItems.isEmpty()){
				request.getSession().setAttribute("IMG", fileItems.get(0));
	        }
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
    }

}
