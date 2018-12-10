package es.iessaladillo.pedrojoya.pr05.ui.main.main.users;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentUsersBinding;

public class UsersFragment extends Fragment {
    private UsersFragmentViewModel viewModel;
    private UsersFragmentAdapter listAdapter;
    private OnUserEditListener onUserEditListener;
    FragmentUsersBinding b;

    public interface OnUserEditListener {
        void onUserEdit(User user);
    }


    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new UsersFragmentViewModelFactory(UsersDB.getInstance()))
                .get(UsersFragmentViewModel.class);
        setupViews();
        observeUsers();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onUserEditListener = (OnUserEditListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                      context.toString() + " must implement UsersFragment.OnUserEditListener");
        }

    }

    @Override
    public void onDetach() {
        onUserEditListener = null;
        super.onDetach();
    }

    private void setupViews() {
        setupToolbar();
        setupRecyclerView();
        //b.fabAdd.setOnClickListener(l -> startProfileActivity());
       // b.lblEmptyView.setOnClickListener(l -> startProfileActivity(new User(),  RC_PUT));
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    private void setupRecyclerView() {
        listAdapter = new UsersFragmentAdapter();
        listAdapter.setOnDeleteListener(position -> deleteUser(listAdapter.getItem(position)));
        listAdapter.setOnEditableListener((v, position) -> onUserEditListener.onUserEdit(listAdapter.getItem(position)));
        b.lstUsersFr.setHasFixedSize(true);
        b.lstUsersFr.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.users_lstUsers_columns)));
        b.lstUsersFr.setItemAnimator(new DefaultItemAnimator());
        b.lstUsersFr.setAdapter(listAdapter);
    }


    private void deleteUser(User user) {
        viewModel.deleteUser(user);
    }


    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.lblEmptyViewFr.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }


    private void addUser(User user) {
        viewModel.addUser(user);
    }

    private void userEdited(User user) {
        viewModel.editUser(user);
    }

}
