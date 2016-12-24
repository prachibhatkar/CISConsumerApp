package com.essel.smartutilities.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.essel.smartutilities.activity.LoginActivity;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;


/**
 * Created by Admin on 23-08-2016.
 */
public class DialogCreator implements ServiceCaller, View.OnClickListener {

    Context context;


    public static void showMessageDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showExitDialog(final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                activity.finishAffinity();
                System.exit(0);
                dialog.cancel();

            }
        });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showLogoutDialog(final Context context, Activity activity, String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                /*initProgressDialog();
                if (pDialog != null && !pDialog.isShowing()) {
                    pDialog.setMessage(" please wait..");
                    pDialog.show();
                }*/
                //JsonObjectRequest request = WebRequests.getLogOut(context, Request.Method.GET, AppConstants.URL_LOGOUT, AppConstants.REQUEST_LOGOUT, context,SharedPrefManager.getStringValue(context,SharedPrefManager.AUTH_TOKEN));
                //App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGOUT);


                // CommonUtils.logout(activity);
                // Intent in =new Intent(activity, LoginActivity.class);
                // activity.startActivity(in);
                // activity.finish();
                dialog.cancel();
            }
        });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {

        switch (label) {
            case AppConstants.REQUEST_LOGOUT: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.message != null) {
                            Intent in = new Intent(context, LoginActivity.class);
                            context.startActivity(in);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.message);


                        }
                        if (jsonResponse.authorization != null) {

                            //  CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(context, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }


            }

        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

    }

    @Override
    public void onClick(View v) {

    }
}
