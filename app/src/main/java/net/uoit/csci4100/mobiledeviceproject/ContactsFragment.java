package net.uoit.csci4100.mobiledeviceproject;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ContactsFragment extends Fragment {

    private View mContactView;
    private RecyclerView mContactList;

    private DatabaseReference mRef;
    private DatabaseReference mUsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private String profileImage;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContactView =inflater.inflate(R.layout.fragment_contacts, container, false);
        mContactList = (RecyclerView) mContactView.findViewById(R.id.userList);
        mContactList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID);
        mUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FloatingActionButton fab = (FloatingActionButton)mContactView.findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FindUserActivity.class);
                startActivity(intent);
            }
        });
        return mContactView;


    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(mRef, Users.class)
                .build();

        FirebaseRecyclerAdapter<Users, UserListAdapter> adapter = new FirebaseRecyclerAdapter<Users, UserListAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserListAdapter holder, int position, @NonNull final Users model) {

                final String userID = getRef(position).getKey();
                mUsersRef.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(dataSnapshot.hasChild("image")){
                                profileImage = dataSnapshot.child("image").getValue().toString();
                                Picasso.get().load(profileImage).placeholder(R.drawable.avatar1).into(holder.mUserImage);
                            }
                            final String profileName = dataSnapshot.child("name").getValue().toString();
                            final String profileEmail = dataSnapshot.child("email").getValue().toString();

                            holder.mUserName.setText(profileName);
                            holder.mUserEmail.setText(profileEmail);
                            //Picasso.get().load().placeholder(R.drawable.avatar1).into(holder.userProfileImage);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                    chatIntent.putExtra("contactID", userID);
                                    chatIntent.putExtra("contactName", profileName);
                                    chatIntent.putExtra("contactEmail", profileEmail);
                                    chatIntent.putExtra("contactImage", profileImage);
                                    startActivity(chatIntent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public UserListAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userdisplay_layout, viewGroup,false );
                UserListAdapter userListAdapter = new UserListAdapter(view);

                return userListAdapter;
            }
        };

        mContactList.setAdapter(adapter);
        adapter.startListening();
    }
}
