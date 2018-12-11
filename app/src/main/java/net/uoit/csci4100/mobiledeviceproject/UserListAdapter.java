package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    ArrayList<Users> usersList;

    public UserListAdapter(ArrayList<Users> usersList){
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users, null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(layoutParams);

        UserListViewHolder userListViewHolder = new UserListViewHolder(layoutView);
        return userListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder userListViewHolder, int i) {
        userListViewHolder.mName.setText(usersList.get(i).getName());
        userListViewHolder.mEmail.setText(usersList.get(i).getEmail());
        //userListViewHolder.mImage.setImageDrawable(usersList.get(i).getUserImage());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder{
        public TextView mName;
        public TextView mEmail;
        public ImageView mImage;
        public UserListViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name);
            mEmail = view.findViewById(R.id.email);
            mImage = view.findViewById(R.id.pictures);


        }

    }
}
