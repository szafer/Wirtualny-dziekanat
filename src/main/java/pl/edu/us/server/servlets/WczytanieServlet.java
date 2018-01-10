package pl.edu.us.server.servlets;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class WczytanieServlet extends UploadAction {

    private static final long serialVersionUID = -1315411100965698922L;


    @Override
    public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
        String wynik = "koniec";
        return wynik;

    }

}
