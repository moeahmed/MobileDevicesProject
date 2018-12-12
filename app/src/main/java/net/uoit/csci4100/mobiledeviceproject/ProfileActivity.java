package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements ImageDataListener {

    private Button updateUserInfo;
    private EditText name;
    private EditText email;
    private EditText password;
    private ImageView profilePic;
    String currentUserID;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private StorageReference mStorage;

    private static final int GALLARY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateUserInfo = (Button) findViewById(R.id.btnProfileUpdate);
        name = (EditText)findViewById(R.id.txtProfileName);
        email = (EditText) findViewById(R.id.txtProfileEmail);
        password = (EditText) findViewById(R.id.txtProfilePassword);
        profilePic = (ImageView) findViewById(R.id.imageView3);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile Pics");

        String currentUserEmail = mAuth.getCurrentUser().getEmail();
        email.setText(currentUserEmail);

        retrieveInfo();


    }

    public void onClickUpdate(View view){
        final String mName = this.name.getText().toString();
        //String mPasswword = this.password.getText().toString();
        final String mMmail = this.email.getText().toString();
        HashMap<String,String> profileMap = new HashMap<>();

        mRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String,String> profileMapT = new HashMap<>();

                if(dataSnapshot.exists() && dataSnapshot.hasChild("image")){
                    String mImage = dataSnapshot.child("image").getValue().toString();
                    profileMapT.put("image", mImage);

                    if(!name.getText().toString().isEmpty() || !password.getText().toString().isEmpty() || !email.getText().toString().isEmpty()){
                        profileMapT.put("uid", currentUserID);
                        profileMapT.put("name", mName);
                        profileMapT.put("email", mMmail);

                        mRef.child("Users").child(currentUserID).setValue(profileMapT).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent launchChat = new Intent(ProfileActivity.this, MainActivity.class );
                                    startActivityForResult(launchChat,0);
                                    finish();
                                }
                            }
                        });
                    }
                }else{
                    if(!name.getText().toString().isEmpty() || !password.getText().toString().isEmpty() || !email.getText().toString().isEmpty()){
                        profileMapT.put("uid", currentUserID);
                        profileMapT.put("name", mName);
                        profileMapT.put("email", mMmail);

                        mRef.child("Users").child(currentUserID).setValue(profileMapT).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent launchChat = new Intent(ProfileActivity.this, MainActivity.class );
                                    startActivityForResult(launchChat,0);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        retrieveInfo();

    }

    private void retrieveInfo() {
        mRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("email") && dataSnapshot.hasChild("image")){
                    String mUsername = dataSnapshot.child("name").getValue().toString();
                    String mEmail = dataSnapshot.child("email").getValue().toString();
                    String mImage = dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(mImage).into(profilePic);

                    //Toast.makeText(ProfileActivity.this, mImage, Toast.LENGTH_SHORT).show();

                    //TODO Fix this, since we really dont need a library for doing its job
//                    DownloadImageTask imageTask = new DownloadImageTask();
//                    imageTask.setImageDataListener(ProfileActivity.this);
//                    imageTask.execute(new String[] {mImage});

                    name.setText(mUsername);
                    email.setText(mEmail);
                    //Picasso.get().load(mImage).into(profilePic);
                }else if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("email")) {
                    String mUsername = dataSnapshot.child("name").getValue().toString();
                    String mEmail = dataSnapshot.child("email").getValue().toString();

                    name.setText(mUsername);
                    email.setText(mEmail);

                }else if(dataSnapshot.exists() && dataSnapshot.hasChild("image")){
                    String mImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(mImage).into(profilePic);
                }else{
                    //Toast.makeText(ProfileActivity.this, "Please Update Profile Info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLARY && resultCode==RESULT_OK && data!=null){

            Uri imageURI = data.getData();


            CropImage.activity(imageURI)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                Uri imgURI = result.getUri();

                final StorageReference storageReference = mStorage.child(currentUserID + ".jpg");
                storageReference.putFile(imgURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Profile Picture Updated!", Toast.LENGTH_LONG).show();

                            task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    final String downloadUrl = task.getResult().toString();

                                    mRef.child("Users").child(currentUserID).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(ProfileActivity.this, "Error Has Occured", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }else{
                            Toast.makeText(ProfileActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        }
        retrieveInfo();
    }

    public void onClickPicture(View view){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLARY);
        //finish();
    }


    @Override
    public void taskUpdater(Bitmap data) {
        profilePic.setImageBitmap(data);
    }
}

