package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {
    private ImageView imgCat01;
    private ImageView imgCat02;
    private ImageView imgCat03;
    private ImageView imgCat04;
    private ImageView imgCat05;
    private ImageView imgCat06;
    private TextView lblCat01;
    private TextView lblCat02;
    private TextView lblCat03;
    private TextView lblCat04;
    private TextView lblCat05;
    private TextView lblCat06;
    private Avatar avatar;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private static final String STATE_AVATAR = "STATE_AVATAR";
    private long idChoosed = 1;

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        initViews();
        initAvatars();
        getIntentData();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_AVATAR, idChoosed);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        idChoosed = savedInstanceState.getLong("STATE_AVATAR");
        isSelected(idChoosed);
    }

    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
    }

    private void unselectImageView(ImageView imageView){
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            selectAvatar(idChoosed);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initViews() {
        imgCat01 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        imgCat02 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        imgCat03 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        imgCat04 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        imgCat05 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        imgCat06 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);
        lblCat01 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        lblCat02 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        lblCat03 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        lblCat04 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        lblCat05 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        lblCat06 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);

        imgCat01.setOnClickListener(l -> isSelected(1));
        imgCat02.setOnClickListener(l -> isSelected(2));
        imgCat03.setOnClickListener(l -> isSelected(3));
        imgCat04.setOnClickListener(l -> isSelected(4));
        imgCat05.setOnClickListener(l -> isSelected(5));
        imgCat06.setOnClickListener(l -> isSelected(6));

        lblCat01.setOnClickListener(l -> isSelected(1));
        lblCat02.setOnClickListener(l -> isSelected(2));
        lblCat03.setOnClickListener(l -> isSelected(3));
        lblCat04.setOnClickListener(l -> isSelected(4));
        lblCat05.setOnClickListener(l -> isSelected(5));
        lblCat06.setOnClickListener(l -> isSelected(6));
    }

    private void isSelected(long idSelected) {
        idChoosed = idSelected;
        for(int i=0; i<imageViews.size(); i++){
            if (i+1!=idSelected) {
                unselectImageView(imageViews.get(i));
            }else{
                selectImageView(imageViews.get(i));
            }
        }
    }
    public static void startForResult(Activity activity, int requestCode, Avatar avatar) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    private void initAvatars() {
        imgCat01.setImageResource(R.drawable.cat1);
        imgCat02.setImageResource(R.drawable.cat2);
        imgCat03.setImageResource(R.drawable.cat3);
        imgCat04.setImageResource(R.drawable.cat4);
        imgCat05.setImageResource(R.drawable.cat5);
        imgCat06.setImageResource(R.drawable.cat6);

        lblCat01.setText(R.string.avatar1_name);
        lblCat02.setText(R.string.avatar2_name);
        lblCat03.setText(R.string.avatar3_name);
        lblCat04.setText(R.string.avatar4_name);
        lblCat05.setText(R.string.avatar5_name);
        lblCat06.setText(R.string.avatar6_name);

        imageViews.add(imgCat01);
        imageViews.add(imgCat02);
        imageViews.add(imgCat03);
        imageViews.add(imgCat04);
        imageViews.add(imgCat05);
        imageViews.add(imgCat06);
    }


    private void selectAvatar(long id) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_AVATAR, Database.getInstance().queryAvatar(id));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_AVATAR)) {
            avatar = intent.getParcelableExtra(EXTRA_AVATAR);
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + EXTRA_AVATAR);
        }
    }



}

