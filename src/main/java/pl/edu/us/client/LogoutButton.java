package pl.edu.us.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author dmartin
 *
 */
@UiTemplate("LogoutButton")
public class LogoutButton extends Button {
	private static final String DEFAULT_SPRING_LOGOUT_URL = "http://127.0.0.1:8888/GWTSpring.html";
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
			logoutUrl = "localhost/uczelnia/start.php";
			// logoutUrl = GWT.getHostPageBaseURL() + DEFAULT_SPRING_LOGOUT_URL;
		}
		return logoutUrl;
	}

	public void setLogoutUrl(final String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
}