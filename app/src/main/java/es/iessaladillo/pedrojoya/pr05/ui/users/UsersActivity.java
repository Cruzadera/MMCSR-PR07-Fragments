package es.iessaladillo.pedrojoya.pr05.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import es.iessaladillo.pedrojoya.pr05.ui.profile.ProfileActivity;

public class UsersActivity extends AppCompatActivity {
    ActivityUsersBinding b;
    private UsersActivityViewModel viewModel;
    private UsersActivityAdapter listAdapter;
    private User user;

    public static final int RC_EDIT = 1;
    public static final int RC_PUT = 2;

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
        b.fabAdd.setOnClickListener(l -> startProfileActivity(new User(), RC_PUT));
        b.lblEmptyView.setOnClickListener(l -> startProfileActivity(new User(),  RC_PUT));
    }

    private void setupRecyclerView() {
        listAdapter = new UsersActivityAdapter();
        listAdapter.setOnDeleteListener(position -> deleteUser(listAdapter.getItem(position)));
        listAdapter.setOnEditableListener(position -> startProfileActivity(listAdapter.getItem(position), RC_EDIT));
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.users_lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);
    }

    private void startProfileActivity(User user, int requestCode) {
        if(requestCode == RC_EDIT){
            ProfileActivity.startForResultUser(UsersActivity.this, RC_EDIT, user);
        }else {
            ProfileActivity.startForResultUser(UsersActivity.this, RC_PUT, user);
        }
    }

    private void deleteUser(User user) {
        viewModel.deleteUser(user);
    }


    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && (requestCode == RC_EDIT || requestCode == RC_PUT)) {
            if (data != null && data.hasExtra(ProfileActivity.EXTRA_USER)) {
                user = data.getParcelableExtra(ProfileActivity.EXTRA_USER);
                if(requestCode == RC_PUT){
                    addUser(user);
                }else{
                    userEdited(user);
                }
            }
        }
    }

    private void addUser(User user) {
        viewModel.addUser(user);
    }

    private void userEdited(User user) {
        viewModel.editUser(user);
    }
}
