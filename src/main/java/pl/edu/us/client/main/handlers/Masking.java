package pl.edu.us.client.main.handlers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Component;

import pl.edu.us.shared.enums.Message;

public interface Masking {
    
    public <X> AsyncCallback<X> call(Message message, final AsyncCallback<X> subcallback);

    public void setMaskedComponent(Component maskedComponent);
}
