package pl.edu.us.client.main.handlers;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class FieldHandler<T> implements ValueChangeHandler<T> {
	@Override
	public void onValueChange(ValueChangeEvent<T> event) {
		Info.display("Zmieniono", "Zmieniono wartość na : " + event.getValue() == null ? "" : event.getValue() + "");
	}
}
