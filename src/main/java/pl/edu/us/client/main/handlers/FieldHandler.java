package pl.edu.us.client.main.handlers;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.info.Info;

public class FieldHandler<T> implements ValueChangeHandler<T> {

    public interface FieldListener {
        void onChangeFieldValue();
    }

    FieldListener fl;

    public FieldHandler(FieldListener fl) {
        super();
        this.fl = fl;

    }

    @Override
    public void onValueChange(ValueChangeEvent<T> event) {
        Field f = (Field) event.getSource();
        String s = (f.getId() == null || f.getId().contains("x-auto") ? "Zmieniono" : f.getId());
        Info.display(s, "Zmieniono wartość na : " + event.getValue() == null ? "" : event.getValue() + "");
        fl.onChangeFieldValue();
    }
}
