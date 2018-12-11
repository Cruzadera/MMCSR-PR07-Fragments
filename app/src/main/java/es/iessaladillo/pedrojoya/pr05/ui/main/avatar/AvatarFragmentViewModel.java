package es.iessaladillo.pedrojoya.pr05.ui.main.avatar;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class AvatarFragmentViewModel extends ViewModel {
    private Avatar avatar;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
