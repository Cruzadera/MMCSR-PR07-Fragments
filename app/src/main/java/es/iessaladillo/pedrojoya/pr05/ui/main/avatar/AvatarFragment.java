package es.iessaladillo.pedrojoya.pr05.ui.main.avatar;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentAvatarBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarFragment extends Fragment {

    private FragmentAvatarBinding b;
    private AvatarFragmentViewModel viewModel;
    private static final String ARG_AVATAR = "ARG_AVATAR";
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private Avatar avatar;
    private MainActivityViewModel viewModelActivity;

    public static AvatarFragment newInstance(Avatar avatar) {
        AvatarFragment fragment = new AvatarFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_AVATAR, avatar);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_avatar, container, false);
        return b.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        obtainArguments();
    }

    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) { //It will always be not null
            avatar = arguments.getParcelable(ARG_AVATAR);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        Objects.requireNonNull(getArguments().getParcelable(ARG_AVATAR));
        viewModel = ViewModelProviders.of(this).get(AvatarFragmentViewModel.class);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews();
        initAvatars();
        if(savedInstanceState == null){
            viewModel.setAvatar(avatar);
            obtainAvatar();
        }

    }

    private void obtainAvatar() {
        isSelected(avatar.getId());
    }

    private void initAvatars() {
        b.imgAvatar1.setImageResource(R.drawable.cat1);
        b.imgAvatar2.setImageResource(R.drawable.cat2);
        b.imgAvatar3.setImageResource(R.drawable.cat3);
        b.imgAvatar4.setImageResource(R.drawable.cat4);
        b.imgAvatar5.setImageResource(R.drawable.cat5);
        b.imgAvatar6.setImageResource(R.drawable.cat6);

        b.lblAvatar1.setText(R.string.avatar1_name);
        b.lblAvatar2.setText(R.string.avatar2_name);
        b.lblAvatar3.setText(R.string.avatar3_name);
        b.lblAvatar4.setText(R.string.avatar4_name);
        b.lblAvatar5.setText(R.string.avatar5_name);
        b.lblAvatar6.setText(R.string.avatar6_name);

        imageViews.add(b.imgAvatar1);
        imageViews.add(b.imgAvatar2);
        imageViews.add(b.imgAvatar3);
        imageViews.add(b.imgAvatar4);
        imageViews.add(b.imgAvatar5);
        imageViews.add(b.imgAvatar6);
    }

    private void initViews() {
        b.imgAvatar1.setOnClickListener(l -> isSelected(1));
        b.imgAvatar2.setOnClickListener(l -> isSelected(2));
        b.imgAvatar3.setOnClickListener(l -> isSelected(3));
        b.imgAvatar4.setOnClickListener(l -> isSelected(4));
        b.imgAvatar5.setOnClickListener(l -> isSelected(5));
        b.imgAvatar6.setOnClickListener(l -> isSelected(6));

        b.lblAvatar1.setOnClickListener(l -> isSelected(1));
        b.lblAvatar2.setOnClickListener(l -> isSelected(2));
        b.lblAvatar3.setOnClickListener(l -> isSelected(3));
        b.lblAvatar4.setOnClickListener(l -> isSelected(4));
        b.lblAvatar5.setOnClickListener(l -> isSelected(5));
        b.lblAvatar6.setOnClickListener(l -> isSelected(6));
    }

    private void isSelected(long idSelected) {
        viewModel.setAvatar(Database.getInstance().queryAvatar(idSelected));
        for(int i=0; i<imageViews.size(); i++){
            if (i+1!=idSelected) {
                unselectImageView(imageViews.get(i));
            }else{
                selectImageView(imageViews.get(i));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_avatar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            selectAvatar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectAvatar() {
        viewModelActivity.setAvatar(viewModel.getAvatar());
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_selected_image_alpha));
    }

    private void unselectImageView(ImageView imageView){
        imageView.setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_not_selected_image_alpha));
    }

}
