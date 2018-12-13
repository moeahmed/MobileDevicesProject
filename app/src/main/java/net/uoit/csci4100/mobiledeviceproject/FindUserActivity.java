package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A class containing code for the FindUserActivity.
 */
public class FindUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecylerView;

    private DatabaseReference mRef;
    private DatabaseReference mContactRef;

    private String currentUserID;

    /**
     * The onCreate method creates the FindUserActivity and toolbar.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mContactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        currentUserID = FirebaseAuth.getInstance().getUid();


        mRecylerView = (RecyclerView)findViewById(R.id.finduser_Recycler);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mToolbar = (Toolbar)findViewById(R.id.findUser_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * The onStart method contains the onBindViewHolder responsible for adding contacts when clicked.
     */
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Users> opt = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(mRef, Users.class)
                .build();

        FirebaseRecyclerAdapter<Users, UserListAdapter> adapters = new FirebaseRecyclerAdapter<Users, UserListAdapter>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull UserListAdapter holder, final int position, @NonNull Users model) {

                holder.mUserName.setText(model.getName());
                holder.mUserEmail.setText(model.getEmail());
                Picasso.get().load(model.getImage()).placeholder(R.drawable.avatar1).into(holder.mUserImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visitUserID = getRef(position).getKey();

                        Toast.makeText(FindUserActivity.this, "Added user to contacts", Toast.LENGTH_LONG).show();
                        accceptChat(visitUserID);

                        Intent mainIntent = new Intent(FindUserActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                });

            }

            /**
             * The onCreateViewHolder method aids in populating the Find Users' RecyclerView.
             * @param viewGroup
             * @param i
             * @return
             */
            @NonNull
            @Override
            public UserListAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userdisplay_layout, viewGroup,false );
                UserListAdapter userListAdapter = new UserListAdapter(view);

                return userListAdapter;
            }
        };

        mRecylerView.setAdapter(adapters);
        adapters.startListening();



    }

    /**
     * The acceptChat method adds new users from the Find User RecyclerView.
     * @param otherUserID
     */
    private void accceptChat(final String otherUserID) {
        
        if (currentUserID == otherUserID) {
            Toast.makeText(this, "You can not add yourself!", Toast.LENGTH_SHORT).show();
        } else {
            mContactRef.child(otherUserID).child(currentUserID).child("Contacts").setValue("Saved")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mContactRef.child(currentUserID).child(otherUserID).child("Contacts").setValue("Saved")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                }
                                            }
                                        });
                            }
                        }
                    });
        }



    }
}
