package pl.edu.us.client.main.handlers;

import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent.ParseErrorHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class ErrorHandler implements ParseErrorHandler {

	@Override
	public void onParseError(ParseErrorEvent event) {
		Info.display("Błąd formatowania",
				"Niewłaściwa wartość : " + event.getErrorValue() == null ? "" : event.getErrorValue() + "");
	}

}
