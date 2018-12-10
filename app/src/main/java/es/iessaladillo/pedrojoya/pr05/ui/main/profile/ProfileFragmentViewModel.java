package es.iessaladillo.pedrojoya.pr05.ui.main.profile;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class ProfileFragmentViewModel extends ViewModel {
    private final Database database = Database.getInstance();
    private Avatar avatar;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Avatar getDefaulfAvatar(){
        return Database.getInstance().getDefaultAvatar();
    }
}
