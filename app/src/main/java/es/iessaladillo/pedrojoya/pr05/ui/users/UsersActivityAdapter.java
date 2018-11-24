package es.iessaladillo.pedrojoya.pr05.ui.users;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr05.R;

import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class UsersActivityAdapter extends ListAdapter<User, UsersActivityAdapter.ViewHolder>{

    //Creamos dos interfaces de comunicación, una para borrar un Usuario y otra para editarlo.
    interface OnDeleteListener{
        void onDelete(int position);
    }

    interface OnEditableListener{
        void onEdit(int position);
    }

    private OnDeleteListener onDeleteListener;
    private OnEditableListener onEditableListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public void setOnEditableListener(OnEditableListener onEditableListener) {
        this.onEditableListener = onEditableListener;
    }

    UsersActivityAdapter() {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail())&&
                        TextUtils.equals(oldItem.getPhoneNumber(), newItem.getPhoneNumber()) &&
                        oldItem.getImgResId() == newItem.getImgResId();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_users_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersActivityAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected User getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView lblName;
        private final TextView lblPhonenumber;
        private final TextView lblEmail;
        private final ImageView imgUser;
        private Button btnDelete;
        private Button btnEdit;

       ViewHolder(View itemView) {
           super(itemView);
           lblName = ViewCompat.requireViewById(itemView, R.id.lblNameCV);
           lblPhonenumber = ViewCompat.requireViewById(itemView, R.id.lblPhonenumberCV);
           lblEmail = ViewCompat.requireViewById(itemView, R.id.lblEmailCV);
           imgUser = ViewCompat.requireViewById(itemView, R.id.imgUserCV);
           btnDelete = ViewCompat.requireViewById(itemView, R.id.btnDelete);
           btnEdit = ViewCompat.requireViewById(itemView, R.id.btnEdit);
       }

       void bind(User user){
           lblName.setText(user.getName());
           lblEmail.setText(user.getEmail());
           lblPhonenumber.setText(user.getPhoneNumber());
           imgUser.setImageResource(user.getImgResId());
           if (onDeleteListener != null) {
               btnDelete.setOnClickListener(v -> onDeleteListener.onDelete(getAdapterPosition()));
           }
           if (onEditableListener != null) {
               itemView.setOnClickListener(v -> onEditableListener.onEdit(getAdapterPosition()));
           }
       }
    }
}
