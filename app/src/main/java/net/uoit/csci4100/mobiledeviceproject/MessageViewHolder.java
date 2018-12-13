package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView senderMessageText;
    public TextView receiverMessageText;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        senderMessageText = (TextView) itemView.findViewById(R.id.senderMessage);
        receiverMessageText = (TextView) itemView.findViewById(R.id.receiverMessage);
    }

}
