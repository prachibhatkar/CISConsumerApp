package com.essel.smartutilities.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.Contact_Details_Fragment;
import com.essel.smartutilities.fragments.Locate_Us_Fragment;

/**
 * Created by hp on 9/12/2016.
 */
public class ContactUsAdapter extends FragmentPagerAdapter {



    private static int NUM_ITEMS = 2;
    private final Context mContext;


    public ContactUsAdapter(Context context,FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext=context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Contact_Details_Fragment.newInstance(0, mContext.getString(R.string.contact_detail));
            case 1:
                return Locate_Us_Fragment.newInstance(1, mContext.getString(R.string.locate_us));

            default:
                return null;
        }
    }

    //public Fragment getItem(int position) {
    //Returning the current tabs
    // switch (position) {
    //  case 0:
    //     Contact_Details_Fragment conde = new Contact_Details_Fragment();
    //   return conde;
    // case 1:
    //  Locate_Us_Fragment locate = new Locate_Us_Fragment();
    // return locate;

    // default:
    // return null;
    //  }
    // }



    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.contact_detail);
            case 1:
                return mContext.getString(R.string.locate_us);

            default:
                return null;
        }
    }




    @Override
    public int getCount()
    {
        return NUM_ITEMS;
    }

}

