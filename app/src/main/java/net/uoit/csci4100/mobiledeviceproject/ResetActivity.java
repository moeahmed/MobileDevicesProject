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

/**
 * The ResetActivity class contains code corresponding to the activity_reset layout.
 */
public class ResetActivity extends AppCompatActivity {

    /**
     * The onCreate method creates the reset activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }

    /**
     * The handleAlreadyRegistered method closes the register activity.
     * @param view
     */
    public void handleAlreadyRegistered(View view) {
        finish();
    }

    /**
     * The handleReset method resets a user's password via email.
     * @param view
     */
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
