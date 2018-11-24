package es.iessaladillo.pedrojoya.pr05.data.local;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class UsersDB {
    private static UsersDB instance;

    private final ArrayList<User> users;
    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    private UsersDB() {
        this.users = new ArrayList<>(Arrays.asList(new User(1, "Petrolina", "petrolina@gmail.com", "600223444", R.drawable.cat1),
                new User(2, "Baldomero", "baldomero@gmail.com", "600116411", R.drawable.cat2),
                new User(3, "Pancracia", "pancracia@gmail.com", "677889900", R.drawable.cat3)));
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public static UsersDB getInstance() {
        if(instance == null){
            instance = new UsersDB();
        }
        return instance;
    }

    public LiveData<List<User>>queryUsers(){
        return usersLiveData;
    }

    public void deleteUser(User user){
        users.remove(user);
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public User editUser(User user) {
        //TODO: TERMINAR MÉTODO
        return user;
    }
}
