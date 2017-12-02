package pl.edu.us.client.registeruser;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.kartoteki.student.kartoteka.ErrorHandler;
import pl.edu.us.client.main.handlers.FieldHandler;
import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;
import pl.edu.us.shared.model.User;

public class RejestracjaPanel extends FramedPanel implements /* IsWidget, */Editor<User>, FieldListener {

    private final RejestracjaModel model;

    TextField imie, nazwisko, ulica, kodPocztowy, miasto, email, login, nrDomu, nrMieszkania;
    PasswordField password, passwordRepeat;

    DateField dataUrodzenia;
    ComboBox<Plec> plec;
    ComboBox<Rola> rola;

    private TextButton powrot;

    private TextButton zarejestruj;

    FieldHandler<String> listener = new FieldHandler<String>(this);

    @Inject
    public RejestracjaPanel(RejestracjaModel model) {
        super();
        this.model = model;
        setBorders(true);
        setHeadingText("Rejestracja");
        createForm1();
//        setSize("700px", "700px");
//        driver.initialize(this);
    }

    public void setSaveEnabled(boolean enabled) {
        zarejestruj.setEnabled(enabled);
    }

    public void clearForm() {
        imie.clear();
        nazwisko.clear();
        plec.clear();
        rola.clear();
        dataUrodzenia.clear();
        ulica.clear();
        miasto.clear();
        kodPocztowy.clear();
        nrDomu.clear();
        nrMieszkania.clear();
        email.clear();
        login.clear();
        password.clear();
        passwordRepeat.clear();
    }

    private void createForm1() {
        imie = new TextField();
        imie.setAllowBlank(false);
        imie.setEmptyText("Prosze wpisać imię...");
        imie.addValueChangeHandler(listener);
        imie.setId("Imię");

        nazwisko = new TextField();
        nazwisko.setAllowBlank(false);
        nazwisko.setEmptyText("Prosze wpisać nazwisko...");
        nazwisko.addValueChangeHandler(listener);

        plec = new ComboBox<Plec>(model.getStorePlec(), model.getPlecProp().nazwa());
        plec.setAllowBlank(false);
        plec.setForceSelection(true);
        plec.setTriggerAction(TriggerAction.ALL);
        plec.addValueChangeHandler(new FieldHandler<Plec>(this));
        plec.setEditable(false);
        rola = new ComboBox<Rola>(model.getStoreRola(), model.getRolaProp().nazwa());
        rola.setAllowBlank(false);
        rola.setForceSelection(true);
        rola.setTriggerAction(TriggerAction.ALL);
        rola.addValueChangeHandler(new FieldHandler<Rola>(this));
        rola.setEditable(false);
        dataUrodzenia = new DateField();
        // dataUrodzenia.addValidator(new
        // MinDateValidator(DateUtils.addYears(new Date(), -18)));
        dataUrodzenia.addParseErrorHandler(new ErrorHandler());
        dataUrodzenia.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                String v = event.getValue() == null ? "" : DateTimeFormat
                    .getFormat(PredefinedFormat.DATE_MEDIUM).format(event.getValue());
                Info.display("Data urodzenia", "Wybrano " + v);
            }
        });
        ulica = new TextField();
        ulica.setAllowBlank(false);
        ulica.addValueChangeHandler(new FieldHandler<String>(this));

        nrDomu = new TextField();
        nrDomu.setAllowBlank(false);
        nrDomu.setWidth(50);
        nrDomu.addValueChangeHandler(new FieldHandler<String>(this));

        nrMieszkania = new TextField();
        nrMieszkania.setAllowBlank(false);
        nrMieszkania.setWidth(50);
        nrMieszkania.addValueChangeHandler(new FieldHandler<String>(this));

        HorizontalPanel hpAdres = new HorizontalPanel();
        hpAdres.add(new FieldLabel(ulica, "Ulica"));
        FieldLabel s1 = new FieldLabel();
        s1.setWidth(30);
        hpAdres.add(s1);
        hpAdres.add(new FieldLabel(nrDomu, "Nr domu"));
        FieldLabel s2 = new FieldLabel();
        s2.setWidth(30);
        hpAdres.add(s2);
        hpAdres.add(new FieldLabel(nrMieszkania, "Nr mieszkania"));

        kodPocztowy = new TextField();
        kodPocztowy.setAllowBlank(false);
        kodPocztowy.addParseErrorHandler(new ErrorHandler());
        kodPocztowy.setWidth(60);

        miasto = new TextField();
        miasto.setAllowBlank(false);
        miasto.addValueChangeHandler(new FieldHandler<String>(this));

        HorizontalPanel hpMiasto = new HorizontalPanel();
        hpMiasto.add(miasto);
        FieldLabel s3 = new FieldLabel();
        s3.setWidth(30);
        hpMiasto.add(s3);
        hpMiasto.add(new FieldLabel(kodPocztowy, "Kod pocztowy"));

        email = new TextField();
        email.setAllowBlank(false);
        email.addValidator(new Validator<String>() {

            @Override
            public List<EditorError> validate(Editor<String> editor, String value) {
                Boolean b = value.matches(
                    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                if (!b)
                    return Collections.<EditorError> singletonList(new DefaultEditorError(editor, "Błędny email", value));
                return null;
            }
        });
        email.addValueChangeHandler(new FieldHandler<String>(this));

        login = new TextField();
        login.setAllowBlank(false);
        login.addValueChangeHandler(new FieldHandler<String>(this));

        password = new PasswordField();
        password.setAllowBlank(false);
        password.addValueChangeHandler(new FieldHandler<String>(this));

        passwordRepeat = new PasswordField();
        passwordRepeat.setAllowBlank(false);
        passwordRepeat.addValueChangeHandler(new FieldHandler<String>(this));

        password.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (password.getValue() == null) {
                    passwordRepeat.clear();
                    passwordRepeat.setEnabled(false);
                } else {
                    passwordRepeat.setEnabled(true);
                }

            }
        });

//        HorizontalPanel edycjaPanel = new HorizontalPanel();
        powrot = new TextButton("Powrót");
        powrot.setWidth(100);

        zarejestruj = new TextButton("Zarejestruj");
        zarejestruj.setEnabled(false);
        zarejestruj.setWidth(100);

//        edycjaPanel.add(powrot);
//        edycjaPanel.add(zatwierdz);
        zarejestruj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {

            }
        });
        getButtonBar().add(powrot);
        getButtonBar().add(zarejestruj);

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(imie, "Imię"));
        vlc.add(new FieldLabel(nazwisko, "Nazwisko"));
        vlc.add(new FieldLabel(plec, "Płeć"));
        vlc.add(new FieldLabel(dataUrodzenia, "Data urodzenia"));
        vlc.add(new FieldLabel(null, "Adres"));
        vlc.add(hpAdres);
        vlc.add(new FieldLabel(hpMiasto, "Miasto"));
        vlc.add(new FieldLabel(email, "Email"));
        vlc.add(new FieldLabel(login, "Login"));
        vlc.add(new FieldLabel(password, "Hasło"));
        vlc.add(new FieldLabel(passwordRepeat, "Powtórz hasło"));
        vlc.add(new FieldLabel(rola, "Rola"));
        vlc.setId("formPanel rejestracja");
//        vlc.add(edycjaPanel);
        add(vlc);

    }

    public TextButton getZarejestruj() {
        return zarejestruj;
    }

    public TextButton getPowrot() {
        return powrot;
    }

    @Override
    public void onChangeFieldValue() {
        zarejestruj.setEnabled(fieldIsValid(imie)
            && fieldIsValid(nazwisko)
            && plec.getValue() != null
            && fieldIsValid(dataUrodzenia)
            && fieldIsValid(miasto)
            && fieldIsValid(email)
            && fieldIsValid(email)
            && fieldIsValid(login)
            && fieldIsValid(password)
            && fieldIsValid(passwordRepeat)
            && rola.getValue() != null
            && fieldIsValid(kodPocztowy)
            && (nrDomu.getValue() == null || fieldIsValid(nrDomu))
            && (nrMieszkania.getValue() == null || fieldIsValid(nrMieszkania)));
    }

    private boolean fieldIsValid(Field f) {
        return f.getValue() != null && f.isValid();
    }

    public TextField getImie() {
        return imie;
    }

    public TextField getNazwisko() {
        return nazwisko;
    }

    public ComboBox<Plec> getPlec() {
        return plec;
    }

    public DateField getDataUrodzenia() {
        return dataUrodzenia;
    }

    public TextField getUlica() {
        return ulica;
    }

    public TextField getNrDomu() {
        return nrDomu;
    }

    public TextField getNrMieszkania() {
        return nrMieszkania;
    }

    public TextField getMiasto() {
        return miasto;
    }

    public TextField getKodPocztowy() {
        return kodPocztowy;
    }

    public TextField getEmail() {
        return email;
    }

    public TextField getLogin() {
        return login;
    }

    public PasswordField getPassword() {
        return password;
    }

    public ComboBox<Rola> getRola() {
        return rola;
    }
}
