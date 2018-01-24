package pl.edu.us.client;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

import pl.edu.us.server.servlets.InstrukcjaServlet;
import pl.edu.us.server.servlets.DokumentacjaServlet;
import pl.edu.us.server.servlets.DrukujWniosekServlet;

public class DispatchServletModule extends ServletModule {

    @Override
    public void configureServlets() {
        String address = "/" + ActionImpl.DEFAULT_SERVICE_NAME + "*";

         serve("/wniosek").with(DrukujWniosekServlet.class);
         serve("/instrukcja").with(InstrukcjaServlet.class);
         serve("/dokumentacja").with(DokumentacjaServlet.class);
         serve(address).with(DispatchServiceImpl.class);

    }

}
