package net.uoit.csci4100.mobiledeviceproject;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private Toolbar mToolbarLayout;
    private ViewPager mViewPager;
    private DatabaseReference mRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("Accounts").child(mUser.getUid()).child("Name").setValue("Somthing");

        mRef.child("Accounts").child(mUser.getUid()).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);

        // Initialize AppBar and components
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mToolbarLayout = (Toolbar) findViewById(R.id.main_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        // Setup Toolbar
        setSupportActionBar(mToolbarLayout);

        // Add Fragments to ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ChatsFragment(), getString(R.string.chats));
        viewPagerAdapter.AddFragment(new ContactsFragment(), getString(R.string.contacts));

        // Setup Adapter
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

<<<<<<< HEAD
=======

>>>>>>> 953999db293a49877c2b142b8d892b4e92d3dbe5
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
}
