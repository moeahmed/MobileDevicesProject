package net.uoit.csci4100.mobiledeviceproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private View mChatsView;
    private RecyclerView mAllChatList;
    private DatabaseReference mChatsRef;
    private DatabaseReference mUsersRef;
    private FirebaseRecyclerOptions<Users> mOptions;
    private FirebaseAuth mAuth;
    private String mCurrentUserID;


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mChatsView =  inflater.inflate(R.layout.fragment_chats, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserID = mAuth.getCurrentUser().getUid();
        mChatsRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(mCurrentUserID);
        mUsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserID);

        mAllChatList = (RecyclerView) mChatsView.findViewById(R.id.allChatsList);
        mAllChatList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mChatsView;
    }

/*
    @Override
    public void onStart() {
        super.onStart();

        mOptions = new FirebaseRecyclerOptions.Builder<Users>().setQuery(mChatsRef, Users.class).build();

        FirebaseRecyclerAdapter<Users, ChatsViewAdapter> fbAdapter = new FirebaseRecyclerAdapter<Users, ChatsViewAdapter>(mOptions) {
                @Override
                protected void onBindViewHolder(@NonNull final ChatsViewAdapter holder, int position, @NonNull Users model) {
                    final String userIDs = getRef(position).getKey();

                    mUsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("image")){
                                String profileImage = dataSnapshot.child("image").getValue().toString();
                                Picasso.get().load(profileImage).placeholder(R.drawable.avatar1).into(holder.mUserImage);
                            }
                            String profileName = dataSnapshot.child("name").getValue().toString();
                            String profileEmail = dataSnapshot.child("email").getValue().toString();

                            holder.mUserName.setText(profileName);
                            holder.mUserEmail.setText(profileEmail);
                            //Picasso.get().load().placeholder(R.drawable.avatar1).into(holder.userProfileImage);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @NonNull
                @Override
                public ChatsViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userdisplay_layout, viewGroup, false);
                    return new ChatsViewAdapter(view);
                }
            };
        mAllChatList.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }
    */
}
