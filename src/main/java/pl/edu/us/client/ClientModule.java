package pl.edu.us.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import pl.edu.us.client.admin.AdminPresenter;
import pl.edu.us.client.admin.AdminView;
import pl.edu.us.client.details.DetailPresenter;
import pl.edu.us.client.details.DetailView;
import pl.edu.us.client.kartoteki.pracownik.PracownikPresenter;
import pl.edu.us.client.kartoteki.pracownik.PracownikView;
import pl.edu.us.client.kartoteki.student.oceny.OcenyStudentaPresenter;
import pl.edu.us.client.kartoteki.student.oceny.OcenyStudentaView;
import pl.edu.us.client.main.ContentPagePresenter;
import pl.edu.us.client.main.ContentPageView;
import pl.edu.us.client.main.MainPagePresenter;
import pl.edu.us.client.main.MainPageView;
import pl.edu.us.client.main.MenuBuilder;
import pl.edu.us.client.main.MenuPresenter;
import pl.edu.us.client.main.MenuView;
import pl.edu.us.client.przedmioty.PrzedmiotyPresenter;
import pl.edu.us.client.przedmioty.PrzedmiotyView;
import pl.edu.us.client.symulacja.przychodykoszty.PrzychodyKosztyPresenter;
import pl.edu.us.client.symulacja.przychodykoszty.PrzychodyKosztyView;
import pl.edu.us.client.symulacja.przychodykosztykierunki.PKKierunkiPresenter;
import pl.edu.us.client.symulacja.przychodykosztykierunki.PKKierunkiView;
import pl.edu.us.client.symulacja.przychodykosztyroczne.PKRocznePresenter;
import pl.edu.us.client.symulacja.przychodykosztyroczne.PKRoczneView;
import pl.edu.us.client.uzytkownik.daneuzytkownika.DaneUzytkownikaPresenter;
import pl.edu.us.client.uzytkownik.daneuzytkownika.DaneUzytkownikaView;
import pl.edu.us.client.uzytkownik.hasloprzypomnienie.PassRemindPresenter;
import pl.edu.us.client.uzytkownik.hasloprzypomnienie.PassRemindView;
import pl.edu.us.client.uzytkownik.haslozmiana.PassChangePresenter;
import pl.edu.us.client.uzytkownik.haslozmiana.PassChangeView;
import pl.edu.us.client.uzytkownik.kartoteka.KartotekaUzytkownikowPresenter;
import pl.edu.us.client.uzytkownik.kartoteka.KartotekaUzytkownikowView;
import pl.edu.us.client.uzytkownik.mojeprzedmioty.MojePrzedmiotyPresenter;
import pl.edu.us.client.uzytkownik.mojeprzedmioty.MojePrzedmiotyView;
import pl.edu.us.client.uzytkownik.rejestracja.RejestracjaPresenter;
import pl.edu.us.client.uzytkownik.rejestracja.RejestracjaView;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new DefaultModule(AppPlaceManager.class));

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);

        bind(MenuBuilder.class).in(Singleton.class);
        // Presenter główny
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class,
            MainPagePresenter.MyProxy.class);

        bindPresenter(PassRemindPresenter.class, PassRemindPresenter.MyView.class, PassRemindView.class, PassRemindPresenter.MyProxy.class);
        bindPresenter(PassChangePresenter.class, PassChangePresenter.MyView.class, PassChangeView.class, PassChangePresenter.MyProxy.class);

        bindPresenter(RejestracjaPresenter.class, RejestracjaPresenter.MyView.class,
            RejestracjaView.class, RejestracjaPresenter.MyProxy.class);

        bindPresenter(DaneUzytkownikaPresenter.class, DaneUzytkownikaPresenter.MyView.class,
            DaneUzytkownikaView.class, DaneUzytkownikaPresenter.MyProxy.class);

        bindSingletonPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class, MenuView.class);

        bindPresenter(ContentPagePresenter.class, ContentPagePresenter.MyView.class, ContentPageView.class,
            ContentPagePresenter.MyProxy.class);

        bindPresenter(DetailPresenter.class, DetailPresenter.MyView.class, DetailView.class,
            DetailPresenter.MyProxy.class);

        bindPresenter(AdminPresenter.class, AdminPresenter.MyView.class, AdminView.class, AdminPresenter.MyProxy.class);
        // Studenci
        bindPresenter(KartotekaUzytkownikowPresenter.class, KartotekaUzytkownikowPresenter.MyView.class,
            KartotekaUzytkownikowView.class, KartotekaUzytkownikowPresenter.MyProxy.class);
        bindPresenter(OcenyStudentaPresenter.class, OcenyStudentaPresenter.MyView.class, OcenyStudentaView.class,
            OcenyStudentaPresenter.MyProxy.class);
        bindPresenter(MojePrzedmiotyPresenter.class, MojePrzedmiotyPresenter.MyView.class,
            MojePrzedmiotyView.class, MojePrzedmiotyPresenter.MyProxy.class);
        // Studia
        bindPresenter(PrzedmiotyPresenter.class, PrzedmiotyPresenter.MyView.class, PrzedmiotyView.class,
            PrzedmiotyPresenter.MyProxy.class);

        bindPresenter(PracownikPresenter.class, PracownikPresenter.MyView.class, PracownikView.class,
            PracownikPresenter.MyProxy.class);
        // Symulacja
        bindPresenter(PrzychodyKosztyPresenter.class, PrzychodyKosztyPresenter.MyView.class, PrzychodyKosztyView.class,
            PrzychodyKosztyPresenter.MyProxy.class);
        bindPresenter(PKRocznePresenter.class, PKRocznePresenter.MyView.class, PKRoczneView.class,
            PKRocznePresenter.MyProxy.class);
        bindPresenter(PKKierunkiPresenter.class, PKKierunkiPresenter.MyView.class, PKKierunkiView.class,
            PKKierunkiPresenter.MyProxy.class);
    }
}
