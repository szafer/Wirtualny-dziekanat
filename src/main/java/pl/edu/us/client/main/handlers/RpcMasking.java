package pl.edu.us.client.main.handlers;
import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Component;

import pl.edu.us.shared.enums.Message;
public class  RpcMasking implements Masking {

    private Component maskedComponent;

    private static final int MINIMAL_MASKING_TIME = 500;
    private Long startTime;

    public RpcMasking() {
    }

    public RpcMasking(Component maskedComponent) {
        this.maskedComponent = maskedComponent;
    }

    public <X> AsyncCallback<X> call(Message message, final AsyncCallback<X> subcallback) {
        mask(message);
        new Timer() {
            @Override
            public void run() {
                startTime = null;
            }
        }.schedule(MINIMAL_MASKING_TIME);

        startTime = new Date().getTime();

        return new AsyncCallback<X>() {

            @Override
            public void onFailure(final Throwable caught) {
                if (startTime != null) {
                    int remain = (int) (MINIMAL_MASKING_TIME - (new Date().getTime() - startTime));
                    if (remain > 0) {
                        new Timer() {
                            @Override
                            public void run() {
                                onFailure(caught);
                            }
                        }.schedule(remain);
                        return;
                    }
                }
                try {
                    subcallback.onFailure(caught);
                } finally {
                    unmask();
                }
            }

            @Override
            public void onSuccess(final X result) {
                if (startTime != null) {
                    int remain = (int) (MINIMAL_MASKING_TIME - (new Date().getTime() - startTime));
                    if (remain > 0) {
                        new Timer() {
                            @Override
                            public void run() {
                                onSuccess(result);
                            }
                        }.schedule(remain);
                        return;
                    }
                }
                try {
                    subcallback.onSuccess(result);
                } finally {
                    unmask();
                }
            }
        };
    }

    public <Y> AsyncCallback<Y> call(StandardCallback<Y> subcallback) {
        return call(null, subcallback);
    }

    public void mask(Message message) {
        if (message == null) {
            mask();
        } else {
            mask(message.getValue());
        }
    }

    public void unmask() {
        if (maskedComponent != null) {
            maskedComponent.unmask();
        }
    }

    private void mask() {
        if (maskedComponent != null) {
            maskedComponent.mask();
        }
    }

    private void mask(String message) {
        if (maskedComponent != null) {
            maskedComponent.mask(message);
        }
    }

    public Component getMaskedComponent() {
        return maskedComponent;
    }

    public void setMaskedComponent(Component maskedComponent) {
        this.maskedComponent = maskedComponent;
    }

}
