package pl.edu.us.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author dmartin
 *
 */
@UiTemplate("LogoutButton")
public class LogoutButton extends Button {
    private static final String DEFAULT_SPRING_LOGOUT_URL = "http://localhost:8180/Usosweb/Logout.html";
    private static final String DEFAULT_BUTTON_TEXT = "Zamknij aplikację";
    private String logoutUrl = null;

    public LogoutButton() {
        this(DEFAULT_BUTTON_TEXT);
    }

    public LogoutButton(String html) {
        this.setHTML(html);
        this.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cookies.removeCookie("loggedUser");
                shownextpage("");
                Window.Location.replace("Logout.html");
            }
        });
    }

    @Override
    public Widget asWidget() {
        // TODO Auto-generated method stub
        return this;
    }

    public String getLogoutUrl() {
        if (logoutUrl == null) {
            logoutUrl = "localhost:8180/Usosweb/Logout.html";
            // logoutUrl = GWT.getHostPageBaseURL() + DEFAULT_SPRING_LOGOUT_URL;
        }
        return logoutUrl;
    }

    public void setLogoutUrl(final String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    private native void shownextpage(String message) /*-{
		//      $wnd.close();
		$wnd.alert("Nastąpiło wylogowanie z aplikacji");
		//      $wnd.location = "Logout.html";
    }-*/;
}
