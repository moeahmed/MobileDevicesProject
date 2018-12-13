package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private List<Messages> messageList;
    private FirebaseAuth mAuth;

    public MessagesAdapter(List<Messages> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();
        return  new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        String senderID = mAuth.getCurrentUser().getUid();
        Messages messages = messageList.get(i);

        String fromUserID = messages.getFrom();
        String fromMessageDatatype = messages.getDatatype();

        if (fromMessageDatatype.equals("text")) {
            messageViewHolder.receiverMessageText.setVisibility(View.INVISIBLE);

            if (fromUserID.equals(senderID)) {
                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.my_message);
                messageViewHolder.senderMessageText.setText(messages.getMessage());
            } else {
                messageViewHolder.senderMessageText.setVisibility(View.INVISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.their_message);
                messageViewHolder.receiverMessageText.setText(messages.getMessage());

            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
