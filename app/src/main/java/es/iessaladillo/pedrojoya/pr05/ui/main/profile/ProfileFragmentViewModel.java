package es.iessaladillo.pedrojoya.pr05.ui.main.profile;


import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class ProfileFragmentViewModel extends ViewModel {
    private final Database database = Database.getInstance();
    private Avatar avatar;
    private UsersDB usersDB;
    private boolean editedUser;

    public ProfileFragmentViewModel(UsersDB usersDB) {
        this.usersDB = usersDB;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Avatar getDefaulfAvatar(){
        return Database.getInstance().getDefaultAvatar();
    }

    public void addUser(User user){
        usersDB.addUser(user);
    }

    public boolean isEditedUser() {
        return editedUser;
    }

    public void isEditedUser(boolean isEdited) {
        this.editedUser = isEdited;
    }
}
