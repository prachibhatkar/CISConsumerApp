package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.AboutUsFragment;
import com.essel.smartutilities.fragments.ContactUsFragment;
import com.essel.smartutilities.fragments.FragmentDrawer;
import com.essel.smartutilities.fragments.LoginFragment;
import com.essel.smartutilities.fragments.MyProfileFragment;
import com.essel.smartutilities.fragments.MyTariffFragment;
import com.essel.smartutilities.fragments.NewConnectionFragment;
import com.essel.smartutilities.fragments.QuickPay;
import com.essel.smartutilities.fragments.RegisterFragment;
import com.essel.smartutilities.fragments.TipsFragment;
import com.essel.smartutilities.utility.AppConstants;

public class ActivityMainSL extends BaseActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener {
    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private LinearLayout actionHome;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main_sl);
        setupUI();
    }

    private void setupUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangeListner);

        // display the first navigation drawer view on app launch
        mContext = this;
        actionHome = (LinearLayout) findViewById(R.id.action_home);
        if (actionHome != null) {
            actionHome.setOnClickListener(this);
        }

        Bundle bundle = getIntent().getExtras();
        int screenid = Integer.parseInt(bundle.getString(AppConstants.SCREEN_ID));
        displayView(screenid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_call:
                return true;
            case  R.id.action_notifications:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new QuickPay();
                title = getString(R.string.quick_pay);
                break;
            case 1:
                fragment = new LoginFragment();
                title = getString(R.string.login);
                break;
            case 2:
                fragment = new RegisterFragment();
                title = getString(R.string.register);
                break;
            case 3:
                fragment = new NewConnectionFragment();
                title = getString(R.string.apply_for_new_connection);
                break;
            case 6:
                fragment = new AboutUsFragment();
                title = getString(R.string.about_us);
                break;
            case 7:
                fragment = new ContactUsFragment();
                title = getString(R.string.contact_us);
                break;
            case 8:
                fragment = new MyProfileFragment();
                title = getString(R.string.my_profile);
                break;
            case 9:
                fragment = new MyTariffFragment();
                title = getString(R.string.my_tariff);
                break;
            default:
                break;
        }
        if (fragment != null) {
            addFragment(fragment,true);
            getSupportActionBar().setTitle(title);

        }
    }

    public void addFragment(Fragment fragment, boolean withAnimation) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        int entryCount = manager.getBackStackEntryCount();

        String fragName = "";
        if (entryCount > 0)
            fragName = manager.getBackStackEntryAt(entryCount - 1).getName();

        if (!backStateName.equals(fragName)) {
            boolean poped = manager.popBackStackImmediate(backStateName, 0);//int index= getBackStackEntryIndex(backStateName);
            if (!poped && manager.findFragmentByTag(backStateName) == null) {
                FragmentTransaction ft = manager.beginTransaction();
                if (withAnimation) {
                    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_enter, R.anim.slide_exit);
                }
                ft.replace(R.id.container_body, fragment, backStateName);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }
    }

    public void popBackStack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    FragmentManager.OnBackStackChangedListener backStackChangeListner = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
            if (fragment != null) {
                updateTitleAndDrawer(fragment);
            }
        }
    };

    private void updateTitleAndDrawer (Fragment fragment){

        if (fragment instanceof QuickPay){
            setTitle(getString(R.string.quick_pay));
        }else  if (fragment instanceof LoginFragment){
            setTitle(getString(R.string.login));
        }else  if (fragment instanceof RegisterFragment){
            setTitle(getString(R.string.register));
        }else  if (fragment instanceof NewConnectionFragment){
            setTitle(getString(R.string.apply_for_new_connection));
        }else  if (fragment instanceof AboutUsFragment){
            setTitle(getString(R.string.about_us));
        }else  if (fragment instanceof ContactUsFragment) {
            setTitle(getString(R.string.contact_us));
        }else  if (fragment instanceof TipsFragment) {
            setTitle(getString(R.string.tips));
        }else {
            setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == actionHome) {
            Intent i = new Intent(mContext, LandingSkipLogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
