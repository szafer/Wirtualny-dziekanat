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
    private Menu przedmiotyMenu; //pokazuje się zawsze
    private Menu adminMenu;
    private Menu zamknijMenu;
    private Menu studentMenu;
    private Menu defMenu;
    private Menu pracownikMenu;
    private Menu symulacjaMenu;
    private Menu raportyMenu;//pokazuje się zawsze
    private MenuBarItem zamknijBarItem;//pokazuje się zawsze
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
        menuBar.add(buildPrzedmiotyUzytkownikaMenu());
        if (rola != null) {
            if (rola == Rola.ADMIN) {
//                menuBar.add(buildDefMenu());//TODO definicje przedmiotów tu czy w przedmiotach ?
                menuBar.add(buildAdminMenu());
                menuBar.add(buildPracownikMenu());
                menuBar.add(buildStudentMenu());
//                menuBar.add(buildSymulacjaMenu());
            } else if (rola == Rola.NAUCZYCIEL) {
                menuBar.add(buildPracownikMenu());
            } else {
                menuBar.add(buildStudentMenu());
            }
        }
        menuBar.add(buildWydrukiMenu());
        menuBar.add(buildZamknijMenu());
//        menuBar.add(new LogoutButton());//Jakiś problem z LogoutButton - na razie nie używać
        return menuBar;
    }

    private MenuBarItem buildWydrukiMenu() {
        raportyMenu = new Menu();
        MenuBarItem wydrukiMenuBarItem = new MenuBarItem("Druki i wnioski", raportyMenu);
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
        pracownikMenu = new Menu();
        MenuBarItem pracownikBarItem = new MenuBarItem("Pracownik", pracownikMenu);
        pracownikMenu.add(createMenuItem("Kartoteka Pracowników", NameTokens.pracownicy));
        return pracownikBarItem;
    }

    private MenuBarItem buildDefMenu() {
        defMenu = new Menu();
        MenuBarItem definicjeBarItem = new MenuBarItem("Definicje", defMenu);
        defMenu.add(createMenuItem("Kierunki studiów", NameTokens.przedmioty));
        return definicjeBarItem;
    }

    private MenuBarItem buildStudentMenu() {

        studentMenu = new Menu();
        MenuBarItem studentBarItem = new MenuBarItem("Studenci", studentMenu);
        studentMenu.add(createMenuItem("Studenci", NameTokens.kartotekaUzytkownikow));
        studentMenu.add(createMenuItem("Oceny Studenta", NameTokens.ocenyStudenta));
        return studentBarItem;
    }

    private MenuBarItem buildDaneOsoboweMenu() {
        daneOsoboweMenu = new Menu();
        MenuBarItem daneOsoboweBarItem = new MenuBarItem("Dane osobowe", daneOsoboweMenu);
        daneOsoboweMenu.add(createMenuItem("Moje dane", NameTokens.daneUzytkownika));
        return daneOsoboweBarItem;
    }

    private MenuBarItem buildPrzedmiotyUzytkownikaMenu() {
        przedmiotyMenu = new Menu();
        MenuBarItem przedmiotyBarItem = new MenuBarItem("Przedmioty", przedmiotyMenu);
        String nazwa = "Moje przedmioty";
        if (rola == Rola.ADMIN) {
            nazwa = "Przypisanie ocen";
            przedmiotyMenu.add(createMenuItem("Przedmioty", NameTokens.przedmioty));//Dodawanie i edycja przedmiotow
        } 
        //else TODO potem odkomentować - formatka dostepna tylko dla studentów i nauczycieli
            przedmiotyMenu.add(createMenuItem(nazwa, NameTokens.mojePrzedmioty));
        return przedmiotyBarItem;
    }

    private MenuBarItem buildZamknijMenu() {

        zamknijMenu = new Menu();
        // zamknij
        zamknijBarItem = new MenuBarItem("Zamknij", zamknijMenu);//Na razie jest podpięty menuitem - można rozwazyc podpięcie handlera bzpośrednio do menu
        // TODO zrobić wyjćsie z aplikacji
        // zamknijMenu.add(new LogoutButton());
        zamknijMenu.add(createZamknijItem("Zamknij"));

        // createZamknij();
        return zamknijBarItem;
    }

    private MenuBarItem buildAdminMenu() {

        adminMenu = new Menu();
        MenuBarItem adminbarItem = new MenuBarItem("Administrator", adminMenu);
        adminMenu.add(createMenuItem("Administrator", NameTokens.admin));
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
