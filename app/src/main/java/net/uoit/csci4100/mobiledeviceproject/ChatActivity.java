package net.uoit.csci4100.mobiledeviceproject;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatActivity extends AppCompatActivity {

    private String messageReceiverID;
    private String messageReceiverName;
    private String messageReceiverImage;
    private String messageReceiverEmail;
    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get message info
        messageReceiverID = getIntent().getExtras().get("contactID").toString();
        messageReceiverName = getIntent().getExtras().get("contactName").toString();
        messageReceiverEmail = getIntent().getExtras().get("contactEmail").toString();
        messageReceiverImage = getIntent().getExtras().get("contactImage").toString();


        InitializeUser();
        Picasso.get().load(messageReceiverImage).placeholder(R.drawable.avatar1);
        mUserName.setText(messageReceiverName);
        mUserEmail.setText(messageReceiverEmail);
    }

    private void InitializeUser() {


        mUserImage = (ImageView) findViewById(R.id.chatbarProfileImage);
        mUserName = (TextView) findViewById(R.id.chatbarName);
        mUserEmail = (TextView) findViewById(R.id.chatbarEmail);


    }
}
