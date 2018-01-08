package pl.edu.us.client;

import com.google.inject.servlet.ServletModule;

import pl.edu.us.server.servlets.RaportyImgServlet;

public class DispatchServletModule extends ServletModule {

    @Override
    public void configureServlets() {
//        String address = "/" + ActionImpl.DEFAULT_SERVICE_NAME + "*";

         serve("/usosweb/usosweb/raport_img").with(RaportyImgServlet.class);
    }

}
