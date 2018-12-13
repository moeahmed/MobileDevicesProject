package net.uoit.csci4100.mobiledeviceproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * ChatFragment for the main activity.
 */
public class ChatsFragment extends Fragment {

    private View mChatsView;
    private RecyclerView mAllChatList;
    private DatabaseReference mRef;
    private DatabaseReference mUsersRef;
    //private FirebaseRecyclerOptions<Users> mOptions;
    private FirebaseAuth mAuth;
    private String mCurrentUserID;
    private String profileImage = "";

    /**
     * The chatMessage empty constructor
     */
    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * The onCreateView creates the chat fragment view, initializes the chat list, and
     * holds the FAB onClickListener.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mChatsView =  inflater.inflate(R.layout.fragment_chats, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserID = mAuth.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(mCurrentUserID);
        mUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mAllChatList = (RecyclerView) mChatsView.findViewById(R.id.allChatsList);
        mAllChatList.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = (FloatingActionButton) mChatsView.findViewById(R.id.newChatButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openContactsFragment();
            }
        });

        return mChatsView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
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
                            holder.mUserEmail.setText("Last Message: ");

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            Query lastQuery = databaseReference.child("Chats").child(mCurrentUserID).child(userID).orderByKey().limitToLast(1);

                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String tmp = "";
                                    Boolean fromOrThem = false;

                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        Log.d("User key", child.getKey());
                                        Log.d("User val", child.child("message").getValue().toString());
                                        tmp = child.child("message").getValue().toString();

                                        if(child.child("from").getValue().toString().equals(mCurrentUserID)){
                                            fromOrThem = true;
                                        }
                                    }

                                    if(fromOrThem){
                                        holder.mUserEmail.setText("You: " + tmp);
                                    }else{
                                        holder.mUserEmail.setText(profileName + ": " + tmp);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    databaseError.getMessage();
                                }
                            });

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


        mAllChatList.setAdapter(adapter);
        adapter.startListening();
    }
}
