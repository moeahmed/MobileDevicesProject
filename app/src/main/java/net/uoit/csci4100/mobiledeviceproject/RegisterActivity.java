package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void handleAlreadyRegistered(View view) {
        finish();
    }

    public void handleRegister(View view) {
        mAuth = FirebaseAuth.getInstance();

        EditText txtUser = findViewById(R.id.txtUsername);
        EditText txtPass = findViewById(R.id.txtPassword);

        mAuth.createUserWithEmailAndPassword(txtUser.getText().toString(), txtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
