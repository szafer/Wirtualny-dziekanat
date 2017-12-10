package pl.edu.us.client.passchange;

import com.google.inject.Singleton;

import pl.edu.us.shared.model.User;

@Singleton
public class PassChangeModel {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
