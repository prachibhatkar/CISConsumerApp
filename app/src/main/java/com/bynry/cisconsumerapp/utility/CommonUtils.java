package com.bynry.cisconsumerapp.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.bynry.cisconsumerapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils
{

    private static CommonUtils nUtilsHelper;
    private static Context mContext;

    public static CommonUtils getInstance(Context context)
    {
        if (nUtilsHelper == null)
        {
            mContext = context;
            nUtilsHelper = new CommonUtils();
        }
        return nUtilsHelper;
    }
    public static boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isValidEmail(CharSequence target)
    {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidNumber(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public static void hideKeyBoard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void saveDetails(Context context, String consumer_no, String name, String city) {
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SharedPrefManager.saveValue(context, SharedPrefManager.CONSUMER_NO, consumer_no);
//        SharedPrefManager.saveValue(context, AppConstants.CONSUMER_PASSWORD,password);
        SharedPrefManager.saveValue(context, SharedPrefManager.CONSUMER_NAME, name);
        SharedPrefManager.saveValue(context, SharedPrefManager.CONSUMER_CITY, city);
//        SharedPrefManager.saveValue(context, AppConstants.CONSUMER_LOGGED_IN_DATE,date);
    }

    public static void saveAuthToken(Context context, String authToken)
    {
        SharedPrefManager.saveValue(context, SharedPrefManager.AUTH_TOKEN, authToken);
    }

    public static void saveConsumerCity(Context context, String city)
    {
        SharedPrefManager.saveValue(context, SharedPrefManager.CONSUMER_CITY, city);
    }

    public static void saveConsumerNo(Context context, String consumer_no)
    {
        SharedPrefManager.saveValue(context, SharedPrefManager.CONSUMER_NO, consumer_no);
    }


    public static String getBitmapEncodedString(Bitmap pBitmap)
    {
        if (pBitmap != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return image;
        }
        return "";
    }


    public static boolean isNetworkAvaliable(Context context)
    {
        final ConnectivityManager conn_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo network_info = conn_manager.getActiveNetworkInfo();
        if (network_info != null && network_info.isConnected()) {
            if (network_info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            else if (network_info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }

        return false;
    }

    public static int getColor(Context context, int id)
    {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23)
        {
            return ContextCompat.getColor(context, id);
        } else
        {
            return context.getResources().getColor(id);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void askForPermissions(final Context context, LinearLayout ll_main_view, ArrayList<String> permissions) {
        ArrayList<String> permissionsToRequest = findUnAskedPermissions(context, App.getInstance().permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        final ArrayList<String> permissionsRejected = findRejectedPermissions(context, App.getInstance().permissions);

        if (permissionsToRequest.size() > 0)
        {//we need to ask for permissions
            //but have we already asked for them?

            ((Activity) context).requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), AppConstants.ALL_PERMISSIONS_RESULT);

            //mark all these as asked..
            for (String perm : permissionsToRequest)
            {
                markAsAsked(context, perm);
            }
        } else {

            if (permissionsRejected.size() > 0)
            {
                //we have none to request but some previously rejected..tell the user.
                //It may be better to show a dialog here in a prod application
                showPostPermissionsShackBar(context, ll_main_view, permissionsRejected);
            }
        }
    }

    public static void showPostPermissionsShackBar(final Context context, LinearLayout ll_mail_view, final ArrayList<String> permissionsRejected) {
        Snackbar snackBarView = Snackbar
                .make(ll_mail_view, String.valueOf(permissionsRejected.size()) + context.getString(R.string.permission_rejected_already), Snackbar.LENGTH_LONG)
                .setAction(R.string.allow_to_ask_permission_again, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        for (String perm : permissionsRejected) {
                            clearMarkAsAsked(context, perm);
                        }
                    }
                });
        ViewGroup group = (ViewGroup) snackBarView.getView();
        group.setBackgroundColor(getColor(context, R.color.colorPrimary));
        snackBarView.show();
    }


    /**
     * method that will return whether the permission is accepted. By default it is true if the user is using a device below
     * version 23
     *
     * @param context
     * @param permission
     * @return
     */

    public static boolean hasPermission(Context context, String permission)
    {
        if (canMakeSmores())
        {
            return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }


    /**
     * we will save that we have already asked the user
     *
     * @param permission
     */
    public static void markAsAsked(Context context, String permission)
    {
        SharedPrefManager.saveValue(context, permission, false);
    }

    /**
     * We may want to ask the user again at their request.. Let's clear the
     * marked as seen preference for that permission.
     *
     * @param permission
     */
    public static void clearMarkAsAsked(Context context, String permission)
    {
        SharedPrefManager.saveValue(context, permission, true);
    }


    /**
     * This method is used to determine the permissions we do not have accepted yet and ones that we have not already
     * bugged the user about.  This comes in handle when you are asking for multiple permissions at once.
     *
     * @param wanted
     * @return
     */
    public static ArrayList<String> findUnAskedPermissions(Context context, ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted)

        {
            if (!hasPermission(context, perm) && SharedPrefManager.shouldWeAskPermission(context, perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    /**
     * this will return us all the permissions we have previously asked for but
     * currently do not have permission to use. This may be because they declined us
     * or later revoked our permission. This becomes useful when you want to tell the user
     * what permissions they declined and why they cannot use a feature.
     *
     * @param wanted
     * @return
     */
    public static ArrayList<String> findRejectedPermissions(Context context, ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted)
        {
            if (!hasPermission(context, perm) && !SharedPrefManager.shouldWeAskPermission(context, perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    public static int randInt(int min, int max)
    {
        // Usually this should be a field rather than a method variable so
        // that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static boolean isLoggedIn(Context context)
    {
        String boo=SharedPrefManager.getStringValue(context,SharedPrefManager.AUTH_TOKEN);
        if (!boo.equalsIgnoreCase("no"))
            return true;
        else
            return false;
    }

    private static boolean canMakeSmores()
    {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


}
