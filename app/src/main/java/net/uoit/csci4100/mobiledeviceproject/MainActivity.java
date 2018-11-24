package net.uoit.csci4100.mobiledeviceproject;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private AppBarLayout mAppBarLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AppBar and components
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        // Add Fragments to ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ChatsFragment(), getString(R.string.chats));
        viewPagerAdapter.AddFragment(new ContactsFragment(), getString(R.string.contacts));

        // Setup Adapter
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);





    }
}
