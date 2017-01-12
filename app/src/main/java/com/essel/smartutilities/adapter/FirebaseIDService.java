package com.essel.smartutilities.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/*
 * Created by hp on 1/09/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    private Context context;
    @Override

    public void onTokenRefresh() {
// Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);
// TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
        FirebaseMessaging.getInstance().subscribeToTopic("info");
        FirebaseInstanceId.getInstance().getToken();
//        SharedPrefManager sharedPref= (SharedPrefManager) getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPref.sa;
        SharedPrefManager.saveValue(this, SharedPrefManager.FCM_PREF, getString(R.string.FCM_PREF));
        SharedPrefManager.saveValue(this, SharedPrefManager.FCM_TOKEN, getString(R.string.FCM_TOKEN));

    }
    /*
     * Persist token to third-party servers.
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Log.i(TAG, "sendRegistrationToServer");
// Add custom implementation, as needed.
    }
}