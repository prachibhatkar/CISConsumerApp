package com.bynry.cisconsumerapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hp on 9/12/2016.
 */
public class TipsAdapter extends FragmentPagerAdapter {



    private static int NUM_ITEMS = 3;
    private final Context mContext;


    public TipsAdapter(Context context,FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext=context;
    }



    @Override
    public Fragment getItem(int position) {

        /*switch (position) {
            case 0:
                return TipOneFragment.newInstance(0, mContext.getString(R.string.tips));
            case 1:
                return TipTwoFragment.newInstance(1, mContext.getString(R.string.tips));
            case 2:
                return TipThreeFragment.newInstance(2, mContext.getString(R.string.tips));
            default:

        }*/
        return null;
   }
   /* public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tips);
            case 1:
                return mContext.getString(R.string.tips);
            case 2:
                return mContext.getString(R.string.tips);

            default:
                return null;
        }
    }*/

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}



