package net.uoit.csci4100.mobiledeviceproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private String mSenderID;
    private String mReceiverID;
    private String mReceiverName;
    private String mReceiverImage;
    private String mReceiverEmail;
    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserImage;
    private ImageButton mSendButton;
    private EditText mMessageText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        // Get Sender Info
        mAuth = FirebaseAuth.getInstance();
        mSenderID = mAuth.getCurrentUser().getUid();

        mDbRef = FirebaseDatabase.getInstance().getReference();
        
        // Get message info
        mReceiverID = getIntent().getExtras().get("contactID").toString();
        mReceiverName = getIntent().getExtras().get("contactName").toString();
        mReceiverEmail = getIntent().getExtras().get("contactEmail").toString();
        mReceiverImage = getIntent().getExtras().get("contactImage").toString();


        InitializeChat();
        Picasso.get().load(mReceiverImage).placeholder(R.drawable.avatar1).into(mUserImage);
        mUserName.setText(mReceiverName);
        mUserEmail.setText(mReceiverEmail);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void InitializeChat() {
        mUserImage = (ImageView) findViewById(R.id.chatbarProfileImage);
        mUserName = (TextView) findViewById(R.id.chatbarName);
        mUserEmail = (TextView) findViewById(R.id.chatbarEmail);

        //mSendButton = (ImageButton) findViewById(R.id.sendButton);
//        FloatingActionButton fab = (FloatingActionButton)mSendButton.findViewById(R.id.button);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), FindUserActivity.class);
//                startActivity(intent);
//            }
//        });
        mMessageText = (EditText) findViewById(R.id.inputMessageTxt);
    }

    private void sendMessage() {
        String messageContent = mMessageText.getText().toString();

        if (!TextUtils.isEmpty(messageContent)) {
            String mSenderRef = "Chats/" + mSenderID + "/" + mReceiverID;
            String mReceiverRef = "Chats/" + mReceiverID + "/" + mSenderID;

            DatabaseReference chatRef = mDbRef.child("Chats").child(mSenderID).child(mReceiverID).push();

            String pushID = chatRef.getKey();

            Map messageMetaData = new HashMap();
            messageMetaData.put("to", mReceiverID);
            messageMetaData.put("from", mSenderID);
            messageMetaData.put("message", messageContent);
            messageMetaData.put("type", "text");

            Map messageBody = new HashMap();
            messageBody.put(mSenderRef + "/" + pushID, messageMetaData);
            messageBody.put(mReceiverRef + "/" + pushID, messageMetaData);

            mDbRef.updateChildren(messageBody).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(ChatActivity.this, "Message failed to send", Toast.LENGTH_SHORT);
                    }
                    mMessageText.setText("");
                }
            });


        }
    }
}
