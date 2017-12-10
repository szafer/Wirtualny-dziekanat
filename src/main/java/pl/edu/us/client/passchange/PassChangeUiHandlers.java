package pl.edu.us.client.passchange;

import com.gwtplatform.mvp.client.UiHandlers;

public interface PassChangeUiHandlers extends UiHandlers {

    public void onPowrotClicked();

    public void onZmianaClicked();
    
    public void sprawdzCzyStareHasloJestPoprawne(String login);
}
