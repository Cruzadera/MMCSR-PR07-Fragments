package es.iessaladillo.pedrojoya.pr05.ui.users;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.ActivityUsersBinding;

public class UsersActivity extends AppCompatActivity {
    ActivityUsersBinding b;
    private UsersActivityViewModel viewModel;
    private UsersActivityAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_users);
        viewModel = ViewModelProviders.of(this, new UsersActivityViewModelFactory(UsersDB.getInstance()))
                .get(UsersActivityViewModel.class);
        setupViews();
        observeUsers();
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        listAdapter = new UsersActivityAdapter();
        listAdapter.setOnDeleteListener(position -> deleteUser(listAdapter.getItem(position)));
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.users_lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);

    }

    private void deleteUser(User user) {
        viewModel.deleteUser(user);
    }

    private void editUser(User user){
        viewModel.editUser(user);
    }

    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

}
