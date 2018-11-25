package es.iessaladillo.pedrojoya.pr05.ui.users;

import android.content.Intent;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;



public class UsersActivityViewModel extends ViewModel {

    private final UsersDB database;
    private LiveData<List<User>> users;

    public UsersActivityViewModel(UsersDB database) {
        this.database = database;
        users = database.queryUsers();
    }

    LiveData<List<User>> getUsers() {
        return users;
    }

    void deleteUser(User user) {
        database.deleteUser(user);
    }

    public void editUser(User user) {

    }

    public void addUser(int position) {
        database.addUser(position);
    }
}
