package pl.edu.us.client.uzytkownik.daneuzytkownika;

import com.google.inject.Inject;

import pl.edu.us.client.main.BazowyPanel;

public class DaneUzytkownikaMainPanel extends BazowyPanel {

    private final DaneUzytkownikaPanel panel;

    @Inject
    public DaneUzytkownikaMainPanel(DaneUzytkownikaModel model) {
        setHeaderVisible(true);
        setHeadingHtml("Moje dane");
        panel = new DaneUzytkownikaPanel(model, this);
        getBorderLayoutContainer().setCenterWidget(panel);
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public DaneUzytkownikaPanel getPanel() {
        return panel;
    }
}
