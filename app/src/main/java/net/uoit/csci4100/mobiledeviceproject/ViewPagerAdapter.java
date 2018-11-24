package net.uoit.csci4100.mobiledeviceproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentListTitles = new ArrayList<>();


    /**
     * The viewPagerAdapter is a constructor for the ViewPagerAdapter class.
     * @param fm - Fragment Manager (FragmentManager)
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     *  The getItem function gets the selected tab item from the AppBar.
     * @param i - tab item position (int)
     * @return item at mfragmentList position (Fragment)
     */
    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    /**
     * The getCount function returns the number of fragments in the mFragmentList.
     * @return - Number of fragments (int)
     */
    @Override
    public int getCount() {
        return mFragmentListTitles.size();
    }

    /**
     * The getPageTitle function gets the title for a respective page.
     * @param position - Position of tab (int)
     * @return Title of fragment (String)
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentListTitles.get(position);
    }

    /**
     * The addFragment function adds a fragment to the mFragmentList.
     * @param fragment - Fragment to be added (Fragment)
     * @param Title - Fragment title (String)
     */
    public void AddFragment(Fragment fragment, String Title) {
        mFragmentList.add(fragment);
        mFragmentListTitles.add(Title);
    }
}
