package pl.edu.us.client.uzytkownik.haslozmiana;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;

import pl.edu.us.client.main.handlers.FieldHandler;
import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;
import pl.edu.us.client.main.handlers.PassChangeHander;

public class PassChangePanel extends FramedPanel implements FieldListener {

    private PasswordField oldPassword, newPassword, newPasswordRepeat;

    private TextButton powrot;

    private TextButton zmianaHasla;

    FieldHandler<String> listener = new FieldHandler<String>(this);

    @Inject
    public PassChangePanel() {
        super();
        setBorders(true);
        setHeadingText("Zmiana hasła");
        createForm();
    }

    public void setSaveEnabled(boolean enabled) {
        zmianaHasla.setEnabled(enabled);
    }

    public void clearForm() {
        oldPassword.clear();
        newPassword.clear();
        newPasswordRepeat.clear();
    }

    private void createForm() {

        oldPassword = new PasswordField();
        oldPassword.setAllowBlank(false);
        oldPassword.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                onChangeFieldValue();
            }
        });

        newPassword = new PasswordField();
        newPassword.setAllowBlank(false);

        newPasswordRepeat = new PasswordField();
        newPasswordRepeat.setAllowBlank(false);

        PassChangeHander<String> passHandler = new PassChangeHander<>(this, newPassword, newPasswordRepeat);
        newPasswordRepeat.addValueChangeHandler(passHandler);
        newPassword.addValueChangeHandler(passHandler);

        powrot = new TextButton("Powrót");
        powrot.setWidth(100);

        zmianaHasla = new TextButton("Zmień hasło");
        zmianaHasla.setEnabled(false);
        zmianaHasla.setWidth(100);
        zmianaHasla.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {

            }
        });
        getButtonBar().add(powrot);
        getButtonBar().add(zmianaHasla);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(oldPassword, "Stare hasło"));
        vlc.add(new FieldLabel(newPassword, "Hasło"));
        vlc.add(new FieldLabel(newPasswordRepeat, "Powtórz hasło"));
        vlc.setId("formPanel rejestracja");
        add(vlc);

    }

    public TextButton getZmiana() {
        return zmianaHasla;
    }

    public TextButton getPowrot() {
        return powrot;
    }

    @Override
    public void onChangeFieldValue() {
        zmianaHasla.setEnabled(fieldIsValid(oldPassword)
            && fieldIsValid(newPassword)
            && fieldIsValid(newPasswordRepeat));
    }

    private boolean fieldIsValid(Field f) {
        return f.getValue() != null && f.isValid();
    }

    public PasswordField getOldPassword() {
        return oldPassword;
    }

    public PasswordField getPassword() {
        return newPassword;
    }

}
