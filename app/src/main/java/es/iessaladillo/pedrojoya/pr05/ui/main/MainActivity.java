package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr05.utils.Field;
import es.iessaladillo.pedrojoya.pr05.utils.IntentsImplicitUtils;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.SnackbarUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private TextView lblAvatar;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtAddress;
    private EditText txtPhonenumber;
    private EditText txtWeb;
    private ImageView imgEmail;
    private ImageView imgWeb;
    private ImageView imgAddress;
    private ImageView imgPhonenumber;
    private TextView lblName;
    private TextView lblAddress;
    private TextView lblEmail;
    private TextView lblPhonenumber;
    private TextView lblWeb;
    private Database database = Database.getInstance();

    public static final int RC_AVATAR = 1;
    private Avatar avatar = database.getDefaultAvatar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);

        //Initially
        initParametres();

        //test
        imgAvatar.setTag(Database.getInstance().getDefaultAvatar().getImageResId());

        imgAvatar.setOnClickListener(l -> startAvatarActivity());

        imgEmail.setOnClickListener(l -> startIntent(Field.EMAIL));
        imgAddress.setOnClickListener(l -> startIntent(Field.ADDRESS));
        imgPhonenumber.setOnClickListener(l -> startIntent(Field.PHONENUMBER));
        imgWeb.setOnClickListener(l -> startIntent(Field.WEB));

        txtName.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, lblName));
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, lblEmail));
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, lblAddress));
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, lblPhonenumber));
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> onPointerCaptureFocus(hasFocus, lblWeb));

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldSimple(lblName, txtName);
            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(lblAddress, txtAddress, imgAddress, Field.ADDRESS);
            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(lblEmail, txtEmail, imgEmail, Field.EMAIL);
            }
        });

        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(lblPhonenumber, txtPhonenumber, imgPhonenumber, Field.PHONENUMBER);
            }
        });

        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkField(lblWeb, txtWeb, imgWeb, Field.WEB);


            }
        });

        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_AVATAR) {
            if (data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
                avatar = data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR);
                changeAvatar(avatar);
            }
        }
    }

    private void changeAvatar(Avatar avatar) {
        imgAvatar.setImageResource(avatar.getImageResId());
        lblAvatar.setText(avatar.getName());
        //test
        imgAvatar.setTag(avatar.getImageResId());

    }

    private void startAvatarActivity() {
        AvatarActivity.startForResult(MainActivity.this, RC_AVATAR, avatar);
    }

    private void initParametres() {
        lblName.setTypeface(Typeface.DEFAULT_BOLD);
        txtName.requestFocus();
        imgAvatar.setImageResource(database.getDefaultAvatar().getImageResId());
        lblAvatar.setText(database.getDefaultAvatar().getName());
    }

    private void startIntent(Field field) {
        Intent intent = new Intent();
        if (field == Field.EMAIL) {
            intent = IntentsImplicitUtils.sendEmail(txtEmail);
        } else if (field == Field.WEB) {
            intent = IntentsImplicitUtils.searchWeb(txtWeb);
        } else if (field == Field.ADDRESS) {
            intent = IntentsImplicitUtils.openMaps(txtAddress);
        } else if (field == Field.PHONENUMBER) {
            intent = IntentsImplicitUtils.startCall(txtPhonenumber);
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onPointerCaptureFocus(boolean hasCapture, TextView lbl) {
        lbl.setTypeface(hasCapture ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
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

    private boolean isValidForm(){
        boolean isValid;
        isValid = checkFieldSimple(lblName, txtName) && checkField(lblAddress, txtAddress, imgAddress, Field.ADDRESS) && checkField(lblPhonenumber, txtPhonenumber, imgPhonenumber, Field.PHONENUMBER)
                && checkField(lblEmail, txtEmail, imgEmail, Field.EMAIL) && checkField(lblWeb, txtWeb, imgWeb, Field.WEB);
        return isValid;
    }


    private void save() {
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(this);
            SnackbarUtils.snackbar(imgAvatar, getString(R.string.main_saved_succesfully));
        } else {
            KeyboardUtils.hideSoftKeyboard(this);
            SnackbarUtils.snackbar(imgAvatar, getString(R.string.main_error_saving));
            checkField(lblAddress, txtAddress, imgAddress, Field.ADDRESS);
            checkField(lblPhonenumber, txtPhonenumber, imgPhonenumber, Field.PHONENUMBER);
            checkField(lblEmail, txtEmail, imgEmail, Field.EMAIL);
            checkField(lblWeb, txtWeb, imgWeb, Field.WEB);
        }

    }

}
