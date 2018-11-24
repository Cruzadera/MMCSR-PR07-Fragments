package es.iessaladillo.pedrojoya.pr05.ui.users;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;

public class UsersActivityViewModelFactory implements ViewModelProvider.Factory{
    private final UsersDB usersDB;

    UsersActivityViewModelFactory(UsersDB usersDB) {
        this.usersDB = usersDB;
    }

    @NonNull
    public <T extends ViewModel>T create(@NonNull Class<T> modelClass){
        //noinspection unchecked
        return (T) new UsersActivityViewModel(usersDB);
    }
}
