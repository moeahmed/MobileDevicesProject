package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * The MessageView holder class extends the RecyclerView holder to populate the messageList.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView senderMessageText;
    public TextView receiverMessageText;

    /**
     * The MessageViewHolder method gets sender/receiver message text for the MessageViewHolder.
     * @param itemView
     */
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        senderMessageText = (TextView) itemView.findViewById(R.id.senderMessage);
        receiverMessageText = (TextView) itemView.findViewById(R.id.receiverMessage);
    }

}
