package pl.edu.us.client;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import pl.edu.us.server.services.WczytanePlikiActionHandler;
import pl.edu.us.shared.action.WczytanePliki;

public class ServerModule extends HandlerModule {
    public ServerModule() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void configureHandlers() {
        // TODO Auto-generated method stub
        bindHandler(WczytanePliki.class, WczytanePlikiActionHandler.class);

    }

}
