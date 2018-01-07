package pl.edu.us.client.main;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import pl.edu.us.client.NameTokens;
import pl.edu.us.shared.enums.Rola;

public class MenuBuilder {

    private Menu daneOsoboweMenu;//pokazuje się zawsze
    private Menu przedmiotyMenu; //stdent lub pracownik
    private Menu adminMenu;//administrator
    private Menu zamknijMenu;
    private Menu raportyMenu;//pokazuje się zawsze
    private MenuBarItem zamknijBarItem;//pokazuje się zawsze

    private Menu symulacjaMenu;//wycofane
    private final PlaceManager placeManager;
    private Rola rola;

    @Inject
    MenuBuilder(final PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    public MenuBar build() {
        String r = Cookies.getCookie("userRole");
        rola = Rola.values()[Integer.valueOf(r)];

        MenuBar menuBar = new MenuBar();
        menuBar.add(buildDaneOsoboweMenu());
        if (rola != null) {
            if (rola == Rola.ADMIN) {
                menuBar.add(buildAdminMenu());
            } else if (rola == Rola.NAUCZYCIEL) {
                menuBar.add(buildPracownikMenu());
            } else {
                menuBar.add(buildStudentMenu());
            }
        }
        menuBar.add(buildWydrukiMenu());
        menuBar.add(buildZamknijMenu());
        return menuBar;
    }

    private MenuBarItem buildWydrukiMenu() {
        raportyMenu = new Menu();
        MenuBarItem wydrukiMenuBarItem = new MenuBarItem("Druki i wnioski", raportyMenu);
        raportyMenu.add(createMenuItem("Moje wnioski", NameTokens.mojeWnioski));
        if (rola == Rola.ADMIN)
            raportyMenu.add(createMenuItem("Kartoteka wniosków", NameTokens.wnioskiKart));
        //Tutaj dodawać kolejne raporty
        return wydrukiMenuBarItem;
    }

    private MenuBarItem buildSymulacjaMenu() {
        symulacjaMenu = new Menu();
        MenuBarItem symulacjaMenuBarItem = new MenuBarItem("Symulacja", symulacjaMenu);
        symulacjaMenu.add(createMenuItem("Przychody i koszty", NameTokens.przychodykoszty));
        symulacjaMenu.add(createMenuItem("Przychody i koszty - kierunki", NameTokens.przychodykosztykierunki));
        return symulacjaMenuBarItem;
    }

    private MenuBarItem buildPracownikMenu() {
        przedmiotyMenu = new Menu();
        MenuBarItem przedmiotyBarItem = new MenuBarItem("Przedmioty", przedmiotyMenu);
        przedmiotyMenu.add(createMenuItem("Przypisanie ocen", NameTokens.mojePrzedmioty));
        return przedmiotyBarItem;
    }

    private MenuBarItem buildStudentMenu() {
        przedmiotyMenu = new Menu();
        MenuBarItem przedmiotyBarItem = new MenuBarItem("Przedmioty", przedmiotyMenu);
        przedmiotyMenu.add(createMenuItem("Moje przedmioty", NameTokens.mojePrzedmioty));
        return przedmiotyBarItem;
    }

    private MenuBarItem buildDaneOsoboweMenu() {
        daneOsoboweMenu = new Menu();
        MenuBarItem daneOsoboweBarItem = new MenuBarItem("Dane osobowe", daneOsoboweMenu);
        daneOsoboweMenu.add(createMenuItem("Moje dane", NameTokens.daneUzytkownika));
        return daneOsoboweBarItem;
    }

//    private MenuBarItem buildPrzedmiotyUzytkownikaMenu() {
//        przedmiotyMenu = new Menu();
//        MenuBarItem przedmiotyBarItem = new MenuBarItem("Przedmioty", przedmiotyMenu);
//        String nazwa = "Moje przedmioty";
//        if (rola == Rola.NAUCZYCIEL) {
//            nazwa = "Przypisanie ocen";
//        }
//        przedmiotyMenu.add(createMenuItem(nazwa, NameTokens.mojePrzedmioty));
//        return przedmiotyBarItem;
//    }

    private MenuBarItem buildZamknijMenu() {
        zamknijMenu = new Menu();
        zamknijBarItem = new MenuBarItem("Zamknij", zamknijMenu);//Na razie jest podpięty menuitem - można rozwazyc podpięcie handlera bzpośrednio do menu
        zamknijMenu.add(createZamknijItem("Zamknij"));
        return zamknijBarItem;
    }

    private MenuBarItem buildAdminMenu() {

        adminMenu = new Menu();
        MenuBarItem adminbarItem = new MenuBarItem("Administrator", adminMenu);
        adminMenu.add(createMenuItem("Użytkownicy", NameTokens.kartotekaUzytkownikow));
        adminMenu.add(createMenuItem("Przedmioty", NameTokens.przedmioty));//Dodawanie i edycja przedmiotow
        adminMenu.add(createMenuItem("Wnioski", NameTokens.wnioski));
        adminMenu.add(createMenuItem("Wiadomości", NameTokens.wiadomosci));
        return adminbarItem;
    }

    private native void shownextpage(String message) /*-{
		$wnd.alert("Nastąpiło wylogowanie z aplikacji");
    }-*/;

    protected MenuItem createZamknijItem(String title) {
        MenuItem item = new MenuItem(title);
        item.addSelectionHandler(new SelectionHandler<Item>() {

            @Override
            public void onSelection(SelectionEvent<Item> arg0) {
                Cookies.removeCookie("loggedUser");
                Cookies.removeCookie("userRole");
                shownextpage("");
                Window.Location.replace("Logout.html");
            }
        });
        return item;
    }

    protected MenuItem createMenuItem(String title, String token) {
        MenuItem item = new MenuItem(title);
        item.addSelectionHandler(createSelectionHandler(token));
        return item;
    }

    private SelectionHandler<Item> createSelectionHandler(final String token) {
        SelectionHandler<Item> handler = new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                placeManager.revealPlace(new PlaceRequest.Builder().nameToken(token).build());
            }
        };
        return handler;
    }

}
