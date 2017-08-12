package com.bynry.cisconsumerapp.Services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/*
 * Created by hp on 1/09/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    private Context context;
    @Override

    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
        FirebaseInstanceId.getInstance().getToken();

    }

    private void sendRegistrationToServer(String token)
    {
        Log.i(TAG, "sendRegistrationToServer");
    }
}