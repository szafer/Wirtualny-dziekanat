package pl.edu.us.shared.action;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

import pl.edu.us.shared.dto.ObrazDTO;

public class WczytanePlikiResult extends PagingLoadResultBean<ObrazDTO> implements Result {

    private static final long serialVersionUID = 3709576993931884694L;
    private PagingLoadResultBean<ObrazDTO> result;

    @SuppressWarnings("unused")
    private WczytanePlikiResult() {
        // For serialization only
    }

    public WczytanePlikiResult(PagingLoadResultBean<ObrazDTO> result) {
        this.result = result;
    }

    public PagingLoadResultBean<ObrazDTO> getResult() {
        return result;
    }
}
