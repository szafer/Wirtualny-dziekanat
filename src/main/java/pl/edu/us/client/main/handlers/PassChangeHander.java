package pl.edu.us.client.main.handlers;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;

public class PassChangeHander<T> implements ValueChangeHandler<T> {

//    public interface FieldListener {
//        void onChangeFieldValue();
//    }

    FieldListener fl;

    public PassChangeHander(FieldListener fl) {
        super();
        this.fl = fl;

    }

    @Override
    public void onValueChange(ValueChangeEvent<T> event) {
//        Field f = (Field) event.getSource();
//        String s = (f.getId() == null || f.getId().contains("x-auto") ? "Zmieniono" : f.getId());
//        Info.display(s, "Zmieniono wartość na : " + event.getValue() == null ? "" : event.getValue() + "");
        fl.onChangeFieldValue();
    }
}
