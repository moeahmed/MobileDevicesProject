package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private Button updateUserInfo;
    private EditText name;
    private EditText email;
    private EditText password;
    String currentUserID;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateUserInfo = (Button) findViewById(R.id.btnProfileUpdate);
        name = (EditText)findViewById(R.id.txtProfileName);
        email = (EditText) findViewById(R.id.txtProfileEmail);
        password = (EditText) findViewById(R.id.txtProfilePassword);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        currentUserID = mAuth.getCurrentUser().getUid();


    }

    public void onClickUpdate(View view){
        String mName = this.name.getText().toString();
        String mPasswword = this.password.getText().toString();
        String mMmail = this.email.getText().toString();

        if(!name.getText().toString().isEmpty() || !password.getText().toString().isEmpty() || !email.getText().toString().isEmpty()){
            HashMap<String,String> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", mName);
            profileMap.put("email", mMmail);
            mRef.child("Users").child(currentUserID).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        Intent launchChat = new Intent(ProfileActivity.this, MainActivity.class );
                        startActivityForResult(launchChat,0);
                        finish();
                    }else{
                        Toast.makeText(ProfileActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
