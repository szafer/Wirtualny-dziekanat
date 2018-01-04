package pl.edu.us.client.main.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.widget.core.client.Component;

public abstract class GwtpProxy<C, D> extends RpcProxy<C, D> {

    @Inject
    protected Masking rpcMasking = new RpcMasking();
    protected Component component;

    public GwtpProxy() {
    }

    public GwtpProxy(Widget widget) {
        this.component = (Component) widget;
        rpcMasking.setMaskedComponent(component);
    }

    public GwtpProxy(Component component) {
        this.component = component;
        rpcMasking.setMaskedComponent(component);
    }

    public void setWidgetMasking(Widget widget) {
        rpcMasking.setMaskedComponent((Component) widget);
    }

    public void setWidgetMasking(Component component) {
        rpcMasking.setMaskedComponent(component);
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

//    @Override
//    protected void load(Object loadConfig, AsyncCallback<D> callback) {
//        setWidgetMasking(component);
//    }
}
