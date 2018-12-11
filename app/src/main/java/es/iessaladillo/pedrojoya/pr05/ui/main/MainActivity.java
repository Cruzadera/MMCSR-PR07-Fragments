package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.ui.main.avatar.AvatarFragment;
import es.iessaladillo.pedrojoya.pr05.ui.main.main.users.UsersFragment;
import es.iessaladillo.pedrojoya.pr05.ui.main.profile.ProfileFragment;
import es.iessaladillo.pedrojoya.pr05.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements UsersFragment.OnUserEditListener, ProfileFragment.OnAvatarChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.container,
                    UsersFragment.newInstance(), UsersFragment.class.getSimpleName());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onUserEdit(@NonNull User user) {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),
                R.id.container, ProfileFragment.newInstance(user),
                ProfileFragment.class.getSimpleName(), ProfileFragment.class.getSimpleName(),
                FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    @Override
    public void onAvatarChanged(Avatar avatar) {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.container,
                AvatarFragment.newInstance(avatar), AvatarFragment.class.getSimpleName(), AvatarFragment.class.getSimpleName(),
                FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }
}
