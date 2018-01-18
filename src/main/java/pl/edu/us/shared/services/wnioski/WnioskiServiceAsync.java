package pl.edu.us.shared.services.wnioski;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pl.edu.us.shared.dto.wnioski.UWniosekDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;

public interface WnioskiServiceAsync {

    void getWnioski(AsyncCallback<List<WniosekDTO>> callback);

    void zapisz(List<WniosekDTO> doZapisu, AsyncCallback<List<WniosekDTO>> callback);

    void pobierzWnioskiStudentow(AsyncCallback<List<UWniosekDTO>> asyncCallback);

    void zapiszWnoiski(List<UWniosekDTO> doZapisu, AsyncCallback<List<UWniosekDTO>> callback);

}
