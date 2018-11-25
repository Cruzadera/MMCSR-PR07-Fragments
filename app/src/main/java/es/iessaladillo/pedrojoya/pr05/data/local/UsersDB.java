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
        this.users = new ArrayList<>(Arrays.asList(new User(1, "Petrolina", "petrolina@gmail.com", "600223444", "eldiario.es", "el patio de mi casa", Database.getInstance().getRandomAvatar()),
                new User(2, "Baldomero", "baldomero@gmail.com", "600116411","xataka.es", "Calle Maestro Jiménez", Database.getInstance().getRandomAvatar()),
                new User(3, "Pancracia", "pancracia@gmail.com", "677889900","youtube.com","Avenida Ramón Puyol", Database.getInstance().getRandomAvatar())));
        update();
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
        update();
    }

    private void update() {
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public User addUser(int position) {
        return users.get(position);
    }

    public User queryUser(long id) {
        for (User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void editUser(User user) {
        for(int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId() == user.getId()) {
                users.set(i, user);
                i = users.size();
            }
        }
        update();
    }
}
