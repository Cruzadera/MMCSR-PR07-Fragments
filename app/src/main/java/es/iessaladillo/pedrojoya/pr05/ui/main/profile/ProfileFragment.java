package es.iessaladillo.pedrojoya.pr05.ui.main.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentProfileBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.utils.Field;
import es.iessaladillo.pedrojoya.pr05.utils.IntentsImplicitUtils;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.SnackbarUtils;
import es.iessaladillo.pedrojoya.pr05.utils.TextWatcherUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private User user;
    private ProfileFragmentViewModel viewModel;
    private FragmentProfileBinding b;
    private MainActivityViewModel viewModelActivity;
    private OnAvatarChangedListener onAvatarChangedListener;

    public interface OnAvatarChangedListener {
        void onAvatarChanged(Avatar avatar);
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_USER, user);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onAvatarChangedListener = (OnAvatarChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement ProfileFragment.OnAvatarChangedListener");
        }
    }

    @Override
    public void onDetach() {
        onAvatarChangedListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        obtainArguments();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModelActivity.setAvatar(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            user = arguments.getParcelable(ARG_USER);
        }
        if (arguments == null || user == null) {
            user = new User();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        Objects.requireNonNull(getArguments().getParcelable(ARG_USER));
        viewModel = ViewModelProviders.of(this, new ProfileFragmentViewModelFactory(
                UsersDB.getInstance())).get(ProfileFragmentViewModel.class);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setupViews();
        if (savedInstanceState == null) {
            if (user.getAvatar() != null) {
                viewModel.isEditedUser(true);
                obtainDataUser();
            }
        }
    }

    private void setupViews() {
        setupToolbar();
        initViews();
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setTitle(getString(R.string.title_fragmentProfile));
        }
    }

    private void obtainDataUser() {
        b.include.txtName.setText(user.getName());
        b.include.txtAddress.setText(user.getAddress());
        b.include.txtPhonenumber.setText(user.getPhoneNumber());
        b.include.txtWeb.setText(user.getWeb());
        b.include.txtEmail.setText(user.getEmail());
        viewModel.setAvatar(user.getAvatar());
        displayAvatar();
    }

    private void initViews() {
        initParametres();
        displayAvatar();

        //LISTENERS
        b.imgAvatar.setOnClickListener(l -> onAvatarChangedListener.onAvatarChanged(viewModel.getAvatar()));
        b.include.imgAddress.setOnClickListener(l -> startIntent(Field.ADDRESS));
        b.include.imgEmail.setOnClickListener(l -> startIntent(Field.EMAIL));
        b.include.imgWeb.setOnClickListener(l -> startIntent(Field.WEB));
        b.include.imgPhonenumber.setOnClickListener(l -> startIntent(Field.PHONENUMBER));

        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblName));
        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblAddress));
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblEmail));
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblPhonenumber));
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblWeb));

        TextWatcherUtils.setAfterTextChangeListener(b.include.txtName, s -> checkFieldSimple(b.include.lblName, b.include.txtName));
        TextWatcherUtils.setAfterTextChangeListener(b.include.txtEmail, s -> checkField(b.include.lblEmail, b.include.txtEmail, b.include.imgEmail, Field.EMAIL));
        TextWatcherUtils.setAfterTextChangeListener(b.include.txtAddress, s -> checkField(b.include.lblAddress, b.include.txtAddress, b.include.imgAddress, Field.ADDRESS));
        TextWatcherUtils.setAfterTextChangeListener(b.include.txtPhonenumber, s -> checkField(b.include.lblPhonenumber, b.include.txtPhonenumber, b.include.imgPhonenumber, Field.PHONENUMBER));
        TextWatcherUtils.setAfterTextChangeListener(b.include.txtWeb, s -> checkField(b.include.lblWeb, b.include.txtWeb, b.include.imgWeb, Field.WEB));

        b.include.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });
    }

    private void save() {
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            SnackbarUtils.snackbar(b.imgAvatar, getString(R.string.main_saved_succesfully));
            getDataUserEdit();
            if (viewModel.isEditedUser()) {
                viewModelActivity.setUser(user);
            }else{
                viewModel.addUser(user);
            }
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            SnackbarUtils.snackbar(b.imgAvatar, getString(R.string.main_error_saving));
            checkField(b.include.lblAddress, b.include.txtAddress, b.include.imgAddress, Field.ADDRESS);
            checkField(b.include.lblPhonenumber, b.include.txtPhonenumber, b.include.imgPhonenumber, Field.PHONENUMBER);
            checkField(b.include.lblEmail, b.include.txtEmail, b.include.imgEmail, Field.EMAIL);
            checkField(b.include.lblWeb, b.include.txtWeb, b.include.imgWeb, Field.WEB);
        }
    }

    private void getDataUserEdit() {
        user.setName(b.include.txtName.getText().toString());
        user.setEmail(b.include.txtEmail.getText().toString());
        user.setPhoneNumber(b.include.txtPhonenumber.getText().toString());
        user.setAddress(b.include.txtAddress.getText().toString());
        user.setWeb(b.include.txtWeb.getText().toString());
        user.setAvatar(viewModel.getAvatar());
    }

    private boolean isValidForm() {
        boolean isValid;
        isValid = checkFieldSimple(b.include.lblName, b.include.txtName) && checkField(b.include.lblAddress, b.include.txtAddress, b.include.imgAddress, Field.ADDRESS) && checkField(b.include.lblPhonenumber, b.include.txtPhonenumber, b.include.imgPhonenumber, Field.PHONENUMBER)
                && checkField(b.include.lblEmail, b.include.txtEmail, b.include.imgEmail, Field.EMAIL) && checkField(b.include.lblWeb, b.include.txtWeb, b.include.imgWeb, Field.WEB);
        return isValid;
    }

    private boolean checkField(TextView label, EditText txt, ImageView imageView, Field field) {
        boolean isValid;
        if (field == Field.EMAIL && !ValidationUtils.isValidEmail(txt.getText().toString())) {
            isValid = invalidateField(label, txt, imageView);

        } else if (field == Field.PHONENUMBER && !ValidationUtils.isValidPhone(txt.getText().toString())) {
            isValid = invalidateField(label, txt, imageView);

        } else if (field == Field.WEB && !ValidationUtils.isValidUrl(txt.getText().toString())) {
            isValid = invalidateField(label, txt, imageView);

        } else if (field == Field.ADDRESS && !ValidationUtils.isEmptyText(txt.getText().toString())) {
            isValid = invalidateField(label, txt, imageView);

        } else {
            label.setEnabled(true);
            imageView.setEnabled(true);
            isValid = true;
        }
        return isValid;
    }

    private boolean invalidateField(TextView label, EditText txt, ImageView imageView) {
        txt.setError(getString(R.string.main_invalid_data));
        label.setEnabled(false);
        imageView.setEnabled(false);
        return false;
    }

    private boolean checkFieldSimple(TextView label, EditText editText) {
        boolean isValid;
        if (!ValidationUtils.isEmptyText(editText.getText().toString())) {
            editText.setError(getString(R.string.main_invalid_data));
            label.setEnabled(false);
            isValid = false;
        } else {
            label.setEnabled(true);
            isValid = true;
        }
        return isValid;
    }

    private void onPointerCaptureFocus(boolean hasCapture, TextView lbl) {
        lbl.setTypeface(hasCapture ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }

    private void startIntent(Field field) {
        Intent intent = new Intent();
        if (field == Field.EMAIL) {
            intent = IntentsImplicitUtils.sendEmail(b.include.txtEmail);
        } else if (field == Field.WEB) {
            intent = IntentsImplicitUtils.searchWeb(b.include.txtWeb);
        } else if (field == Field.ADDRESS) {
            intent = IntentsImplicitUtils.openMaps(b.include.txtAddress);
        } else if (field == Field.PHONENUMBER) {
            intent = IntentsImplicitUtils.startCall(b.include.txtPhonenumber);
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initParametres() {
        b.include.lblName.setTypeface(Typeface.DEFAULT_BOLD);
        b.include.txtName.requestFocus();
        if (viewModelActivity.getAvatar() != null) {
            viewModel.setAvatar(viewModelActivity.getAvatar());
            viewModel.isEditedUser(false);
        }else if(viewModel.getAvatar() == null){
            viewModel.setAvatar(viewModel.getDefaulfAvatar());
        }
    }

    private void displayAvatar() {
        if (viewModelActivity.getAvatar() != null) {
            viewModel.setAvatar(viewModelActivity.getAvatar());
        }
        b.imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        b.lblAvatar.setText(viewModel.getAvatar().getName());
        //TEST
        b.imgAvatar.setTag(viewModel.getAvatar().getImageResId());
    }

}
