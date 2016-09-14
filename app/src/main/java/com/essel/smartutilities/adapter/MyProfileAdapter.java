package com.essel.smartutilities.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.My_Profile_Change_Details;
import com.essel.smartutilities.fragments.My_Profile_Change_Password;


public class MyProfileAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private final Context mContext;

    public MyProfileAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }
    // @Override
    //public Fragment getItem(int position) {
    //   switch (position) {
    //     case 0:
    //       return MyProfileFragment.newInstance(0, mContext.getString(R.string.change_details));
    //  case 1:
    //    return MyProfileFragment.newInstance(1, mContext.getString(R.string.change_password));
    //default:
    //  return null;
    //}
//}

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                My_Profile_Change_Details tab1 = new My_Profile_Change_Details();
                return tab1;
            case 1:
                My_Profile_Change_Password tab2 = new My_Profile_Change_Password();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.change_details);
            case 1:
                return mContext.getString(R.string.change_password);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
