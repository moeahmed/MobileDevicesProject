package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple Adapter for populating the chats fragment.
 */
public class ChatsViewAdapter extends RecyclerView.ViewHolder{

    ImageView mUserImage;
    TextView mUserName;
    TextView mUserEmail;

    public ChatsViewAdapter(@NonNull View itemView) {
        super(itemView);

        mUserImage = itemView.findViewById(R.id.profileImage);
        mUserName = itemView.findViewById(R.id.txtUsername);
        mUserEmail = itemView.findViewById(R.id.profileEmail);
    }
}
