package es.iessaladillo.pedrojoya.pr05.ui.main.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;

public class ProfileFragmentViewModelFactory implements ViewModelProvider.Factory {

    private UsersDB usersDB;

    public ProfileFragmentViewModelFactory(UsersDB usersDB) {
        this.usersDB = usersDB;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileFragmentViewModel(usersDB);
    }
}
