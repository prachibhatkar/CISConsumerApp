package com.bynry.cisconsumerapp.callers;


import com.android.volley.NetworkResponse;
import com.bynry.cisconsumerapp.models.JsonResponse;

/**
 * Created by Admin on 22-08-2016.
 */
public interface ServiceCaller {
    void onAsyncSuccess(JsonResponse jsonResponse, String label);
    void onAsyncFail(String message, String label, NetworkResponse response);
}
