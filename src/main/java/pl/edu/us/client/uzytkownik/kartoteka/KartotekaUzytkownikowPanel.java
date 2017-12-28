package pl.edu.us.client.uzytkownik.kartoteka;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import pl.edu.us.client.kartoteki.student.kartoteka.ErrorHandler;
import pl.edu.us.client.kartoteki.student.kartoteka.ui.KierunkiStudentaDialog;
import pl.edu.us.client.main.handlers.FieldHandler;
import pl.edu.us.client.main.handlers.FieldHandler.FieldListener;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.model.old.Kierunek;
import pl.edu.us.shared.model.old.Student;

public class KartotekaUzytkownikowPanel extends FramedPanel implements /* IsWidget, *//*Editor<Student>,*/ FieldListener {

    private final KartotekaUzytkownikowModel model;
    private KierunkiStudentaDialog<Kierunek> kierunkiDialog;
    TextField imie, nazwisko, ulica, kodPocztowy, miasto, email, login,    nrDomu, nrMieszkania;
    PasswordField password;
    DateField dataUrodzenia;
    ComboBox<Plec> plec;

    private TextButton nowy;
    private TextButton usun;
    private TextButton zatwierdz;
    private TextButton przypnijKierunki;

//    interface ListDriver extends SimpleBeanEditorDriver<Student, KartotekaUzytkownikowPanel> {
//    }

//    private ListDriver driver = GWT.create(ListDriver.class);

    @Inject
    public KartotekaUzytkownikowPanel(KartotekaUzytkownikowModel model) {
        super();
        this.model = model;
        setBorders(true);
        setHeadingText("Kartoteka studenta");
        createForm1();
//        driver.initialize(this);
    }

    public void setSaveEnabled(boolean enabled) {
        // save.setEnabled(enabled);
        przypnijKierunki.setEnabled(enabled);
        // usun.setEnabled(enabled);
        zatwierdz.setEnabled(enabled);
        imie.setEnabled(enabled);
        nazwisko.setEnabled(enabled);
        plec.setEnabled(enabled);
        dataUrodzenia.setEnabled(enabled);
        ulica.setEnabled(enabled);
        miasto.setEnabled(enabled);
        kodPocztowy.setEnabled(enabled);
        nrDomu.setEnabled(enabled);
        nrMieszkania.setEnabled(enabled);
        email.setEnabled(enabled);
        login.setEnabled(enabled);
        password.setEnabled(enabled);
        if (!enabled) {
            imie.clear();
            nazwisko.clear();
            plec.clear();
            dataUrodzenia.clear();
            ulica.clear();
            miasto.clear();
            kodPocztowy.clear();
            nrDomu.clear();
            nrMieszkania.clear();
            email.clear();
            login.clear();
            password.clear();
        }
    }

    private void createForm1() {
       
        imie = new TextField();
        imie.setAllowBlank(false);
        imie.setEmptyText("Prosze wpisać imię...");
        imie.addValueChangeHandler(new FieldHandler<String>(this));

        nazwisko = new TextField();
        nazwisko.setAllowBlank(false);
        nazwisko.setEmptyText("Prosze wpisać nazwisko...");
        nazwisko.addValueChangeHandler(new FieldHandler<String>(this));

        plec = new ComboBox<Plec>(model.getStorePlec(), model.getPlecProp().nazwa());
        plec.setAllowBlank(true);
        plec.setForceSelection(true);
        plec.setTriggerAction(TriggerAction.ALL);
        plec.addValueChangeHandler(new FieldHandler<Plec>(this));

        dataUrodzenia = new DateField();
        // dataUrodzenia.addValidator(new
        // MinDateValidator(DateUtils.addYears(new Date(), -18)));
        dataUrodzenia.addParseErrorHandler(new ErrorHandler());
        dataUrodzenia.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                String v = event.getValue() == null ? "nothing" : DateTimeFormat
                    .getFormat(PredefinedFormat.DATE_MEDIUM).format(event.getValue());
                Info.display("Selected", "You selected " + v);
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

        login = new TextField();
        login.setAllowBlank(false);
        password = new PasswordField();
        password.setAllowBlank(false);

        HorizontalPanel edycjaPanel = new HorizontalPanel();
        nowy = new TextButton("Nowy");
        nowy.setWidth(100);
        // usun = new TextButton("Usuń");
        // usun.setEnabled(false);
        // usun.setWidth(100);
        zatwierdz = new TextButton("Zatwierdź");
        zatwierdz.setEnabled(false);
        zatwierdz.setWidth(100);
        przypnijKierunki = new TextButton("Przypnij Kierunki");
        przypnijKierunki.setEnabled(false);
        przypnijKierunki.setWidth(100);
        edycjaPanel.add(nowy);
        // edycjaPanel.add(usun);
        edycjaPanel.add(zatwierdz);
        edycjaPanel.add(przypnijKierunki);
        nowy.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                Student s1 = new Student();
                int size = 0;
//                for (Student s : model.getStudents().getAll()) {
//                    if (s.getId() != null && s.getId() > 0)
//                        size = s.getId();
//                }
//                size++;
//                s1.setId(size);
                model.getStudents().add(s1);
            }
        });
        // usun.addSelectHandler(new SelectHandler() {
        //
        // @Override
        // public void onSelect(SelectEvent event) {
        // try {
        // Student edited = driver.flush();
        // if (edited == null)
        // return;
        // model.getStudents().remove(edited);
        // model.getDoUsunieccia().add(edited);
        // setSaveEnabled(false);
        // } catch (IllegalStateException e) {
        // return;
        // }
        // }
        // });
        zatwierdz.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
//                try {
//                    Student edited = driver.flush();
//                    if (edited == null)
//                        return;
//                    if (!driver.hasErrors()) {
//                        model.getStudents().update(edited);
//                    }
//                } catch (IllegalStateException e) {
//                    return;
//                }
            }
        });
        przypnijKierunki.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                model.zaznaczKerunki();
                kierunkiDialog = new KierunkiStudentaDialog<Kierunek>(model.getKierunki(), model.getKieruProp(), model);
                kierunkiDialog.show();
            }
        });
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(new FieldLabel(imie, "Imie"));
        vlc.add(new FieldLabel(nazwisko, "Nazwisko"));
        vlc.add(new FieldLabel(plec, "Płeć"));
        vlc.add(new FieldLabel(dataUrodzenia, "Data urodzenia"));
        vlc.add(new FieldLabel(null, "Adres"));
        vlc.add(hpAdres);
        vlc.add(new FieldLabel(hpMiasto, "Miasto"));
        vlc.add(new FieldLabel(email, "Email"));
        vlc.add(new FieldLabel(login, "Login"));
        vlc.add(new FieldLabel(password, "Password"));
        vlc.add(edycjaPanel);
        add(vlc);

    }

//    public ListDriver getDriver() {
//        return driver;
//    }

    @Override
    public void onChangeFieldValue() {
        // TODO Auto-generated method stub

    }
}
