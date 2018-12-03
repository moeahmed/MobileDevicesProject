package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }

    public void handleAlreadyRegistered(View view) {
        finish();
    }

    public void handleReset(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText txtEmail = findViewById(R.id.txtUsername);

        auth.sendPasswordResetEmail(txtEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetActivity.this, "Password reset email Sent. Please check your email.", Toast.LENGTH_LONG).show();
                        }
                        else {

                        }
                    }
                });
    }
}
