package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {
 private User user;
 private Avatar avatar;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
