package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class corresponding with activity_login
 */
public class LoginActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public static final int CHAT = 0;

    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * The onCreate method creates the login activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    Intent launchChat = new Intent(LoginActivity.this, MainActivity.class );
                    startActivityForResult(launchChat,CHAT);
                } else {
                    Toast.makeText(LoginActivity.this, firebaseAuth.getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    /**
     * The handleLogin method aims to authenticate users and sign them into the application using
     * Firebase authentication.
     * @param view
     */
    public void handleLogin(View view){
        EditText txtUser = findViewById(R.id.txtUsername);
        EditText txtPass = findViewById(R.id.txtPassword);

        if (txtUser.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(txtUser.getText().toString(),
                    txtPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            } else {
                                String currentID = mAuth.getCurrentUser().getUid();
                                mRef.child("Users").child(currentID);

                                Intent launchChat = new Intent(LoginActivity.this, MainActivity.class );
                                startActivityForResult(launchChat,CHAT);
                                finish();
                            }
                        }
                    });
        }
    }

    /**
     * The handleCreateAccount method launches the register activity.
     * @param view
     */
    public void handleCreateAccount(View view) {
        Intent launchRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(launchRegister);
    }

    /**
     * The handleResetAccount method launches the account reset activity.
     * @param view
     */
    public void handleResetAccount(View view) {
        Intent launchReset = new Intent(LoginActivity.this, ResetActivity.class);
        startActivity(launchReset);
    }

}
