package net.uoit.csci4100.mobiledeviceproject;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private TabLayout mTabLayout;
    private Toolbar mToolbarLayout;
    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayout;
    private ViewPager mViewPager;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    ArrayList<Users> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermissions();

        userList = new ArrayList<>();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();


        // Initialize AppBar and components
//        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        mToolbarLayout = (Toolbar) findViewById(R.id.main_toolbar);

        //Initialize Recycler View
        mUserList = (RecyclerView)findViewById(R.id.userList);
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);
        mUserListLayout = new LinearLayoutManager(this,LinearLayout.VERTICAL,false);
        mUserList.setLayoutManager(mUserListLayout);
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);

//        mUserList = (RecyclerView) findViewById(R.id.userList);
//        layoutManager = new LinearLayoutManager(this);
//        mUserList.setLayoutManager(layoutManager);
        // Setup Toolbar
//        setSupportActionBar(mToolbarLayout);
//
//        // Add Fragments to ViewPagerAdapter
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.AddFragment(new ChatsFragment(), getString(R.string.chats));
//        viewPagerAdapter.AddFragment(new ContactsFragment(), getString(R.string.contacts));

//        // Setup Adapter
//        mViewPager.setAdapter(viewPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);

        getContactList();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.settings){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent,0);
        }

        if(id == R.id.profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivityForResult(intent,0);
        }

        if(id == R.id.signOut){
            // Sign out user and send them back to LoginActivity
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent,0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getContactList(){
        Cursor contactList = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while(contactList.moveToNext()){
            String name = contactList.getString(contactList.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String email = contactList.getString(contactList.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
            //Image image = contactList.getString(contactList.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
            Image image = null;
            Users mUsers = new Users(name,email, image);
            userList.add(mUsers);
            mUserListAdapter.notifyDataSetChanged();
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 0);
        }
    }
}
