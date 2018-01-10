package pl.edu.us.shared.services.instrukcja;

import javax.ejb.Local;

import com.google.gwt.user.client.rpc.RemoteService;

//@RemoteServiceRelativePath("usosweb/manualService")
@Local
public interface InstrukcjaService /*extends RemoteService*/ {

    byte[] pobierz();

}
