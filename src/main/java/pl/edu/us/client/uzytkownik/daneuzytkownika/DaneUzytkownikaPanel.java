package pl.edu.us.client.uzytkownik.daneuzytkownika;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

import pl.edu.us.client.main.handlers.DateChangeHandler;
import pl.edu.us.client.main.handlers.ErrorHandler;
import pl.edu.us.client.main.handlers.FieldHandler;
import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;
import pl.edu.us.client.main.handlers.PassChangeHander;
import pl.edu.us.client.main.validators.EmailValidator;
import pl.edu.us.shared.commons.AppStrings;
import pl.edu.us.shared.dto.UserDTO;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.Rola;
import pl.edu.us.shared.model.User;

public class DaneUzytkownikaPanel extends CenterLayoutContainer implements /* IsWidget, */Editor<UserDTO>, FieldListener {

    private final DaneUzytkownikaModel model;
    private TextField imie, nazwisko, ulica, kodPocztowy, miasto, email, login, nrDomu, nrMieszkania;
    private PasswordField password, passwordRepeat;
    private DateField dataUrodzenia;
    private ComboBox<Plec> plec;
    private ComboBox<Rola> rola;

    FieldHandler<String> listener = new FieldHandler<String>(this);
    DaneUzytkownikaMainPanel mainPanel;

    @Inject
    public DaneUzytkownikaPanel(DaneUzytkownikaModel model, DaneUzytkownikaMainPanel daneUzytkownikaMainPanel) {
        super();
        this.model = model;
        this.mainPanel = daneUzytkownikaMainPanel;
//        setBorders(true);
//        setHeadingText("Moje dane");
        createForm1();

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
        imie.setEmptyText(AppStrings.wpisz_imie);
        imie.setId(AppStrings.imie);

        nazwisko = new TextField();
        nazwisko.setAllowBlank(false);
        nazwisko.setEmptyText(AppStrings.wpisz_nazwisko);
        nazwisko.setId("Nazwisko");

        plec = new ComboBox<Plec>(model.getStorePlec(), model.getPlecProp().nazwa());
        plec.setAllowBlank(false);
        plec.setForceSelection(true);
        plec.setTriggerAction(TriggerAction.ALL);
        plec.setEditable(false);
        plec.setEmptyText(AppStrings.wybierz_plec);
        plec.setId(AppStrings.plec);

        rola = new ComboBox<Rola>(model.getStoreRola(), model.getRolaProp().nazwa());
        rola.setAllowBlank(false);
        rola.setForceSelection(true);
        rola.setTriggerAction(TriggerAction.ALL);
        rola.setReadOnly(true);
        rola.setEmptyText(AppStrings.wybierz_role);
        rola.setId("Rola");

        dataUrodzenia = new DateField();
        // dataUrodzenia.addValidator(new
        // MinDateValidator(DateUtils.addYears(new Date(), -18)));
        dataUrodzenia.setId("Data urodzenia");

        ulica = new TextField();
        ulica.setAllowBlank(false);
        ulica.setId("Ulica");

        nrDomu = new TextField();
        nrDomu.setAllowBlank(false);
        nrDomu.setWidth(50);
        nrDomu.addValueChangeHandler(new FieldHandler<String>(this));
        nrDomu.setId("Nr domu");

        nrMieszkania = new TextField();
        nrMieszkania.setAllowBlank(false);
        nrMieszkania.setWidth(50);
        nrMieszkania.addValueChangeHandler(new FieldHandler<String>(this));
        nrMieszkania.setId("Nr mieszkania");

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
        kodPocztowy.setId("Kod pocztowy");

        miasto = new TextField();
        miasto.setAllowBlank(false);
        miasto.setId("Miasto");

        HorizontalPanel hpMiasto = new HorizontalPanel();
        hpMiasto.add(miasto);
        FieldLabel s3 = new FieldLabel();
        s3.setWidth(30);
        hpMiasto.add(s3);
        hpMiasto.add(new FieldLabel(kodPocztowy, "Kod pocztowy"));

        email = new TextField();
        email.setAllowBlank(false);
        email.addValidator(new EmailValidator());
        email.setWidth(250);
        email.setId("E-mail");

        login = new TextField();
        login.setReadOnly(true);
        login.setId("Login");

        password = new PasswordField();
        password.setAllowBlank(false);
        password.setId("Hasło");
        passwordRepeat = new PasswordField();
        passwordRepeat.setAllowBlank(false);
        passwordRepeat.setId("Powtórz hasło");

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(imie, "Imię"));
        vlc.add(new FieldLabel(nazwisko, "Nazwisko"));
        vlc.add(new FieldLabel(plec, "Płeć"));
        vlc.add(new FieldLabel(dataUrodzenia, "Data urodzenia"));
        vlc.add(new FieldLabel(null, "Adres"));
        vlc.add(hpAdres);
        vlc.add(new FieldLabel(hpMiasto, "Miasto"));
        vlc.add(new FieldLabel(email, "E-mail"));
        vlc.add(new FieldLabel(login, "Login"));
        vlc.add(new FieldLabel(password, "Hasło"));
        vlc.add(new FieldLabel(passwordRepeat, "Powtórz hasło"));
        vlc.add(new FieldLabel(rola, "Rola"));
        add(vlc);

        addListeners();
    }

    private boolean fieldIsValid(Field f) {
        return f.getValue() != null && f.isValid();
    }

    public void bind() {
        initialState();
        UserDTO u = model.getUser();
        if (u != null) {
            login.setValue(u.getLogin());
            imie.setValue(u.getImie());
            nazwisko.setValue(u.getNazwisko());
            dataUrodzenia.setValue(u.getDataUrodzenia());
            ulica.setValue(u.getUlica());
            nrDomu.setValue(u.getNrDomu());
            nrMieszkania.setValue(u.getNrMieszkania());
            miasto.setValue(u.getMiasto());
            kodPocztowy.setValue(u.getKodPocztowy());
            plec.setValue(u.getPlec());
            email.setValue(u.getEmail());
            rola.setValue(u.getRola());
            password.setValue(u.getPassword());
            passwordRepeat.setValue(u.getPassword());
        } else {
            clearForm();
        }
    }

    public void initialState() {
        mainPanel.getZapisz().setEnabled(false);
        mainPanel.getAnuluj().setEnabled(false);
    }

    private void addListeners() {
        imie.addValueChangeHandler(listener);
        nazwisko.addValueChangeHandler(listener);
        plec.addValueChangeHandler(new FieldHandler<Plec>(this));
        rola.addValueChangeHandler(new FieldHandler<Rola>(this));
        dataUrodzenia.addParseErrorHandler(new ErrorHandler());
        dataUrodzenia.addValueChangeHandler(new DateChangeHandler());
        ulica.addValueChangeHandler(listener);
        miasto.addValueChangeHandler(listener);
        email.addValueChangeHandler(listener);
        login.addValueChangeHandler(listener);
        PassChangeHander<String> passHandler = new PassChangeHander<>(this, password, passwordRepeat);
        password.addValueChangeHandler(passHandler);
        passwordRepeat.addValueChangeHandler(passHandler);
    }

    @Override
    public void onChangeFieldValue() {
        mainPanel.getAnuluj().setEnabled(true);
        mainPanel.getZapisz().setEnabled(fieldIsValid(imie)
            && fieldIsValid(nazwisko)
            && plec.getValue() != null
            && fieldIsValid(dataUrodzenia)
            && fieldIsValid(miasto)
            && fieldIsValid(email)
            && fieldIsValid(ulica)
            && fieldIsValid(login)
            && fieldIsValid(password)
            && fieldIsValid(passwordRepeat)
            && rola.getValue() != null
            && fieldIsValid(kodPocztowy)
            && (nrDomu.getValue() == null || fieldIsValid(nrDomu))
            && (nrMieszkania.getValue() == null || fieldIsValid(nrMieszkania)));
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

    public PasswordField getPassword() {
        return password;
    }

}
