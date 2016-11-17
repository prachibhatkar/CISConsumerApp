package com.essel.smartutilities.utility;

import android.Manifest;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.security.PublicKey;
import java.util.ArrayList;


/**
 * Created by Admin on 22-08-2016.
 */
public class App extends MultiDexApplication {

    public static final String TAG = App.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static App mInstance;

    public ArrayList<String> permissions;
    public static Boolean dropdown = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static boolean getdropdown() {
    return dropdown;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}


