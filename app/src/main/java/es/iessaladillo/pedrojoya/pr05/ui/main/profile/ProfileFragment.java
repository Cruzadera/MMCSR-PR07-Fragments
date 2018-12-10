package es.iessaladillo.pedrojoya.pr05.ui.main.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDB;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentProfileBinding;
import es.iessaladillo.pedrojoya.pr05.utils.Field;
import es.iessaladillo.pedrojoya.pr05.utils.IntentsImplicitUtils;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.SnackbarUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private User user;
    private ProfileFragmentViewModel viewModel;
    FragmentProfileBinding b;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_USER, user);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
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
        viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);
        //Objects.requireNonNull(getView());
        setupViews();
    }

    private void setupViews() {
        setupToolbar();
        initViews();
    }

    private void initViews() {
        initParametres();
        //TEST
        b.imgAvatar.setTag(viewModel.getDefaulfAvatar().getImageResId());
        //LISTENERS
        b.imgAvatar.setOnClickListener(l->startAvatarFragment());

        b.include.imgAddress.setOnClickListener(l -> startIntent(Field.ADDRESS));
        b.include.imgEmail.setOnClickListener(l -> startIntent(Field.EMAIL));
        b.include.imgWeb.setOnClickListener(l -> startIntent(Field.WEB));
        b.include.imgPhonenumber.setOnClickListener(l -> startIntent(Field.PHONENUMBER));

        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblName));
        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblAddress));
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblEmail));
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblPhonenumber));
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, b.include.lblWeb));

        b.include.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldSimple(b.include.lblName, b.include.txtName);
            }
        });

        b.include.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(b.include.lblEmail, b.include.txtEmail, b.include.imgEmail, Field.EMAIL);
            }
        });

        b.include.txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(b.include.lblAddress, b.include.txtAddress, b.include.imgAddress, Field.ADDRESS);
            }
        });

        b.include.txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(b.include.lblPhonenumber, b.include.txtPhonenumber, b.include.imgPhonenumber, Field.PHONENUMBER);
            }
        });
        b.include.txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(b.include.lblWeb, b.include.txtWeb, b.include.imgWeb, Field.WEB);
            }
        });

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
            KeyboardUtils.hideSoftKeyboard(getActivity());
            SnackbarUtils.snackbar(b.imgAvatar, getString(R.string.main_saved_succesfully));
        } else {
            KeyboardUtils.hideSoftKeyboard(getActivity());
            SnackbarUtils.snackbar(b.imgAvatar, getString(R.string.main_error_saving));
            checkField(b.include.lblAddress, b.include.txtAddress, b.include.imgAddress, Field.ADDRESS);
            checkField(b.include.lblPhonenumber, b.include.txtPhonenumber, b.include.imgPhonenumber, Field.PHONENUMBER);
            checkField(b.include.lblEmail, b.include.txtEmail, b.include.imgEmail, Field.EMAIL);
            checkField(b.include.lblWeb, b.include.txtWeb, b.include.imgWeb, Field.WEB);
        }
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

    private void startAvatarFragment() {

    }

    private void initParametres() {
        b.include.lblName.setTypeface(Typeface.DEFAULT_BOLD);
        b.include.txtName.requestFocus();
        b.imgAvatar.setImageResource(viewModel.getDefaulfAvatar().getImageResId());
        b.lblAvatar.setText(viewModel.getDefaulfAvatar().getName());
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setTitle(getString(R.string.title_fragmentProfile));
        }
    }

}
