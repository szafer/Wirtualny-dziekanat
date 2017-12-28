package pl.edu.us.client.main.handlers;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.widget.core.client.info.Info;

public class DateChangeHandler implements ValueChangeHandler<Date> {
    @Override
    public void onValueChange(ValueChangeEvent<Date> event) {
        String v = event.getValue() == null ? "" : DateTimeFormat
            .getFormat(PredefinedFormat.DATE_MEDIUM).format(event.getValue());
        Info.display("Data urodzenia", "Wybrano " + v);
    }
}
