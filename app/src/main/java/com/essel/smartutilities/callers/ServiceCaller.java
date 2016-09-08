package com.essel.smartutilities.callers;


import com.essel.smartutilities.webservice.response.JsonResponse;

/**
 * Created by Admin on 22-08-2016.
 */
public interface ServiceCaller {
    void onAsyncSuccess(JsonResponse jsonResponse, String label);
    void onAsyncFail(String message, String label);
}
