package pl.edu.us.client.registeruser;

import com.gwtplatform.mvp.client.UiHandlers;

public interface RejestracjaUiHandlers extends UiHandlers {

    public void onPowrotClicked();

    public void onZarejestrujClicked();
    
    public void sprawdzCzyLoginWBazie(String login);
}
