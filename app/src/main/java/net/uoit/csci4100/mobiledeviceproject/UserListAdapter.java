package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The UserListAdapter provides helps populate the UserList for contacts.
 */
public class UserListAdapter extends RecyclerView.ViewHolder{
    ImageView mUserImage;
    TextView mUserName;
    TextView mUserEmail;


    public UserListAdapter(@NonNull View itemView) {
        super(itemView);

        mUserName = itemView.findViewById(R.id.profileUser);
        mUserEmail = itemView.findViewById(R.id.profileEmail);
        mUserImage = itemView.findViewById(R.id.profileImage);
    }

}
