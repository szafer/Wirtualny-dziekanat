package pl.edu.us.client.main.handlers;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;

public class PassChangeHander<T> implements ValueChangeHandler<T> {

    private FieldListener fl;
    private PasswordField password, passwordRepeat;

    public PassChangeHander(FieldListener fl, PasswordField password, PasswordField passwordRepeat) {
        super();
        this.fl = fl;
        this.passwordRepeat = passwordRepeat;
        this.password = password;
    }

    @Override
    public void onValueChange(ValueChangeEvent<T> event) {
        Field f = (Field) event.getSource();
        if (f == password) {
            if (password.getValue() == null || password.getValue().isEmpty()) {
                passwordRepeat.clear();
                passwordRepeat.setEnabled(false);
            } else {
                passwordRepeat.setEnabled(true);
            }
        } else {

        }
        if (password.getValue() != passwordRepeat.getValue()) {
            String s = (f.getId() == null || f.getId().contains("x-auto") ? "Zmieniono hasło" : f.getId());
            Info.display(s, "Hasła są niezgodne");

        } else {
            String s = (f.getId() == null || f.getId().contains("x-auto") ? "Zmieniono hasło" : f.getId());
            Info.display(s, "Hasła są zgodne");
        }
        fl.onChangeFieldValue();
    }
}
