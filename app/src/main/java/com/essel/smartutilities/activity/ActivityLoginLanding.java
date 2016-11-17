package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.LoginDropDownFragment;
import com.essel.smartutilities.fragments.LoginLandingFragment;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.DialogCreator;

public class ActivityLoginLanding extends AppCompatActivity implements View.OnClickListener {
    TextView maintitle;
    LinearLayout img, button, table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        img = (LinearLayout) findViewById(R.id.linear_img);
        button = (LinearLayout) findViewById(R.id.linear_lay_button);
        table = (LinearLayout) findViewById(R.id.container);

        boolean flag1 = true;
        ImageView drop = (ImageView) findViewById(R.id.img_drowdown);
        drop.setOnClickListener(this);
        maintitle = (TextView) findViewById(R.id.title_bar);
        maintitle.setText("31513153515");
        TextView subtitle = (TextView) findViewById(R.id.subtitle_bar);
        subtitle.setText("Barry Alen");

        Button bill = (Button) findViewById(R.id.btn_mybill);
        bill.setOnClickListener(this);
        Button pay = (Button) findViewById(R.id.btn_paynow);
        pay.setOnClickListener(this);
        // toolbar.setOnMenuItemClickListener(ActionBar.DISPLAY_SHOW_HOME);


        Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        Boolean flag = FeedBackActivity.getflag();
        if (flag) {

            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_LONG);
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
            snack.setActionTextColor(Color.WHITE);
            FeedBackActivity.flag = false;

        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            DialogCreator.showLogoutDialog(this, "Logout", "Are you sure you want to logout?");
            return true;
        }

        if (id == R.id.action_notifications) {
            Intent i = new Intent(this, NotificationActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mybill:
                Intent i = new Intent(this, MyBillActivity.class);
                startActivity(i);
                break;
            case R.id.btn_paynow:
                Intent in = new Intent(this, PayNowActivity.class);
                startActivity(in);
                break;
            case R.id.img_drowdown:

                if (App.getdropdown()) {
                    table.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                    Fragment fragment = new LoginDropDownFragment();
                    FragmentManager fragmanager = this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
                    fragmentTransaction.replace(R.id.big_container, fragment);
                    fragmentTransaction.commit();
                    App.dropdown = false;
                } else {
                    table.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);
                    if (getSupportFragmentManager().findFragmentById(R.id.big_container) != null) {
                        getSupportFragmentManager()
                                .beginTransaction().
                                remove(getSupportFragmentManager().findFragmentById(R.id.big_container)).commit();
                        App.dropdown = true;
                    }
                }
                break;
        }
    }

    /*public static boolean snackBarMethod() {
    /* Boolean flag =FeedBackActivity.getflag();
    if(flag) {

        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
        snack.setActionTextColor(Color.WHITE);
        FeedBackActivity.flag = false;

    }


    }*/

        /*Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = BIND_AUTO_CREATE

                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        return true;
    }*/

    public void onBackPressed() {
        if (App.dropdown == false) {
            table.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().findFragmentById(R.id.big_container) != null) {
                getSupportFragmentManager()
                        .beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.big_container)).commit();
            } else {
                DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
            }
            App.dropdown = true;
        }
    }
}