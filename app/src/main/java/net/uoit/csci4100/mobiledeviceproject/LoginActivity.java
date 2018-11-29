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

public class LoginActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    public static final int CHAT = 0;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent launchChat = new Intent(LoginActivity.this, MainActivity.class );
                    startActivityForResult(launchChat,CHAT);
                }
            }
        };
    }

    public void handleLogin(View view){
        EditText txtUser = findViewById(R.id.txtUsername);
        EditText txtPass = findViewById(R.id.txtPassword);

        mAuth.signInWithEmailAndPassword(txtUser.getText().toString(),
                                        txtPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }else{
                    Intent launchChat = new Intent(LoginActivity.this, MainActivity.class );
                    startActivityForResult(launchChat,CHAT);
                }
            }
        });

    }


}
