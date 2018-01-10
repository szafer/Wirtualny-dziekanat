package pl.edu.us.client.wnioski.definicja;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.Component;

import pl.edu.us.client.main.handlers.ActionCallback;
import pl.edu.us.client.main.handlers.GwtpProxy;
import pl.edu.us.client.main.handlers.Masking;
import pl.edu.us.client.main.handlers.RpcMasking;
import pl.edu.us.shared.action.WczytanePliki;
import pl.edu.us.shared.action.WczytanePlikiResult;
import pl.edu.us.shared.dto.ObrazDTO;
import pl.edu.us.shared.enums.Message;

public class PlikiProxy extends GwtpProxy<Object, PagingLoadResult<ObrazDTO>> {

    private Masking rpcMasking;
    @Inject
    private DispatchAsync dispatcher;

    @Inject
    public PlikiProxy(Component component, DispatchAsync dispatcher) {
        super(component);
        this.rpcMasking = new RpcMasking(component);
        this.dispatcher = dispatcher;
    }

    @Override
    public void load(final Object loadConfig, final AsyncCallback<PagingLoadResult<ObrazDTO>> callback) {
        super.load(loadConfig, callback);
        dispatcher.execute(new WczytanePliki(),
            rpcMasking.call(Message.LOADING,
                new ActionCallback<WczytanePlikiResult>() {
                    @Override
                    public void onSuccess(WczytanePlikiResult result) {
                        callback.onSuccess(result.getResult());
                    }
                }));
    }

}
