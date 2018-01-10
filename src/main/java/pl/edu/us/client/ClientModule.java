package pl.edu.us.client;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import pl.edu.us.client.details.DetailPresenter;
import pl.edu.us.client.details.DetailView;
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
import pl.edu.us.client.uzytkownik.kartoteka.UzytkownicyPresenter;
import pl.edu.us.client.uzytkownik.kartoteka.UzytkownicyView;
import pl.edu.us.client.uzytkownik.mojeprzedmioty.MojePrzedmiotyPresenter;
import pl.edu.us.client.uzytkownik.mojeprzedmioty.MojePrzedmiotyView;
import pl.edu.us.client.uzytkownik.mojewnioski.MojeWnioskiPresenter;
import pl.edu.us.client.uzytkownik.mojewnioski.MojeWnioskiView;
import pl.edu.us.client.uzytkownik.rejestracja.RejestracjaPresenter;
import pl.edu.us.client.uzytkownik.rejestracja.RejestracjaView;
import pl.edu.us.client.wiadomosci.WiadomosciPresenter;
import pl.edu.us.client.wiadomosci.WiadomosciView;
import pl.edu.us.client.wnioski.definicja.WnioskiPresenter;
import pl.edu.us.client.wnioski.definicja.WnioskiView;
import pl.edu.us.client.wnioski.kartoteka.WnioskiKartPresenter;
import pl.edu.us.client.wnioski.kartoteka.WnioskiKartView;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
//        install(new DefaultModule());
        install(new RpcDispatchAsyncModule());

//        install(new DefaultModule(AppPlaceManager.class));

        // Constants
//        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);
//        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.main);
//        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.main);
        install(new DefaultModule.Builder()
            .defaultPlace(NameTokens.main)
            .errorPlace(NameTokens.main)
            .unauthorizedPlace(NameTokens.main).build());
        
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

        // Uzytkownicy
        bindPresenter(UzytkownicyPresenter.class, UzytkownicyPresenter.MyView.class,
            UzytkownicyView.class, UzytkownicyPresenter.MyProxy.class);

        bindPresenter(MojePrzedmiotyPresenter.class, MojePrzedmiotyPresenter.MyView.class,
            MojePrzedmiotyView.class, MojePrzedmiotyPresenter.MyProxy.class);
        bindPresenter(MojeWnioskiPresenter.class, MojeWnioskiPresenter.MyView.class, MojeWnioskiView.class,
            MojeWnioskiPresenter.MyProxy.class);

        // Przedmioty
        bindPresenter(PrzedmiotyPresenter.class, PrzedmiotyPresenter.MyView.class, PrzedmiotyView.class,
            PrzedmiotyPresenter.MyProxy.class);

        //Wnioski
        bindPresenter(WnioskiPresenter.class, WnioskiPresenter.MyView.class, WnioskiView.class, WnioskiPresenter.MyProxy.class);
        bindPresenter(WnioskiKartPresenter.class, WnioskiKartPresenter.MyView.class, WnioskiKartView.class, WnioskiKartPresenter.MyProxy.class);

        //Wiadomosci
        bindPresenter(WiadomosciPresenter.class, WiadomosciPresenter.MyView.class, WiadomosciView.class, WiadomosciPresenter.MyProxy.class);

        // Symulacja
        bindPresenter(PrzychodyKosztyPresenter.class, PrzychodyKosztyPresenter.MyView.class, PrzychodyKosztyView.class,
            PrzychodyKosztyPresenter.MyProxy.class);
        bindPresenter(PKRocznePresenter.class, PKRocznePresenter.MyView.class, PKRoczneView.class,
            PKRocznePresenter.MyProxy.class);
        bindPresenter(PKKierunkiPresenter.class, PKKierunkiPresenter.MyView.class, PKKierunkiView.class,
            PKKierunkiPresenter.MyProxy.class);
    }
}
